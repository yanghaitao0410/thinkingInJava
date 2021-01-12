package source_learn.concurrent.locks;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.util.concurrent.locks.LockSupport;

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
         * waitStatus值，表示后续线程需要唤醒 unpark
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
        //循环
        for (;;) {
            Node t = tail;
            //初始化的第一次循环
            if (t == null) {
                //设置新的虚拟头节点
                if (compareAndSetHead(new Node())) {
                    //t为null说明还未初始化 tail节点就是head
                    tail = head;
                }
            } else {
                //二次之后的循环 此时已经有了head和tail节点 设置节点node
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
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
        //tail节点有值 直接设置
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        //这里再调用一次是处理没有初始化的情况
        enq(node);
        return node;
    }


    /**
     * 将队列头设置为node，从而将原队列节点抛弃。 仅被acquire方法调用。
     * 为了GC并抑制不必要的信号和遍历，还清空了未使用的字段
     * @param node
     */
    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    /**
     * 唤醒node的后继节点（如果存在的话）
     * @param node
     */
    private void unparkSuccessor(Node node) {
        /**
         * 如果status是负值（可能需要信号 signal） ,试着在发出信号之前更新状态
         * 如果调用失败或者status被等待的线程改变了，也没有问题
         */
        int ws = node.waitStatus;
        if (ws < 0) {
            compareAndSetWaitStatus(node, ws, 0);
        }

        /**
         * 要唤醒的线程被保存在后继节点中，后继节点通常就是下一个节点。
         * 但如果当前节点的下一个next的status是cancelled或表面上next节点为空，从尾部向前遍历可以找到实际的未取消的后继。
         */
        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            s = null;
            //节点从后向前遍历
            for (Node t = tail; t != null && t != node; t = t.prev) {
                //不停覆盖s  找到最先的status是负值的节点
                if (t.waitStatus <= 0) {
                    s = t;
                }
            }
        }

        //程序运行到这里 就找到了最靠近node的后继节点s 唤醒它的线程
        if (s != null) {
            LockSupport.unpark(s.thread);
        }
    }

    /**
     * shard 模式 放开操作， 让后继节点signals 状态的唤醒 ，其它状态propagation
     * todo （对于独占模式，如果需要signal，释放仅相当于调用head的unparkSuccessor。）
     */
    private void doReleaseShared() {

        /*
            确保release操作传播，即使有其他正在进行的acquires/releases。
            head节点的waitStatus 是 Node.SIGNAL的时候尝试解锁它的继任者。
            其它状态，则将状态设置为PROPAGATE，以确保在release时继续传播。
            此外，在执行此操作时，我们必须进行循环，以防添加了新节点。
            此外，与unpark继任人的其他使用不同，我们需要知道CAS重置状态是否失败，如果失败，需要重新检查。
         */
        for (;;) {
            Node h = head;
            if (h != null && h != tail) {
                int ws = h.waitStatus;
                //head节点是Node.SIGNAL状态进行的操作
                if (ws == Node.SIGNAL) {
                    //唤醒线程 没有成功进入下一次循环
                    if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0)) {
                        continue;
                    }
                    //h被唤醒，唤醒h的后继节点
                    unparkSuccessor(h);
                } else if (ws == 0 && !compareAndSetWaitStatus(h, 0, Node.PROPAGATE)) {
                    //0状态  改成PROPAGATE 下次无条件传播
                    // loop on failed CAS
                    continue;
                }
            }
            //head节点被重新设置了 需要重新遍历
            // loop if head changed
            if (h == head) {
                break;
            }
        }
    }

    /**
     *
     * @param node
     * @param propagate
     */
    private void setHeadAndPropagate(Node node, int propagate) {
        //旧head
        Node h = head;

        setHead(node);

        /*
        向下一个排队节点发送信号的情况：
        调用者指示Propagation状态，或者由之前的操作记录(如h.waitStatus，在setHead之前或之后)
        这块使用了waitStatus的符号检查（sign-check），因为PROPAGATE状态可能转换为SIGNAL

        下一个节点正在共享模式（shared mode）中等待，或者是其它状况，我们不知道，因为它看起来是空的

        这两种检查的保守性可能会导致不必要的唤醒，但只有在有多个竞争 acquires/releases时才会出现，所以大多数检查都需要现在或马上发出信号signals。
         */
        if (propagate > 0 || h == null || h.waitStatus < 0 || (h = head) == null || h.waitStatus < 0) {
            Node s = node.next;
            if (s == null || s.isShared()) {
                doReleaseShared();
            }
        }
    }


    /**
     * 取消正在进行的尝试调用 acquire方法的节点
     * @param node
     */
    private void cancelAcquire(Node node) {
        if (node == null) {
            return;
        }

        node.thread = null;

        //跳过cancelled状态的前节点
        Node pred = node.prev;
        while (pred.waitStatus > 0)  {

            /**
             * 等价于
             * pred = pred.prev;
             * node.prev = pred
             */
            node.prev = pred = pred.prev;
        }

        //前节点的next
        // predNext is the apparent node to unsplice. CASes below will
        // fail if not, in which case, we lost race vs another cancel
        // or signal, so no further action is necessary.
        Node predNext = pred.next;

        //这里可以使用无条件写（unconditional write）代替CAS。
        // 在这个原子步骤之后，其他节点可以跳过我们。在此之前，我们不受其他线程的干扰。
        node.waitStatus = Node.CANCELLED;

        //如果node是末尾节点 将node移除 tail向前移动
        if (node == tail && compareAndSetTail(node, pred)) {
            //设置前一个节点的next为null
            compareAndSetNext(pred, predNext, null);
        } else {
            //如果后继者需要信号，试着设置pred的下一个链接，这样它就会得到一个信号。否则唤醒它传播。
            int ws;
            if (pred != head &&
                    ((ws = pred.waitStatus) == Node.SIGNAL ||
                            (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
                    pred.thread != null) {

                Node next = node.next;

                if (next != null && next.waitStatus <= 0) {
                    compareAndSetNext(pred, predNext, next);
                }
            } else {
                unparkSuccessor(node);
            }

            // help GC
            node.next = node;
        }
    }

    /**
     * 检查并更新acquire方法获取失败的的节点的状态。 如果线程应阻塞，则返回true。
     * 这是所有获取acquire方法循环中的主要信号控制。 要求pred == node.prev。
     *
     * @param pred
     * @param node
     * @return
     */
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {

    }

    /**
     * 中断当前线程
     */
    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }

    /**
     * park and then check if interrupted
     * @return
     */
    private final boolean parkAndCheckInterrupt() {
        /*
        除非有许可，否则出于线程调度目的禁用当前线程。
        如果许可证可用，则将其消耗掉，并立即返回到调用处；否则，出于线程调度目的，当前线程将被禁用并处于休眠状态，
        直到发生以下三种情况之一：
            其他一些线程以当前线程作为目标调用unpark
            其他线程中断当前线程
            调用虚假地(也就是说，没有理由地)返回。

        此方法不报告是哪一种情况导致该方法返回。调用者应该首先重新检查导致线程park的条件。
        例如，调用者还可以决定返回时线程的中断状态。
         */

        LockSupport.park(this);
        return Thread.interrupted();
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

    /**
     * CAS waitStatus field of a node.
     */
    private static final boolean compareAndSetWaitStatus(Node node, int expect, int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset, expect, update);
    }

    /**
     * CAS next field of a node.
     */
    private  static final boolean compareAndSetNext(Node node, Node expect, Node update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }






}
