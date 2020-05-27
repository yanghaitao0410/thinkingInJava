package code_java.garbage_collection;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author water
 * @date 2020/5/8
 **/
public class SoftVsWeakVsNormal {
    public static void main(String[] args) {
        List<Reference<MyObject>> references = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            //weak
            Reference<MyObject> ref = new WeakReference<>(
                    new MyObject("weak " + i));
            references.add(ref);
            //soft
            ref = new SoftReference<>(
                    new MyObject("soft " + i));
            references.add(ref);
            //normal
            new MyObject("normal " + i);
        }
        SoftVsNormal.printReferences(references);
    }
}
