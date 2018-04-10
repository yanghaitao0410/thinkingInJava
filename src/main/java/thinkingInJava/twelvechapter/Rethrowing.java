package thinkingInJava.twelvechapter;

public class Rethrowing {

    public static void f() throws Exception {
        System.out.println("origination the exception in f()");
        throw new Exception("thrown form f()");
    }

    public static void g() throws Exception {
        try {
            f();
        } catch (Exception e) {
            System.out.println("Inside g(),e.printStackTrace()");
            e.printStackTrace(System.out);
            /**
             * 再上一级捕获到的信息显示的是原来异常抛出点的信息
             *
             */
            throw e;
        }
    }

    public static void h() throws Exception {
        try {
            f();
        } catch (Exception e) {
            System.out.println("Inside h(), e.printStackTrace()");
            e.printStackTrace(System.out);
            /**
             * 该方法返回Throwable对象，将更新抛出点信息，再上一级可以获取到
             */
            throw (Exception) e.fillInStackTrace();
        }
    }

    public static void main (String[] args) {
        try {
            g();
        } catch (Exception e) {
            System.out.println("main: printStackTrack()");
            e.printStackTrace(System.out);
        }
        System.out.println("=================");
        try {
            h();
        } catch (Exception e) {
            System.out.println("main: printStackTrack()");
            e.printStackTrace(System.out);
        }

        /*运行结果：
        *   origination the exception in f()
            Inside g(),e.printStackTrace()
            java.lang.Exception: thrown form f()
                at thinkingInJava.twelvechapter.Rethrowing.f(Rethrowing.java:7)
                at thinkingInJava.twelvechapter.Rethrowing.g(Rethrowing.java:12)
                at thinkingInJava.twelvechapter.Rethrowing.main(Rethrowing.java:39)
            main: printStackTrack()  可以看出在main中捕获方法g()再次抛出的e，没有更新信息
            java.lang.Exception: thrown form f()
                at thinkingInJava.twelvechapter.Rethrowing.f(Rethrowing.java:7)
                at thinkingInJava.twelvechapter.Rethrowing.g(Rethrowing.java:12)
                at thinkingInJava.twelvechapter.Rethrowing.main(Rethrowing.java:39)
            =================
            origination the exception in f()
            Inside h(), e.printStackTrace()
            java.lang.Exception: thrown form f()
                at thinkingInJava.twelvechapter.Rethrowing.f(Rethrowing.java:7)
                at thinkingInJava.twelvechapter.Rethrowing.h(Rethrowing.java:26)
                at thinkingInJava.twelvechapter.Rethrowing.main(Rethrowing.java:46)
            main: printStackTrack()  更新了信息
            java.lang.Exception: thrown form f()
                at thinkingInJava.twelvechapter.Rethrowing.h(Rethrowing.java:33)
                at thinkingInJava.twelvechapter.Rethrowing.main(Rethrowing.java:46)
        * */

    }
}
