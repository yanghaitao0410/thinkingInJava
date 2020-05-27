package code_java.garbage_collection;

/**
 * @Desc
 *关于垃圾收集的一个常见误解是：对于对象A,除非它的所有内部引用都消失或被手动删除，否则持有外部引用的对象不会被垃圾收集。这是不对的，
 * 如果程序中没有其他活动对象持有该对象的引用，即使对象内部存在活动对象引用，仍然可以进行垃圾收集。
 *
 * 在下面的例子中，MyClassB对象将持有一个外部对象MyClassA的引用，该对象一直存在到程序结束，但仍然会回收MyClassB对象。
 * @Author water
 * @date 2020/5/8
 **/
public class GcTest {

    public static void main(String[] args) {
        MyClassA myClassA = new MyClassA();
        for (int i = 0; i < 1000; i++) {
            MyClassB myClassB = new MyClassB(i, myClassA);
            System.gc();
        }
        System.out.println(" -- end --");
    }

    private static final class MyClassA {}

    private static final class MyClassB {
        /**
         * 注意，运行时产生OutOfMemoryError异常，那么系统可能没有足够的内存，必须减少MyClassB中d数组的大小
         */
        double[] d = new double[1000000];
        private int name;
        MyClassA myClassA;

        public MyClassB(int name, MyClassA myClassA) {
            this.name = name;
            this.myClassA = myClassA;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("finalized: "+name);
        }
    }
}
