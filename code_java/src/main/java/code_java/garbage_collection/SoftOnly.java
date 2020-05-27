package code_java.garbage_collection;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author water
 * @date 2020/5/12
 **/
public class SoftOnly {

    public static void main(String[] args) throws InterruptedException {
        List<Reference<MyObject>> references = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            MyObject myObject = new MyObject("soft " + i);
            Reference<MyObject> ref = new SoftReference<>(myObject);
            references.add(ref);
            //allocate a little slowly
            Thread.sleep(10);
        }
        SoftVsNormal.printReferences(references);
    }

}
