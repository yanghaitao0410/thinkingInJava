package source_learn.concurrent.locks;

import sun.misc.Unsafe;

import java.io.Serializable;

/**
 * @Desc
 * @Author water
 * @date 2020/12/1
 **/
public class AbstractQueuedSynchronizerL extends AbstractOwnableSynchronizerL implements Serializable {

    private static final long serialVersionUID = 7373984972572414691L;

    protected AbstractQueuedSynchronizerL() {}

    /**
     * 等待队列节点类。
     *
     * <p>等待队列是“ CLH”（Craig，Landin和Hagersten）锁定队列的变体。 CLH锁通常用于自旋锁。
     * 相反，我们将它们用于阻塞同步器，但使用相同的基本策略，将有关线程的某些控制信息保存在其节点的前身中。
     * 每个节点中的“status”字段将跟踪线程是否应阻塞。
     * 节点的前任释放时会发出信号。
     * 未释放时，队列的每个节点都充当一个特定通知样式的监视器，其中包含一个等待线程。
     * 虽然status字段不控制是否授予线程锁等。 线程可能会尝试获取它是否在队列中的第一位。
     * 但是先行并不能保证成功。 它只赋予竞争权。 因此，当前发布的竞争者线程可能需要重新等待。
     *
     * <p>要加入CLH锁，您可以自动将其作为新尾部拼接。 要出队，只需设置头字段向下移动一位。
     * <pre>
     *             +------+  prev +-----+       +-----+
     *        head |      | <---- |     | <---- |     |  tail
     *             +------+       +-----+       +-----+
     * </pre>
     *
     * 插入到CLH队列中只需要对“tail”执行一次原子操作，因此从未排队到排队都有一个简单的原子分界点。
     * 同样，出队仅涉及更新“head”。
     * 但是，节点需要花费更多的精力来确定其后继者是谁，部分原因是要处理由于超时和中断而可能导致的取消。
     * “ prev”links（在原始CLH锁中未使用）主要用于处理取消。
     * 如果取消某个节点，则其后继节点（通常）会重新链接到未取消的前任节点。
     * 有关自旋锁情况下类似机制的说明，请参见Scott和Scherer的论文，网址为http://www.cs.rochester.edu/u/scott/synchronization/。
     * <p>我们还使用“next”links来实现阻塞机制。 每个节点的线程ID都保留在其自己的节点中，
     * 因此prev通过遍历下一个链接来确定它是哪个线程，从而通知下一个节点唤醒。
     * 确定后继者必须避免与新排队的节点竞争来设置其前任节点的“ next”字段。
     * 如果需要，可以通过在节点的后继者为空时从原子更新的“尾部”向后进行检查来解决此问题。
     * （next-links是一种优化，因此我们通常不需要向后扫描。）
     *
     * <p>取消操Cancellation作为基本操作引入了一些保守性算法。
     * 由于我们必须轮询其他节点的取消情况，因此我们可能会遗漏没有注意到已取消的节点在我们前面还是后面。
     * 要解决此问题，必须始终在取消节点同时取消继任者，使他们能够稳定在新的前任身上，
     * 除非我们能够确定谁将担负起这一责任而被取消的前任。
     *
     *  <p>CLH队列需要一个虚拟头节点才能开始。 但是我们不会在构建过程中创建它们，
     *  因为如果没有争用，这将是浪费时间。 在第一次争用时构造虚拟头节点、设置头和尾指针。
     *
     *  <p>等待条件的线程使用相同的节点，但使用额外的链接。
     *  Conditions只需要链接简单(非并发)链接队列中的节点，因为只有在独占持有时才会访问它们。
     *  等待时，将节点插入condition queue。 收到信号后，该节点将转移到主队列。
     *  status字段的特殊值用于标记节点所在的队列。
     */
    static final class Node {
        /**
         * 节点在共享模式下等待的标记
         */
        static final Node SHARED = new Node();

        /**
         * 节点以排他模式等待
         */
        static final Node EXCLUSIVE = null;

        /**
         * waitStatus值表示线程已被取消
         */
        static final int CANCELLED = 1;

        /**
         * waitStatus值，表示后续线程需要解除停车
         */
        static final int SIGNAL = -1;

        /**
         * waitStatus值表示线程正在等待状态
         */
        static final int CONDITION = -2;

        /**
         * waitStatus值，指示下一个acquireShared应该无条件传播
         */
        static final int PROPAGATE = -3;


        /**
         * Status field, taking on only the values:
         * <pre>
         *   SIGNAL:     该节点的后继者立即（或即将）被阻止（通过park），
         *               因此当前节点释放或取消时必须取消其后继者的停放（unpark）。
         *               为了避免竞争，acquire方法必须首先指示它们需要一个信号(signal)，
         *               然后重试原子性获取acquire，在失败时阻塞。
         *
         *   CANCELLED:  由于超时（timeout）或中断（interrupt），该节点被取消（cancelled）。
         *               节点永远不会离开此状态。 特别是，具有取消节点的线程永远不会再次阻塞。
         *
         *   CONDITION:  该节点当前在条件队列中。 在传输之前，它不会用作同步队列节点，此时状态将设置为0。
         *              （此值的使用与该字段的其他用途无关，但简化了技术细节。）
         *
         *   PROPAGATE:  releaseShared应该传播到其他节点。
         *               在doReleaseShared中对此进行了设置（仅适用于头节点），以确保传播继续进行，即使此后进行了其他操作也是如此。
         *
         *   0:          以上都不是
         *</pre>
         * <p>这些值以数字方式排列以简化使用。 非负值表示节点不需要发信号。
         *    因此，大多数代码不需要检查特定值，仅需检查符号即可。
         *
         * <p>对于常规同步节点，该字段初始化为0，对于条件节点，该字段初始化为CONDITION。
         *  使用CAS（或在可能的情况下进行无条件的易失性写操作）对其进行修改。
         */
        volatile int waitStatus;

