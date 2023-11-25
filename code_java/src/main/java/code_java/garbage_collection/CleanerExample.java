package code_java.garbage_collection;

//import java.lang.ref.Cleaner;

/**
 * @Desc 需要jdk9及以上
 * @Author water
 * @date 2020/5/18
 **/
public class CleanerExample {

    public static void main(String[] args) {
        //通过工厂方法创建cleaner实例
//        Cleaner cleaner = Cleaner.create();
//        for (int i = 0; i < 10; i++) {
//            String id = Integer.toString(i);
//            MyObject myObject = new MyObject(id);
//            cleaner.register(myObject, new CleanerRunnable(id));
//        }

        //myObjects are not reachable anymore
        //do some other memory intensive work
        for (int i = 1; i <= 10000; i++) {
            int[] a = new int[10000];
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }

    private static class CleanerRunnable implements Runnable {
        private String id;

        public CleanerRunnable(String id) {
            this.id = id;
        }

        /**
         * 该方法在对象finalize方法执行之后，对象被回收之后调用
         */
        @Override
        public void run() {
            System.out.printf("MyObject with id %s, is gc'ed%n", id);

        }
    }

}
