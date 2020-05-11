package thinkingInJava.TenChapter;

import org.junit.Test;

/**
 * 内部类中使用外部this
 */
public class DotThis {
    void f() {
        System.out.println("DotThis.f()");
    }
    public class Inner {
        public DotThis outer() {
            return DotThis.this;  //使用外部类的名字后面跟圆点和this ：生成的即为外部类的引用
        }
    }

    public Inner inner() {
       return new Inner();
    }

    @Test
    public void test1() {
        DotThis dt = new DotThis();
        DotThis.Inner dti = dt.inner();
        dti.outer().f();

        //运行结果：DotThis.f()  调用的是外部类的f()方法
    }
}
