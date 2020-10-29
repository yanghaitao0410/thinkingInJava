package source_learn.collection;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @Desc
 * @Author water
 * @date 2020/10/28
 **/
public class HashMapL<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {
    private static final long serialVersionUID = 362498820763181265L;

    /**
     * map默认初始化容量 （16） 自定义初始容量的时候必须是2的幂
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * map的最大容量，如果任何一个带参数的构造函数隐式指定了较大的值，则使用。必须是2的幂<= 1<<30。
     * todo
     */
    static final int MAXINMUM_CAPACIRY = 1 << 30;

    /**
     * 创建对象时，构造函数中未指定时使用的默认加载因子。（达到容量的0.75时扩容）
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 为容器使用树而不是列表时的容器计数阈值。
     * 当向至少有这么多节点的bin中添加一个元素时，bin被转换为树。
     * (key经过hash算出在列表中的相同格子的个数)
     * 该值必须大于2，并且至少应该是8，在树中移除节点时小于该阈值会转换为普通链表。
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * 在调整大小操作期间(resize)用于取消树状化（拆分的）箱的箱计数阈值。
     * 该值应小于TREEIFY_THRESHOLD，并且最大为6以与移除下的收缩检测相啮合。
     * todo
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * 容器可能被treeified的最小表容量。(否则，如果容器中有太多节点，就会调整表的大小。)
     * 应该至少被设置为4 * TREEIFY_THRESHOLD，以避免大小调整和treeification阈值之间的冲突。
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /**
     * 基本hash bin节点，大多数entrity都是使用该类。
     * (可以参考下面的TreeNode子类，以及LinkedHashMap中的Entry子类。)
     *
     * @param <K>
     * @param <V>
     */
    static class Node<K, V> implements Map.Entry<K, V> {

        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public final K getKey() {
            return key;
        }

        @Override
        public final V getValue() {
            return value;
        }

        @Override
        public final String toString() {
            return key + "=" + value;
        }

