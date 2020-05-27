package code_java.garbage_collection;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * @Desc
 * @Author water
 * @date 2020/5/18
 **/
public class PhantomRefExample {

    public static void main(String[] args) {
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();

        MyObject myObject1 = new MyObject("phantom");
        Reference<MyObject> ref = new PhantomReference<>(myObject1, referenceQueue);
        System.out.println("ref#get(): " + ref.get()); //获取虚引用对象
        MyObject myObject2 = new MyObject("normal");

        //make objects unreacheable
        myObject1 = null;
        myObject2 = null;

        if(checkObjectGced(ref, referenceQueue)){
            takeAction();
        }

        System.out.println("-- do some memory intensive work --");
        for (int i = 0; i < 10; i++) {
            int[] ints = new int[100000];
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
        if(checkObjectGced(ref, referenceQueue)){
            takeAction();
        }
    }

    private static boolean checkObjectGced(Reference<MyObject> ref, ReferenceQueue<MyObject> referenceQueue) {
        boolean gced = false;
        System.out.println("-- Checking whether object garbage collection due --");
        //运行后可以看出虚引用里面的对象会先执行finalized方法，然后再将该虚引用放入队列
        Reference<? extends MyObject> polledRef = referenceQueue.poll();
        System.out.println("polledRef: "+polledRef);
        System.out.println("Is polledRef same: "+ (gced=polledRef==ref));
        if(polledRef!=null) {
            System.out.println("Ref#get(): " + polledRef.get());
        }
        return gced;
    }

    //处理特定逻辑 比如清理DirectByteBuffer分配的堆外内存
    private static void takeAction() {
        System.out.println("pre-mortem cleanup actions");
    }

}