        /**
         * 链接到当前节点/线程所依赖的先前节点，以检查waitStatus。 在入队期间分配，并且仅在出队时将其清空（出于GC的考虑）。
         * 同样，在取消前任后，我们会短路，同时找到一个未取消的前任，因为根节点永远不会被取消, 所以总会存在存在prev节点，：
         *  只有成功获取（acquire）后，结点才变为根。 取消的线程永远不会成功获取，并且线程只会取消自身，不会取消任何其他节点
         */
        volatile Node prev;

        /**
         * 链接到后继节点，当前节点/线程在释放时将其解散。 在排队过程中分配，在绕过取消的前任对象时进行调整，在出队时无效（出于GC的考虑）。
         * enq方法调用后，链接新节点后才分配prev的下一个字段，因此看到空的下一个字段不一定表示节点在队列末尾。
         * 但是，如果下一个字段似乎为空，则我们可以从尾部扫描上一个以进行双重检查。
         * 已取消节点的下一个字段设置为指向节点本身而不是null，以使isOnSyncQueue的工作更轻松
         */
        volatile Node next;

        /**
         * 使该节点进入队列的线程。在构造时初始化，使用后为空
         */
        volatile Thread thread;

        /**
         * 链接到等待条件的下一个节点，或者链接到特殊值SHARED。
         * 因为条件队列（condition queues）仅在以独占模式保存时才被访问，所以我们只需要一个简单的链接队列即可在节点等待条件时保存节点。
         * 然后将它们转移到队列中以重新获取（re-acquire）。 由于conditions只能是独占的，因此我们使用特殊值来表示共享模式来保存字段。
         */
        Node nextWaiter;

        /**
         * Returns true if node is waiting in shared mode.
         * @return
         */
        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        /**
         * 返回前一个节点，如果为空则抛出NullPointerException。
         * prev不能为空时使用。null检查可以省略，这里抛出是为了帮助VM。
         * @return
         * @throws NullPointerException
         */
        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null) {
                throw new NullPointerException();
            } else {
                return p;
            }
        }

        // Used to establish initial head or SHARED marker
        public Node() {
        }

        // 被addWaiter方法调用
        public Node(Thread thread, Node mode) {
            this.nextWaiter = mode;
            this.thread = thread;
        }

        // Used by Condition
        public Node(Thread thread, int waitStatus) {
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
    }

    /**
     * 等待队列的头，会延迟初始化。
     * 除了初始化之外，只能通过setHead方法进行修改。
     * 注意：如果head存在，则需要保证其waitStatus不可以是CANCELLED状态。
     */
    private transient volatile Node head;

    /**
     * 等待队列的尾部，延迟初始化。
     * 仅可以通过enq方法添加新的等待节点
     */
    private transient volatile Node tail;

    /**
     * The synchronization state.
     */
    private volatile int state;

    /**
     * 返回同步状态的当前值。 此操作具有volatile读取的内存语义。
     * @return
     */
    protected final int getState() {
        return state;
    }


    protected final void setState(int newState) {
        state = newState;
    }

    /**
     * 如果当前状态值等于期望值expect，则以原子方式将同步状态设置为给定的更新值update。 此操作具有volatile读写的内存语义。
     * @param expect
     * @param update
     * @return
     */
    protected  final boolean compareAndState(int expect, int update) {

        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    static final long spinForTimeoutThreshold = 1000L;

    /**
     * 将节点插入队列，必要时进行初始化。
     * @param node 要插入的节点
     * @return 插入节点的前一个节点
     */
    private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) {
                //t为null说明还未初始化
                if (compareAndSetHead(new Node())) {
                    tail = head;
                } else {
                    node.prev = t;
                    if (compareAndSetTail(t, node)) {
                        t.next = node;
                        return t;
                    }
                }
            }
        }
    }

    /**
     * 为当前线程和给定模式mode创建队列，然后讲节点排队
     * @param mode Node.EXCLUSIVE for exclusive, Node.SHARED for shared
     * @return the new node
     */
    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        //将mode节点放到队列的tail节点之后
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }




































    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffSet;
    private static final long tailOffSet;
    private static final long waitStatusOffset;
    private static final long nextOffset;

    static {
        try {
            //返回给定字段在其类的存储分配中的位置。不要期望对这个偏移量执行任何类型的运算;它只是一个cookie
            stateOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizerL.class.getDeclaredField("state"));
            headOffSet = unsafe.objectFieldOffset(AbstractQueuedSynchronizerL.class.getDeclaredField("head"));
            tailOffSet = unsafe.objectFieldOffset(AbstractQueuedSynchronizerL.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizerL.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizerL.class.getDeclaredField("next"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    /**
     * CAS head field. 仅被enq方法调用
     * @param update
     * @return
     */
    private final boolean compareAndSetHead(Node update) {
        //如果当前变量值为expected传入的，则将其以原子方式更新为x
        return unsafe.compareAndSwapObject(this, headOffSet, null, update);
    }

    /**
     * CAS tail field. Used only by enq.
     */
    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffSet, expect, update);
    }











}
