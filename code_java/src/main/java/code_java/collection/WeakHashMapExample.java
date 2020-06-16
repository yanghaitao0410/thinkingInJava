package code_java.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * @Desc
 * @Author water
 * @date 2020/6/15
 **/
public class WeakHashMapExample {

    /**
     * 没有额外引用指向WeakHashMap中的entry 垃圾回收时会将对象回收
     */
    @Test
    public void test1() {
        WeakHashMap<Key, MyObject> map = new WeakHashMap<>();
        int size = 10000;
        for (int i = 0; i < size; i++) {
            Key key = new Key();
            MyObject value = new MyObject();
            map.put(key, value);
        }

        System.gc();
        System.out.println("keys gced: " + Key.getKeyFinalizeCount());
        System.out.println("values gced: " + MyObject.getValueFinalizeCount());

        System.out.println("Map initial size: " + size);
        System.out.println("Map current size: " + map.size());
    }

    /**
     * 只有key具有外部引用
     * 运行后发现没有entry被垃圾回收
     */
    @Test
    public void test2() {
        WeakHashMap<Key, MyObject> map = new WeakHashMap<>();
        List<Key> keys = new ArrayList<>();
        int size = 10000;
        for (int i = 0; i < size; i++) {
            Key key = new Key();
            MyObject value = new MyObject();
            map.put(key, value);
            keys.add(key);
        }

        System.gc();
        System.out.println("keys gced: " + Key.getKeyFinalizeCount());
        System.out.println("values gced: " + MyObject.getValueFinalizeCount());

        System.out.println("Map initial size: " + size);
        System.out.println("Map current size: " + map.size());
    }

    /**
     * 只有value具有外部引用
     * 许多key被垃圾回收，相应的map中entry也被自动删除，但是value直到main结束才被垃圾收集。
     */
    @Test
    public void test3() {
        WeakHashMap<Key, MyObject> map = new WeakHashMap<>();
        List<MyObject> values = new ArrayList<>();
        int size = 10000;
        for (int i = 0; i < size; i++) {
            Key key = new Key();
            MyObject value = new MyObject();
            map.put(key, value);
            values.add(value);
        }
        System.gc();
        System.out.println("keys gced: " + Key.getKeyFinalizeCount());
        System.out.println("values gced: " + MyObject.getValueFinalizeCount());
        System.out.println("Map initial size: " + size);
        System.out.println("Map current size: " + map.size());
    }

}
