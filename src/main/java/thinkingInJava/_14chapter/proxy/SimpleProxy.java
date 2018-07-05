package thinkingInJava._14chapter.proxy;

/**
 * 静态代理模式：
 *  将额外的操作从“实际”对象分离到不同的地方
 *
 */
public class SimpleProxy implements Interface {
    private Interface proxied;

    public SimpleProxy(Interface proxied) {
        this.proxied = proxied;
    }

    @Override
    public void doSomething() {
        System.out.println("Simple proxy doSomething");
        proxied.doSomething();
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("Simple proxy somethingElse " + arg);
        proxied.somethingElse(arg);
    }
}
