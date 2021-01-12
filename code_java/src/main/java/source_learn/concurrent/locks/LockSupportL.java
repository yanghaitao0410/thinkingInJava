package source_learn.concurrent.locks;

/**
 * @Desc
 *
 * 用于创建锁和其他同步类的基本线程阻塞基础类。
 * <p>此类与使用它的每个线程关联一个许可permit（就java.util.concurrent.Semaphore类而言）。
 * 如果有许可证，将立即返回park请求，并在此过程中消耗掉它； 否则可能会阻塞。
 * 如果许可证是不可用的，那么调用unpark方法可以使许可证可用。
 * （但与Semaphore类不同的是，许可证不会累积。最多有一个。）
 *
 * <p>park和unpark方法提供了有效的阻塞和解除阻塞线程的方法，这些线程不会遇到已弃用的Thread.suspend和Thread.resume方法无法用于此类目的的问题:在获得许可的情况下，一个调用park方法的线程与另一个调用unpark的线程之间的将都保持存活。
 *
 * <p>此外，如果调用者的线程被中断 interrupted，并且支持超时版本 timeout versions，park将返回。
 * park方法也可以在任何其他时间“无缘无故”返回，因此通常必须在调用park处的增加循环检查（当前是否真的可以返回）。
 * 从这个意义上讲，park是对“繁忙等待 busy wait”的优化，必须与unpark配对才能有效，它不会浪费太多的时间。
 *
 * <p>park的3个方法都支持传入一个block对象。线程被阻塞时会记录该对象，以允许监视和诊断工具（monitoring and diagnostic tools）确定线程被阻塞的原因。此类工具可以使用方法getBlocker（Thread）访问block。
 *
 * 强烈建议使用这些方法，而不是使用没有block参数的原始方法。
 * 在锁实现中提供作为block的参数通常是this
 *
 * 这些方法被设计用来作为创建高级同步的工具（ higher-level synchronization utilities），它们本身对大多数并发控制应用程序没有用处。
 *
 * park方法仅设计用于以下形式：
 * <pre> {@code
 * 	    while (!canProceed()) { ... LockSupport.park(this); }
 * 	}</pre>
 *
 * 在调用park方法之前，canProceed或任何其他操作都不会引起锁定或阻塞（locking or blocking. ）
 *
 * 因为每个线程仅关联一个许可证，所以park的任何中间用途都可能会干扰其预期的效果。
 *
 * 这是一个先入先出的非重入锁类的样例：
 * <pre> {@code
 * 	 class FIFOMutex {
 * 	   private final AtomicBoolean locked = new AtomicBoolean(false);
 * 	   private final Queue<Thread> waiters
 * 		 = new ConcurrentLinkedQueue<Thread>();
 *
 * 	   public void lock() {
 * 		 boolean wasInterrupted = false;
 * 		 Thread current = Thread.currentThread();
 * 		 waiters.add(current);
 *
 * 		 // Block while not first in queue or cannot acquire lock
 * 		 while (waiters.peek() != current ||
 * 				!locked.compareAndSet(false, true)) {
 * 		   LockSupport.park(this);
 * 		   if (Thread.interrupted()) // ignore interrupts while waiting
 * 			 wasInterrupted = true;
 *                  }
 *
 * 		 waiters.remove();
 * 		 if (wasInterrupted)          // reassert interrupt status on exit
 * 		   current.interrupt();
 *       }
 *
 * 	   public void unlock() {
 * 		 locked.set(false);
 * 		 LockSupport.unpark(waiters.peek());
 *     }
 * 	 }
 * 	 }</pre>
 *
 * @Author water
 * @date 2020/12/5
 */
public class LockSupportL {

    //私有构造方法
    private LockSupportL() {}

    private static void setBlocker(Thread t, Object arg) {
        UNSAFE.putObject(t, parkBlockerOffset, arg);
    }

    //todo 先补完thread


    // Hotspot implementation via intrinsics API
    private static final sun.misc.Unsafe UNSAFE;
    private static final long parkBlockerOffset;
    private static final long SEED;
    private static final long PROBE;
    private static final long SECONDARY;

    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> tk = Thread.class;
            //获取thread类 parkBlocker属性的字段偏移量
            parkBlockerOffset = UNSAFE.objectFieldOffset(tk.getDeclaredField("parkBlocker"));
            SEED = UNSAFE.objectFieldOffset(tk.getDeclaredField("threadLocalRandomSeed"));
            PROBE = UNSAFE.objectFieldOffset(tk.getDeclaredField("threadLocalRandomProbe"));
            SECONDARY = UNSAFE.objectFieldOffset(tk.getDeclaredField("threadLocalRandomSecondarySeed"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }



}
