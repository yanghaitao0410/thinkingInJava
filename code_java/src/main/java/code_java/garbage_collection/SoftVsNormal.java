package code_java.garbage_collection;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desc
 * @Author water
 * @date 2020/5/8
 **/
public class SoftVsNormal {
    public static void main(String[] args) throws InterruptedException {
        List<Reference<MyObject>> references = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //create soft reference
            MyObject myObject = new MyObject("soft " + i);
            Reference<MyObject> ref = new SoftReference<>(myObject);
            references.add(ref);
            //without wrapping in any reference
            new MyObject("normal " + i);
        }
        //let see which ones' get() will return null
        printReferences(references);
    }

    public static void printReferences(List<Reference<MyObject>> references) {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(() -> {
            try {
                //sleep a little in case if finalizers are currently running
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-- printing references --");
            references.stream()
                    .forEach(SoftVsNormal::printReference);
        });
        ex.shutdown();
    }
    public static void printReference(Reference<MyObject> r) {
        System.out.printf("Reference: %s [%s]%n", r.get(),
                r.getClass().getSimpleName());
    }

}
