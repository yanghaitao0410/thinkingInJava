package thinkingInJava._14chapter.proxy;

import java.lang.reflect.Proxy;

/**
 * Proxy.newProxyInstance(classLoader, class[] , implements InvocationHandler)
 *  使用动态代理创建实例
 *      需要传入一个类加载器(要生成类或其子类就可以)，希望该代理实现的接口列表，InvacationHandler的一个实现
 *
 *  动态代理可以将所有的调用重定向到调用处理器，因此通常会向调用处理器的构造器传递一个“实际”对象的引用，
 *  从而使得调用处理器在执行中介任务时可以将请求转发
 */
public class SimpleDynamicProxy {
    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("bonobo");
    }
    public static void main(String[] args) {
        RealObject realObject = new RealObject();
        consumer(realObject);
        Interface proxy = (Interface) Proxy.newProxyInstance(
                Interface.class.getClassLoader(), new Class[]{Interface.class}, new DynamicProxyHandler(realObject));
        consumer(proxy);
    }
}
