package code_java.garbage_collection;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * 如果一个对象的引用是在外部保存的，那么这个对象就不能进行垃圾回收:
 * 下面例子中MyClassB就不能被垃圾回收
 * @Author water
 * @date 2020/5/8
 **/
public class GcTest2 {
    /**
     * 运行结果：
     * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
     * 	at code_java.garbage_collection.GcTest2$MyClassB.<init>(GcTest2.java:28)
     * 	at code_java.garbage_collection.GcTest2.main(GcTest2.java:17)
     *
     * 	这表明没有对象被垃圾收集，程序最终没有可用内存。
     * @param args
     */
    public static void main(String[] args) {
        MyClassA myClassA = new MyClassA();
        for (int i = 0; i < 1000; i++) {
            MyClassB myClassB = new MyClassB(i);
            myClassA.list.add(myClassB);
            System.gc();
        }
    }

    private static final class MyClassA {
        List<MyClassB> list = new ArrayList<>();
    }

    private static final class MyClassB {
        double[] d = new double[1000000];
        private int name;

        public MyClassB(int name) {
            this.name = name;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("finalized: "+name);
        }
    }
}
