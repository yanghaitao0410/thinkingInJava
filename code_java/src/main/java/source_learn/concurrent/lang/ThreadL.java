package source_learn.concurrent.lang;

import sun.nio.ch.Interruptible;

import java.security.AccessControlContext;
import java.security.AccessController;

/**
 * @Desc
 * @Author water
 * @date 2020/12/7
 **/
public class ThreadL implements Runnable {
    //确保registerNatives是<clinit>做的第一件事。
    private static native void registerNatives();

    static {
        registerNatives();
    }

    private volatile String name;
    /**
     * 优先级
     */
    private int priority;
    private ThreadL threadQ;
    private long eetop;

    /**
     * 是否单步执行此线程
     */
    private boolean single_step;

    /**
     * 是否是守护线程
     */
    private boolean daemon = false;

    /**
     * JVM state
     */
    private boolean stillborn = false;

    /**
     * 线程启动后将被运行的
     */
    private Runnable target;

    /**
     * 当前线程的线程组
     * todo
     */
    private ThreadGroup group;

    /**
     * 当前线程的上下文类加载器
     */
    private ClassLoader contextClassLoader;

    /**
     * todo
     */
    private AccessControlContext inheritedAccessControlContext;

    /**
     * 用于匿名线程的自动编号。
     */
    private static int threadInitNumber;

    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }

    /**
     * 属于这个线程的ThreadLocal值。
     * 这个map由ThreadLocal类维护
     */
    ThreadLocalL.ThreadLocalMapL threadLocals = null;

    /**
     * 可继承的threadLocal
     * 属于这个线程的InheritableThreadLocal值。
     * 这个map由InheritableThreadLocal类维护
     */
    ThreadLocalL.ThreadLocalMapL inheritableThreadLocals = null;

    /**
     * 此线程请求的堆栈大小，如果创建者未指定堆栈大小，则为0。
     * 由VM决定使用在设置的容量中进行操作。 一些虚拟机将忽略它。
     */
    private long stackSize;

    /**
     * 在本机线程终止后持续存在的jvm私有状态。
     */
    private long nativeParkEventPointer;

    /**
     * 线程id
     */
    private long tid;

    /**
     * 用于生成线程ID
     */
    private static long threadSeqNumber;

    /**
     * Java线程的工作状态，初始化0表示线程“尚未启动”
     */
    private volatile int threadStatus = 0;

    private static synchronized long nextThreadID() {
        return ++threadSeqNumber;
    }

    /**
     * 提供给当前对java.util.concurrent.locks.LockSupport.park调用的参数。
     * 由java.util.concurrent.locks.LockSupport.getBlocker访问的(私有)java.util.concurrent.locks.LockSupport.setBlocker设置
     */
    volatile Object parkBlocker;


    /**
     * 在可中断的(interruptible)I/O操作中阻塞线程的对象(如果有的话)。
     * blocker的interrupt方法应该在设置线程的中断状态之后被调用。
     */
    private volatile Interruptible blocker;

    private final Object blockerLock = new Object();

    /**
     * 设置blocker字段
     * invoked via sun.misc.SharedSecrets from java.nio code
     *
     * @param b
     */
    void blockedOn(Interruptible b) {
        synchronized (blockerLock) {
            blocker = b;
        }
    }

    /**
     * 线程最小优先级
     */
    public final static int MIN_RPIORITY = 1;

    /**
     * 线程默认优先级
     */
    public final static int NORM_PRIORITY = 5;

    /**
     * 线程最大优先级
     */
    public final static int MAX_PRIORITY = 10;

    /**
     * 返回当前执行的线程对象的引用
     *
     * @return
     */
    public static native Thread currentThread();

    /**
     * 提示调度程序当前线程愿意放弃当前对处理器的使用. 调度程序可以随意忽略此提示。
     * Yield是一种启发式尝试，旨在提高线程之间的相对进程，否则将过度利用CPU。
     * 应将其使用与详细的性能分析和基准测试结合起来，以确保它实际上具有所需的效果。
     * 实际很少适合使用此方法。 对于调试或测试目的，它可能很有用，因为它可能有助于重现由于竞争条件而产生的错误。
     * 当设计并发控制结构（例如java.util.concurrent.locks包中的结构）时，它也可能很有用。
     */
    public static native void yield();

    /**
     * 根据系统计时器和调度程序的精度和准确性，使当前正在执行的线程进入休眠状态（暂时停止执行）达指定的毫秒数。
     * 该线程不会失去任何监视器的所有权。
     *
     * @param millis
     * @throws InterruptedException
     */
    public static native void sleep(long millis) throws InterruptedException;

    /**
     * 根据系统计时器和调度程序的精度和准确性，使当前正在执行的线程进入休眠状态（暂时停止执行）达指定的毫秒数加上指定的纳秒数。
     * 该线程不会失去任何监视器的所有权。
     *
     * @param millis
     * @param nanos
     * @throws InterruptedException
     */
    public static void sleep(long millis, int nanos) throws InterruptedException {
        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException("nanosecond timeout value out of ranges");
        }

        if (nanos >= 500000 || (nanos != 0 && millis == 0)) {
            millis++;
        }

        sleep(millis);
    }

    /**
     * 初始化一个线程 使用当前AccessControlContext
     *
     * @param g
     * @param target
     * @param name
     * @param stackSize
     */
    private void init(ThreadGroup g, Runnable target, String name, long stackSize) {
        init(g, target, name, stackSize, null);
    }

    /**
     * 初始化线程
     *
     * @param g         线程所在组
     * @param target    调用run()方法的对象
     * @param name      线程名称
     * @param stackSize 新线程所需的堆栈大小，0表示将忽略此参数
     * @param acc       AccessControlContext; 如果为null，会设置为默认的AccessController.getContext()
     */
    private void init(ThreadGroup g, Runnable target, String name, long stackSize, AccessControlContext acc) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }

        this.name = name;

        Thread parent = currentThread();
        //todo SecurityManager
        SecurityManager security = System.getSecurityManager();
        //传入的group为null
        if (g == null) {

            //先从SecurityManager获取group
            if (security != null) {
                g = security.getThreadGroup();
            }

            //SecurityManager获取group获取不到，从创建当前线程的线程获取
            if (g == null) {
                g = parent.getThreadGroup();
            }
        }
        //无论是否threadgroup是否被传进来，都调用checkAccess
        //确定当前运行的线程是否具有修改此线程组的权限
        g.checkAccess();

        if (security != null) {
            if (isCCLOverridden(getClass())) {
                security.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
            }
        }

