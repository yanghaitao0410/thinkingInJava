package thinkingInJava._14chapter.nullobject.dynamic_null;

import thinkingInJava._14chapter.nullobject.Null;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;

/**
 * 动态创建实现Robot接口的子类的空对象
 */
class NullRobotProxyHandler implements InvocationHandler{
    private String nullName;
    private Robot proxied = new NRobot();
    NullRobotProxyHandler(Class<? extends Robot> type) {
        nullName = type.getSimpleName() + " NullRobot";
    }

    private class NRobot implements Robot, Null {
        @Override
        public String name() {
            return nullName;
        }

        @Override
        public String model() {
            return nullName;
        }

        @Override
        public List<Operation> operations() {
            return Collections.emptyList();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxied, args);
    }
}

public class NullRobot {
    public static Robot newNullRobot(Class<? extends Robot> type) {
        return (Robot) Proxy.newProxyInstance(
                NullRobot.class.getClassLoader(), new Class[]{Null.class, Robot.class}, new NullRobotProxyHandler(type));

    }
    public static void main(String[] args) {
        Robot[] robots = {new SnowRemovalRobot("SnowBee"),
            newNullRobot(SnowRemovalRobot.class)};
        for(Robot robot : robots) {
            Robot.Test.test(robot);
        }
     }


}
