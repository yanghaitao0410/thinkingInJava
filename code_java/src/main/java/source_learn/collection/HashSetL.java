package source_learn.collection;

import java.io.*;
import java.util.*;

/**
 * @Desc
 * @Author water
 * @date 2020/11/25
 **/
public class HashSetL<E> extends AbstractSet<E> implements Set<E>, Cloneable, Serializable {
    static final long serialVersionUID = -5024744406713321676L;

    /**
     * HashSet主要使用了HashMap的key存储
     */
    private transient HashMap<E, Object> map;

    /**
     * 伪值，以便与map中的对象相关联
     */
    private static final Object PRESENT = new Object();

    /**
     * 构造一个新的空set;HashMap实例具有默认的初始容量(16)和加载因子(0.75)。
     */
    public HashSetL() {
        map = new HashMap<>();
    }

    public HashSetL(Collection<? extends E> c) {
        //容量选择
        map = new HashMap<>(Math.max((int)(c.size()/0.75f + 1), 16));
        //最终会调用自己的add()
        addAll(c);
    }

    /**
     * 指定初始容量和加载因子创建内部map
     * @param initialCapacity
     * @param loadFactor
     */
    public HashSetL(int initialCapacity, float loadFactor) {
        map = new HashMap<>(initialCapacity, loadFactor);
    }

    public HashSetL(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
    }

    /**
     * 包级别私有构造方法，仅被LinkedHashSet使用
     * 内部map是LinkedHashMap
     * @param initialCapacity
     * @param loadFactor
     * @param dummy 该参数是占位的，没有实际含义 将此构造函数与其他int、float构造函数区分开来
     */
    HashSetL(int initialCapacity, float loadFactor, boolean dummy) {
        map = new LinkedHashMap<>(initialCapacity, loadFactor);
    }

    /**
     * 返回集合中元素的迭代器。元素没有特定的顺序返回。
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == PRESENT;
    }

    @Override
    public void clear() {
        map.clear();
    }

    /**
     * 浅clone 元素不会复制
     * @return
     */
    @Override
    protected Object clone() {
        try {
            HashSetL<E> newSet = (HashSetL<E>) super.clone();
            newSet.map = (HashMap<E, Object>) map.clone();
            return newSet;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * 将此HashSet实例的状态保存到流中(即序列化它)。
     * 首先写出HashMap实例的容量（int）及其负载因子（float），
     * 然后写出集合的大小（其包含的元素数）（int），
     * 然后写出其所有元素（每个Object） ）（无特定顺序）。
     * @param s
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        //Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out HashMap capacity and load factor
//        s.writeInt(map.capacity());
//        s.writeFloat(map.loadFactor());

        // Write out size
        s.writeInt(map.size());

        // Write out all elements in the proper order.
        for (E e : map.keySet()) {
            s.writeObject(e);
        }

    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read capacity and verify non-negative.
        int capacity = s.readInt();
        if (capacity < 0) {
            throw new InvalidObjectException("Illegal capacity: " +
                    capacity);
        }

        // Read load factor and verify positive and non NaN.
        float loadFactor = s.readFloat();
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new InvalidObjectException("Illegal load factor: " +
                    loadFactor);
        }

        // Read size and verify non-negative.
        int size = s.readInt();
        if (size < 0) {
            throw new InvalidObjectException("Illegal size: " +
                    size);
        }

        //根据大小和负载因子设置容量，确保HashMap至少已满25％，但要限制在最大容量以内。
        capacity = (int) Math.min(size * Math.min(1 / loadFactor, 4.0f), HashMapL.MAXIMUM_CAPACIRY);

//        map = (((HashSetL<?>) this) instanceof LinkedHashSetL ?
//                new LinkedHashMap<E,Object>(capacity, loadFactor) :
//                new HashMap<E,Object>(capacity, loadFactor));


        // Read in all elements in the proper order.
        for (int i=0; i<size; i++) {
            @SuppressWarnings("unchecked")
            E e = (E) s.readObject();
            map.put(e, PRESENT);
        }
    }

    /**
     * todo
     * @return
     */
    @Override
    public Spliterator<E> spliterator() {
        return null;
    }
}