//        g.addUnstarted();

        this.group = g;
        this.daemon = parent.isDaemon();
        this.priority = parent.getPriority();

        if (security == null || isCCLOverridden(parent.getClass())) {
            this.contextClassLoader = parent.getContextClassLoader();
        } else {
//            this.contextClassLoader = parent.contextClassLoader;
        }

        this.inheritedAccessControlContext = acc != null ? acc : AccessController.getContext();
        this.target = target;

        setPriority(priority);

//        if (parent.inheritableThreadLocals != null) {
//            this.inheritableThreadLocals =
//                    ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
//        }

        this.stackSize = stackSize;

        tid = nextThreadID();
    }

    /**
     * 线程不能被clone
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public ThreadL() {
        init(null, null, "Thread-" + nextThreadNum(), 0);
    }

    public ThreadL(Runnable target) {
        init(null, target, "Thread-" + nextThreadNum(), 0);
    }

    ThreadL(Runnable target, AccessControlContext acc) {
        init(null, target, "Thread-" + nextThreadNum(), 0, acc);
    }

    public ThreadL(ThreadGroup group, Runnable target) {
        init(group, target, "Thread-" + nextThreadNum(), 0);
    }

    public ThreadL(String name) {
        init(null, null, name, 0);
    }

    public ThreadL(ThreadGroup group, String name) {
        init(group, null, name, 0);
    }

    public ThreadL(Runnable target, String name) {
        init(null, target, name, 0);
    }

    public ThreadL(ThreadGroup group, Runnable target, String name) {
        init(group, target, name, 0);
    }


    /**
     * 让线程开始运行，java虚拟机接下来会调用当前线程的run方法
     * <p>
     * vm不使用main方法所在的线程和system所在的线程组调用当前方法
     * status = 0 表示 当前线程处于"NEW"状态
     */
    public synchronized void start() {
        if (threadStatus != 0) {
            throw new IllegalThreadStateException();
        }

        //通知group该线程即将启动，以便将其添加到group的线程列表中，并减少group的未启动计数。
//        group.add(this);

        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
//                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                //什么也不做。如果start0抛出了一个Throwable，那么它将被传递到调用堆栈
            }
        }

    }

    private native void start0();

    /**
     * 如果这个线程是使用一个单独的Runnable的run对象构造的，那么该Runnable对象的run方法将被调用;
     * 否则，此方法不执行任何操作并返回。
     * Thread的子类应该重写这个方法
     */
    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }

    /**
     * 这个方法由系统调用，在线程实际退出之前给它一个清理的机会。
     */
    private void exit() {
        if (group != null) {
//            group.threadTerminated(this);
            group = null;
        }
        target = null;
        threadLocals = null;
        inheritableThreadLocals = null;
        inheritedAccessControlContext = null;
        blocker = null;
//        uncaughtExceptionHandler = null;
    }

    /**
     * 中断当前线程
     * 当前线程可以中断自己，其它情况将调用该线程的checkAccess方法，这可能导致抛出SecurityException。
     * 如果这个线程在Object类的wait(), wait(long), or wait(long, int)方法或
     * 该类的join(), join(long), join(long, int), sleep(long), sleep(long, int),方法的调用中被阻塞，
     * 那么它的中断状态将被清除，并且它将收到一个InterruptedException异常。
     * 如果此线程在InterruptibleChannel上的I / O操作中被blocked，则该通道将被关闭，
     * 该线程的中断状态（interrupt）将被设置，并且该线程将收到java.nio.channels.ClosedByInterruptException。
     * 如果此线程在java.nio.channels.Selector中被阻塞，则将设置该线程的中断状态，
     * 并且它将立即从选择操作（selection operation）中返回（可能具有非零值），就像调用selector的wakeup方法一样。
     * 如果前面的条件都不成立，则该线程的中断状态将被设置。
     * 中断一个非活动线程不需要有任何影响。
     */
    public void interrupt() {
//        if (this != Thread.currentThread()) {
//            checkAccess();
//        }

        synchronized (blockerLock) {
            Interruptible b = blocker;
            if (b != null) {
                interrupt0(); //设置中断标志
//                b.interrupt(this);
                return;
            }
        }
        interrupt0();
    }

    /**
     * 测试当前线程是否被中断。
     * 线程的中断状态将通过此方法清除。
     * 换句话说，如果连续调用该方法两次，那么第二个调用将返回false
     * (除非当前线程在第一个调用清除其中断状态并在第二个调用检查它之前再次中断)。
     *
     * 由于线程在中断时不是活动的，因此线程中断被忽略，这个返回false的方法将反映这个中断
     * @return
     */
    public static boolean interrupted() {
//        return currentThread().isInterrupted(true);
        return false;
    }

    /**
     * 测试此线程是否已被中断。此方法不影响线程的中断状态。
     * 由于线程在中断时不是活动的，因此线程中断被忽略，这个返回false的方法将反映这个中断。
     * @return
     */
    public boolean isInterrupted() {
        return isInterrupted(false);
    }

    /**
     * 测试某个线程是否被中断。根据传递的ClearInterrupted的值，中断状态是否被重置
     * @param ClearInterrupted
     * @return
     */
    private native boolean isInterrupted(boolean ClearInterrupted);

    /**
     * 改变线程的优先级
     *
     * @param newPriority
     */
    public final void setPriority(int newPriority) {
        ThreadGroup g;
        checkAccess();
        if (newPriority > MAX_PRIORITY || newPriority < MIN_RPIORITY) {
            throw new IllegalArgumentException();
        }
        //传入的优先级比当前线程组的最大优先级还大 使用当前线程组最大优先级
        if ((g = getThreadGroup()) != null) {
            if (newPriority > g.getMaxPriority()) {
                newPriority = g.getMaxPriority();
            }
            setPriority0(priority = newPriority);
        }
    }

    public final ThreadGroup getThreadGroup() {
        return group;
    }

    /**
     * 测试线程是否还活着。如果线程存活的话它就是已经启动，还没有死亡的状态。
     * @return
     */
    public final native boolean isAlive();

    /**
     * 返回当前线程的线程组及其子组中的活动线程数的估计值。
     * 递归地遍历当前线程的线程组中的所有子组。
     * 返回的值只是一个估计值，因为在此方法遍历内部数据结构时，线程的数量可能会动态变化，并且可能会受到某些系统线程的影响。
     * 此方法主要用于调试和监视目的。
     * @return
     */
    public static int activeCount() {
        return currentThread().getThreadGroup().activeCount();
    }

    /**
     * 将当前线程的线程组及其子组中的所有活动线程复制到指定数组中。
     * 此方法只需调用当前线程的线程组的ThreadGroup.enumerate(Thread[])方法。
     * 应用程序可以使用activeCount方法来估计数组应该有多大，但是如果数组太短，容纳不了所有线程，那么额外的线程将被忽略。
     * 如果获取当前线程的线程组及其子组中的每个活动线程非常重要，则调用程序应该验证返回的int值严格小于tarray的长度。
     * 由于此方法固有的竞争条件，建议仅将该方法用于调试和监视目的。
     * @param tarray
     * @return
     */
    public static int enumerate(Thread tarray[]) {
        return currentThread().getThreadGroup().enumerate(tarray);
    }

    /**
     * 等待该线程执行结束，最大等待的时间不超过传入的毫秒值。设置为0意味着一直等待，直到当前线程执行完。
     * 此实现使用以this.isAlive为条件的this.wait调用循环。 当线程运行结束时，将调用this.notifyAll方法。
     * 建议应用程序不要在线程实例上使用wait，notify或notifyAll。
     * @param millis
     * @throws InterruptedException
     */
    public final synchronized  void join(long millis) throws InterruptedException {
        long base = System.currentTimeMillis();
        //now表示已经等待了多长时间
        long now = 0;

        if (millis == 0) {
            while (isAlive()) {
                //当前线程处于存活状态，调用wait方法
                wait(0);
            }
        } else {
            while (isAlive()) {
                //等待时间
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);
                //更新now
                now = System.currentTimeMillis() - base;
            }
        }

    }

    /**
     * 最多等待当前线程执行 millIs + nanos时间
     * @param millis
     * @param nanos
     * @throws InterruptedException
     */
    public final synchronized void join(long millis, int nanos) throws InterruptedException {
        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                    "nanosecond timeout value out of range");
        }

        //超过500000ns 或者是millis为0 都取整为1ms
        if (nanos >= 500000 || (nanos != 0 && millis == 0)) {
            millis++;
        }

        join(millis);
    }

    /**
     * 一直等待直到当前线程执行完
     * @throws InterruptedException
     */
    public final void join() throws InterruptedException {
        join(0);
    }

    /**
     * 将当前线程的堆栈跟踪打印到标准错误流。此方法仅用于调试。
     */
    public static void dumpStack() {
        new Exception("Stack trace").printStackTrace();
    }

    /**
     * 将此线程标记为守护线程或用户线程。
     * 当惟一运行的线程都是守护进程线程时，Java虚拟机将退出。
     * 必须在线程启动之前调用此方法。
     * @param on
     */
    public final void setDaemon(boolean on) {
        checkAccess();
        if (isAlive()) {
            throw new IllegalThreadStateException();
        }
        daemon = on;
    }

    /**
     * 返回当前线程是不是守护线程
     * @return
     */
    public final boolean isDaemon() {
        return daemon;
    }

    /**
     * 确定当前运行的线程是否具有修改此线程的权限。
     * 如果存在SecurityManager，则以该线程作为其参数调用其checkAccess方法。这可能会导致抛出SecurityException。
     */
    private final void checkAccess() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
