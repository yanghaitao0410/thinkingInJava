package source_learn.collection;

import java.util.Map;

/**
 * @Desc
 * @Author water
 * @date 2020/10/29
 **/
public class LinkedHashMapL<K,V> extends HashMapL<K,V> implements Map<K,V> {
    /**
     * HashMap.Node subclass for normal LinkedHashMap entries.
     */
    static class Entry<K,V> extends HashMapL.Node<K,V> {
        LinkedHashMapL.Entry<K,V> before, after;
        Entry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }
}
