package source_learn.concurrent.locks;

import java.io.Serializable;

/**
 * @Desc
 * 一个线程独占的同步器。 此类提供了创建锁 locks和相关同步器synchronizers（可能涉及所有权概念）的基础。
 * AbstractOwnableSynchronizer类本身不管理或使用此信息。
 * 但是，子类和工具可以使用适当维护的值来帮助控制和监视访问并提供诊断。
 * @Author water
 * @date 2020/12/1
 **/
public class AbstractOwnableSynchronizerL implements Serializable {
    /**
     * 即使所有字段都是transient，也要使用序列号
     */
    private static final long serialVersionUID = 3737899427754241961L;

    /**
     *被子类使用的空构造函数。
     */
    protected  AbstractOwnableSynchronizerL() {}

    /**
     * 独占模式同步的当前持有线程
     */
    private transient Thread exclusiveOwnerThread;


    /**
     * 设置当前拥有独占访问的线程。null参数表示没有线程拥有访问权。
     * 此方法不强制任何synchronization或volatile字段访问。
     * @param thread
     */
    protected final void setExclusiveOwnerThread(Thread thread) {
        exclusiveOwnerThread = thread;
    }

    /**
     * 返回setExclusiveOwnerThread最后设置的线程，
     * 如果未设置，返回null。此方法不强制任何同步或volatile字段访问
     * @return
     */
    protected final Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }
}