//            security.checkAccess(this);
        }
    }


    /**
     * Returns a string representation of this thread, including the
     * thread's name, priority, and thread group.
     *
     * @return  a string representation of this thread.
     */
    @Override
    public String toString() {
        ThreadGroup group = getThreadGroup();
//        if (group != null) {
//            return "Thread[" + getName() + "," + getPriority() + "," +
//                    group.getName() + "]";
//        } else {
//            return "Thread[" + getName() + "," + getPriority() + "," +
//                    "" + "]";
//        }

        return null;
    }

    private static final RuntimePermission SUBCLASS_IMPLEMENTATION_PERMISSION =
            new RuntimePermission("enableContextClassLoaderOverride");


    /**
     * 一个线程的状态。线程可以处于以下状态之一
     *
     * 线程在某一时间点上只能处于一种状态。这些状态是虚拟机内部状态，不反映任何操作系统线程状态。
     */
    public enum State {
        /**
         * 尚未启动状态。
         */
        NEW,

        /**
         * 处于runnable状态的线程正在Java虚拟机中执行，
         * 但它可能正在等待来自操作系统(如处理器)的其他资源。
         */
        RUNNABLE,

        BLOCKED,

        WAITING,

        TIMED_WAITING,

        TERMINATED;
    }


    private static boolean isCCLOverridden(Class<?> cl) {
        //todo
        return false;
    }


    /*一些私有助手方法*/
    private native void setPriority0(int newPriority);

    private native void interrupt0();
}