        /**
         * 该node的hashcode
         *
         * @return
         */
        @Override
        public int hashCode() {
            //key和value分别hash后异或
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        /**
         * 设置新值 返回旧值
         *
         * @param newValue
         * @return
         */
        @Override
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Entry<?, ?>) o;
                if (Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue())) {
                    return true;
                }
            }
            return false;
        }
    }

    /* ---------------- Static utilities -------------- */

    /**
     * 计算key.hashCode()并将哈希值的较高位扩展到较低位。
     *
     * @param key
     * @return
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 返回x的类，如果它的形式是“class X implements Comparable<X>”，否则为返回null
     *
     * @param x
     * @return
     */
    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c;
            //Type是Java编程语言中所有类型的通用超接口。
            // 这些类型包括原始类型、参数化类型、数组类型、类型变量和基本类型。
            Type[] ts, as;
            Type t;

            //ParameterizedType表示参数化类型，例如Collection<String>
            ParameterizedType p;
            //String通过检查
            if ((c = x.getClass()) == String.class) {
                return c;
            }

            /**
             * 判断x对象的类是否implements Comparable<X>
             */
            //返回由该对象表示的类或接口直接实现的接口的类型。
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                            //返回表示声明此类型的类或接口的类型对象。
                            ((p = (ParameterizedType) t).getRawType() == Comparable.class) &&
                            //返回表示此类型的实际类型参数的类型对象数组
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 && as[0] == c) {

                        return c;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 如果x匹配kc (k的筛选的可比较类)，则返回k.compareto (x)，否则为0。
     * todo
     *
     * @param kc
     * @param k
     * @param x
     * @return
     */
    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 : ((Comparable) k).compareTo(x));
    }

    /**
     * 返回给定目标容量最接近的2的幂次方。
     * 例如传入64 返回64
     * 传入65 返回128
     * 该方法在初始化map长度和map扩容的时候有用到
     *
     * @param cap
     * @return
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXINMUM_CAPACIRY) ? MAXINMUM_CAPACIRY : n + 1;
    }

    /* ---------------- Fields -------------- */


    /**
     * map里面存储Node数组，在第一次使用时初始化，并根据需要调整大小。数组的长度总是2的幂。
     * (我们还允许在一些操作中允许长度为零，以允许目前不需要的引导机制。)
     */
    transient Node<K, V>[] table;

    /**
     * 缓存entrySet()
     * 注意，AbstractMap字段用于keySet()和values()。
     */
    transient Set<Map.Entry<K, V>> entrySet;

    /**
     * 此映射中包含的键-值对的数量。
     */
    transient int size;

    /**
     * 对该HashMap进行结构修改的次数结构修改是指更改HashMap中的映射次数或以其他方式修改其内部结构（例如，重新哈希）的修改。
     * 此字段用于使HashMap的Collection-view上的迭代器快速失败。 （请参见ConcurrentModificationException）
     */
    transient int modCount;

    /**
     * 当前map允许存放的最大数量 超出该值就需要扩容 计算公式：(capacity * load factor)。
     * The javadoc description is true upon serialization.
     * 如果没有分配table[]，则该字段保存初始数组容量，或者表示DEFAULT_INITIAL_CAPACITY的值为0。
     */
    int threshold;

    /**
     * 哈希表的装载因子。
     */
    final float loadFactor;

    /* ---------------- Public operations -------------- */

    /**
     * 通过指定初始容量和加载因子构造map对象
     *
     * @param initialCapacity
     * @param loadFactor
     */
    public HashMapL(int initialCapacity, float loadFactor) {
        //initialCapacity大小校验
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXINMUM_CAPACIRY) {
            initialCapacity = MAXINMUM_CAPACIRY;
        }
        //loadFactor校验
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        //指定初始容量后，可以允许不扩容的最大容量就是传入initialCapacity当前值或是下一个2的幂次方
        this.threshold = tableSizeFor(initialCapacity);
    }

    /**
     * 只通过指定初始容量构造map对象
     * 加载因子使用默认值
     *
     * @param initialCapacity
     */
    public HashMapL(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMapL() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        //所有其他字段为默认值
    }

    /**
     * 通过另一个map集合创建当前map
     * @param m
     */
    public HashMapL(Map<? extends K, ? extends V> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        putMapEntries(m, false);
    }

    /**
     *
     * @param m 传入的map
     * @param evict 最初构造map时为false，否则为true（中继到afterNodeInsertion方法）
     */
    final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
        int s = m.size();
        if (s > 0) {

            // 阶段1：当前map先调整大小
            if (table == null) {
                //初始化阶段

                //通过负载因子 得出不会扩容的map容量
                float ft = ((float) s / loadFactor ) + 1.0F;
                int t = ((ft < (float) MAXINMUM_CAPACIRY) ? (int) ft : MAXINMUM_CAPACIRY);

                //通过 tableSizeFor方法得到2的幂次方容量
                if(t > threshold) {
                    threshold = tableSizeFor(t);
                }
            } else if (s > threshold) {
                //当前map已经有值了
                resize();
            }

            //阶段2：设置值
            for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
                K key = e.getKey();
                V value = e.getValue();
                putVal(hash(key), key, value, false, evict);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 返回指定key映射到的value，如果map不包含key的映射，则返回null。、
     * 更正式地说，如果这个映射包含一个从键k到值v的映射(key==null ?k==null: key.equals(k))，则该方法返回v;否则返回null。
     * (最多只能有一个这样的映射。)
     * 返回值为null并不一定表示map不包含key的映射;也可能显式地将key映射为null。可以使用containsKey操作来区分这两种情况。
     * @param key
     * @return
     */
    @Override
    public V get(Object key) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    /**
     * 通过key的hash 、key 获取node
     * @param hash hash for key
     * @param key the key
     * @return the node, or null if none
     */
    final Node<K, V> getNode(int hash, Object key) {
        //临时存储当前map中的Node<K, V>[] table
        Node<K, V>[] tab;

        //key对应的table中的位置的链表第一个节点
        Node<K, V> first,
        //first节点的next
                e;
        //当前map中元素个数
        int n;
        //first或e中的key
        K k;

        //使用 (n - 1) & hash 定位到Node在table中的位置（槽位）
        if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
            //校验first节点的hash和key都和传入的匹配
            if (first.hash == hash
                    && ((k = first.key) == key || (key != null && key.equals(k)))) {
                return first;
            }

            //程序运行到这里说明first节点不是要找的,继续向下寻找
            if ((e = first.next) != null) {

                //红黑树方式存储的节点
                if (first instanceof TreeNode) {
                    return ((TreeNode<K, V> first).getTreeNode(hash, key));
                }

                //循环遍历链表方式存储的节点
                do {
                    //校验e节点的hash和key都和传入的匹配
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        return e;
                    }
                } while ((e = e.next) != null);
            }
        }
        //没有找到返回null
        return null;
    }

    /**
     * 如果map包含key的映射，则返回true。
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(Object key) {
        return getNode(hash(key), key) != null;
    }

    /**
     * put一对key value到map ，若map中已经存在，会覆盖旧值
     * @param key
     * @param value
     * @return 被覆盖的旧值，如果没有，则为null
     */
    @Override
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    /**
     * 实现了Map.put和相关方法
     * @param hash key的hash值
     * @param key key
     * @param value 要设置的value
     * @param onlyIfAbsent true : map中已经设置过这个可以，不覆盖原有值 （当前新值会设置失败） false : 覆盖旧值
     * @param evict false : table处于创建阶段
     * @return 被覆盖的旧值，如果没有，则为null
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        //临时变量指向map中table
        Node<K, V>[] tab;
        Node<K, V> p;
        //tab的length
        int n,
                //
                i;

        //table为空，首次扩容
        if ((tab = table) == null || (n = tab.length) == 0) {
            n = (tab = resize()).length;
        }

        return null;
    }

    /**
     * 初始化或翻倍table的size
     *若table为null，按 threshold 字段的初始容量目标分配。
     * 否则，由于我们使用的是2的幂次方，每个bin中的元素要么必须保持在相同的索引中，要么在新表中移动2的幂偏移量。
     * todo
     * @return
     */
    final Node<K,V>[] resize() {
        Node<K, V>[] oldTab = table;
        //旧table长度
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        //旧threshold
        int oldThr = threshold;
        int newCap, newThr = 0;

        if (oldCap > 0) {
            //非初始化，空间不足时扩容

            //当前容量已经达到最大容量，直接返回当前table
            if (oldCap >= MAXINMUM_CAPACIRY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXINMUM_CAPACIRY && oldCap >= DEFAULT_INITIAL_CAPACITY) {
                //table长度翻倍后在允许范围内，并且翻倍前的长度大于默认长度
                //threshold翻倍
                newThr = oldThr << 1;
            }

        } else if (oldThr > 0) {
            //oldCap = 0 说明还没有分配空间 初始容量设为threshold
            newCap = oldThr;
        } else {
            // oldCap、oldThr都为0  使用默认值初始化
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }

        //newThr = 0 有2种情况导致, 非初始化才会出现
        //1：newCap >= MAXINMUM_CAPACIRY
        //2: oldCap < DEFAULT_INITIAL_CAPACITY
        if (newThr == 0) {
            //下次扩容前可以存储的数据量
            float ft = (float) newCap * loadFactor;
            //需要确保table容量和不需要扩容的容量都在允许范围内 否则直接设置一个最大值
            newThr = (newCap < MAXINMUM_CAPACIRY && ft < (float)MAXINMUM_CAPACIRY ? (int)ft : Integer.MAX_VALUE);
        }

        //更新threshold
        threshold = newThr;
        //创建新table
        HashMapL.Node<K,V>[] newTab = (HashMapL.Node<K,V>[])new HashMapL.Node[newCap];
        //更新table
        table = newTab;

        //迁移旧table中数据到新table
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                //遍历到table中每个槽位
                Node<K, V> e;
                if ((e = oldTab[j]) != null) {
                    //释放旧Entry数组的对象引用（for循环后，旧的Entry数组不再引用任何对象）
                    oldTab[j] = null;
                    if (e.next == null) {
                        //链表中只有一个节点，直接映射到新table中 下标算法： e.hash & (newCap - 1)
                        newTab[e.hash & (newCap - 1)] = e;
                    } else if (e instanceof TreeNode){
                        //结构是红黑树 todo
                        ((HashMapL.TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    } else {
                        //链表中有多个节点

                    }
                }
            }
        }



    }


    public static void main(String[] args) {
        System.out.println(tableSizeFor(65));
    }



    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }




















    static final class TreeNode<K, V> extends LinkedHashMapL.Entry<K, V> {

        TreeNode(int hash, K key, V value, Node<K, V> next) {
            super(hash, key, value, next);
        }
    }
}
