package source_learn.collection;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

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
     * jdk1.7的源码，jdk1.8没有这个方法，但是实现原理一样的
     *
     * @param h
     * @param length
     * @return
     */
    static int indexFor(int h, int length) {
        return h & (length - 1);
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
     * 记录HashMap内部结构发生变化的次数，主要用于迭代的快速失败。
     * 强调一点，内部结构发生变化指的是结构发生变化，例如put新键值对，但是某个key对应的value值被覆盖不属于结构变化。
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
     *
     * @param m
     */
    public HashMapL(Map<? extends K, ? extends V> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        putMapEntries(m, false);
    }

    /**
     * @param m     传入的map
     * @param evict 最初构造map时为false，否则为true（中继到afterNodeInsertion方法）
     */
    final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
        int s = m.size();
        if (s > 0) {

            // 阶段1：当前map先调整大小
            if (table == null) {
                //初始化阶段

                //通过负载因子 得出不会扩容的map容量
                float ft = ((float) s / loadFactor) + 1.0F;
                int t = ((ft < (float) MAXINMUM_CAPACIRY) ? (int) ft : MAXINMUM_CAPACIRY);

                //通过 tableSizeFor方法得到2的幂次方容量
                if (t > threshold) {
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
     *
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
     *
     * @param hash hash for key
     * @param key  the key
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
                    return ((TreeNode < K,V > first).getTreeNode(hash, key));
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
     *
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(Object key) {
        return getNode(hash(key), key) != null;
    }

    /**
     * put一对key value到map ，若map中已经存在，会覆盖旧值
     *
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
     *
     * @param hash         key的hash值
     * @param key          key
     * @param value        要设置的value
     * @param onlyIfAbsent true : map中已经设置过这个可以，不覆盖原有值 （当前新值会设置失败） false : 覆盖旧值
     * @param evict        false : table处于创建阶段
     * @return 被覆盖的旧值，如果没有，则为null
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        //临时变量指向map中table
        Node<K, V>[] tab;
        //table中要设置的槽位节点
        Node<K, V> p;
        //tab的length
        int n,
                //
                i;

        //table为空，首次扩容
        if ((tab = table) == null || (n = tab.length) == 0) {
            //得到扩容之后的长度
            n = (tab = resize()).length;
        }
        //通过(n - 1) & hash计算出要put的键值对在table中的位置
        // 为空说明该位置没有别的元素
        if ((p = tab[i = (n - 1) & hash]) == null) {
            tab[i] = newNode(hash, key, value, null);
        } else {
            //要替换的旧节点
            Node<K, V> e;
            //传入key临时变量
            K k;

            //链表中第一个节点就是要更新的值
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;
            } else if (p instanceof TreeNode) {
                //红黑树插入节点
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
            } else {
                //在链表中新增或替换节点
                for (int binCount = 0; ; ++binCount) {
                    //遍历到链表结尾，说明map中没设置过当前key，新增节点
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        //binCount从0开始 链表节点达到8个，需要转换为红黑树
                        if (binCount >= TREEIFY_THRESHOLD - 1) {
                            treeifyBin(tab, hash);
                        }
                        break;
                    }
                    //定位到具体节点 跳出循环
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        break;
                    }
                    //p向下移动一位 因为e = p.next
                    p = e;
                }
            }
            //当节点不为空的时候
            if (e != null) {
                //替换节点中value值
                V oldValue = e.value;
                //onlyIfAbsent为false才设置新值
                if (!onlyIfAbsent || oldValue == null) {
                    e.value = value;
                }
                afterNodeAccess(e);
                return oldValue;
            }
        }
        //初始化或某一个没有节点的槽位新增节点时 modCount计数一次
        ++modCount;
        //只要有新增节点的操作 程序都会运行到这里 size自增
        //如果自增后元素数量大于threshold 扩容
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        //新增节点返回null 因为没有旧值
        return null;
    }

    /**
     * 初始化或翻倍table的size
     * 若table为null，按 threshold 字段的初始容量目标分配。
     * 否则，由于我们使用的是2的幂次方，每个bin中的元素要么必须保持在相同的索引中，要么在新表中移动2的幂偏移量。
     * todo
     *
     * @return
     */
    final Node<K, V>[] resize() {
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
            newThr = (newCap < MAXINMUM_CAPACIRY && ft < (float) MAXINMUM_CAPACIRY ? (int) ft : Integer.MAX_VALUE);
        }

        //更新threshold
        threshold = newThr;
        //创建新table
        HashMapL.Node<K, V>[] newTab = (HashMapL.Node<K, V>[]) new HashMapL.Node[newCap];
        //更新table
        table = newTab;

        //迁移旧table中数据到新table
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                //遍历到table中每个槽位
                Node<K, V> e;
                if ((e = oldTab[j]) != null) {
                    //释放旧Node数组的对象引用（for循环后，旧的Node数组不再引用任何对象）
                    oldTab[j] = null;
                    if (e.next == null) {
                        //链表中只有一个节点，直接映射到新table中 下标算法： e.hash & (newCap - 1)
                        newTab[e.hash & (newCap - 1)] = e;
                    } else if (e instanceof TreeNode) {
                        //结构是红黑树 todo
                        ((HashMapL.TreeNode<K, V>) e).split(this, newTab, j, oldCap);
                    } else {
                        /*
                        链表中有多个节点情况:
                        观测可以发现，我们使用的是2次幂的扩展(指长度扩为原来2倍)，
                        所以，元素的位置要么是在原位置，要么是在原位置再移动2次幂的位置

                        因此，我们在扩充HashMap的时候，不需要像JDK1.7的实现那样重新计算hash，
                        只需要看看原来的hash值新增的那个bit是1还是0就好了，
                        是0的话索引没变，是1的话索引变成“原索引+oldCap”
                        这个设计确实非常的巧妙，既省去了重新计算hash值的时间，
                        而且同时，由于新增的1bit是0还是1可以认为是随机的，因此resize的过程，
                        均匀的把之前的冲突的节点分散到新的bucket了
                         */
//                       链表优化重hash的代码块
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        do {
                            next = e.next;
                            // 原索引
                            if ((e.hash & oldCap) == 0) {
                                //第一次尽量 loTail为null，设置头节点
                                //节点的连接都是靠loTail移动
                                //这样做是为了让链表中元素相对顺序保证不变
                                if (loTail == null) {
                                    loHead = e;
                                } else {
                                    loTail.next = e;
                                }
                                loTail = e;
                            } else { // 原索引+oldCap
                                if (hiTail == null) {
                                    hiHead = e;
                                } else {
                                    hiTail.next = e;
                                }
                                hiTail = e;
                            }
                        } while ((e = next) != null);

                        // 原索引放到bucket里
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }

                        // 原索引+oldCap放到bucket里
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

    /**
     * jdk1.7扩容算法
     * 1.7使用的是Entry类 为了在这里不报错 改成了使用Node类
     *
     * @param newCapacity
     */
    void resize(int newCapacity) {   //传入新的容量
        Node[] oldTable = table;    //引用扩容前的Entry数组
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXINMUM_CAPACIRY) {  //扩容前的数组大小如果已经达到最大(2^30)了
            threshold = Integer.MAX_VALUE; //修改阈值为int的最大值(2^31-1)，这样以后就不会扩容了
            return;
        }

        Node[] newTable = new Node[newCapacity];  //初始化一个新的Entry数组
        transfer(newTable);                         //！！将数据转移到新的Entry数组里
        table = newTable;                          //HashMap的table属性引用新的Entry数组
        threshold = (int) (newCapacity * loadFactor);//修改阈值
    }

    /**
     * JDK1.7中rehash的时候，旧链表迁移新链表的时候，如果在新表的数组索引位置相同，则链表元素会倒置
     * 因为头插法
     *
     * @param newTable
     */
    void transfer(Node[] newTable) {
        Node[] src = table;                   //src引用了旧的Entry数组
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) { //遍历旧的Entry数组
            Node<K, V> e = src[j];             //取得旧Entry数组的每个元素
            if (e != null) {
                src[j] = null;//释放旧Entry数组的对象引用（for循环后，旧的Entry数组不再引用任何对象）
                do {
                    Node<K, V> next = e.next;
                    int i = indexFor(e.hash, newCapacity); //！！重新计算每个元素在数组中的位置

                    //使用头插法插入节点到newTable上，同一位置上新元素总会被放在链表的头部位置
                    e.next = newTable[i]; //标记[1]
                    newTable[i] = e;      //将元素放在数组上
                    e = next;             //访问下一个Entry链上的元素
                } while (e != null);
            }
        }
    }

    /**
     * 链表转换为红黑树 todo
     *
     * @param tab
     * @param hash
     */
    final void treeifyBin(Node<K, V>[] tab, int hash) {

    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        putMapEntries(m, true);
    }

    @Override
    public V remove(Object key) {
        Node<K, V> e;
        return (e = removeNode(hash(key), key, null, false, true)) == null ? null : e.value;
    }

    /**
     * 移除节点 实现思路和put节点类似
     *
     * @param hash
     * @param key
     * @param value      要匹配的value值
     * @param matchValue true :传入value和实际map中存储的相同才删除
     * @param movable    如果为false，则删除时不移动其他节点
     * @return 移除的节点 map中没有返回null
     */
    private Node<K, V> removeNode(int hash, Object key, Object value, boolean matchValue, boolean movable) {
        Node<K, V>[] tab;
        //要移除节点的前一个节点
        Node<K, V> p;
        int n, index;
        //定位到要移除key对应的槽位
        if ((tab = table) != null && (n = tab.length) > 0 && (p = tab[index = (n - 1) & hash]) != null) {
            //要移除的节点
            Node<K, V> node = null,
                    e;
            K k;
            V v;
            //槽位第一个节点就是要移除的
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                node = p;
            } else if ((e = p.next) != null) {
                //遍历槽位其它节点
                if (p instanceof TreeNode) {
                    node = ((TreeNode<K, V>) p).getTreeNode(hash, key);
                } else {
                    //链表
                    do {
                        if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                            node = e;
                            break;
                        }
                        p = e;
                    } while ((e = e.next) != null);
                }
            }

            //条件校验 node非空  并且满足matchValue
            if (node != null && (!matchValue || (v = node.value) == value || (value != null && value.equals(v)))) {
                if (node instanceof TreeNode) {
                    ((TreeNode<K, V>) node).removeTreeNode(this, tab, movable);
                } else if (node == p) {
                    //第一个节点就是要移除的节点
                    tab[index] = node.next;
                } else {
                    //前一个节点的next指向移除节点的next
                    p.next = node.next;
                }
                ++modCount;
                --size;
                afterNodeRemoval(node);
                return node;
            }
        }

        //没有找到节点 返回null
        return null;
    }

    /**
     * 清空map中元素
     */
    @Override
    public void clear() {
        Node<K, V>[] tab;
        modCount++;
        if ((tab = table) != null && size > 0) {
            size = 0;
            for (int i = 0; i < tab.length; ++i) {
                tab[i] = null;
            }
        }
    }

    /**
     * 如果map中存在传入的value，返回true
     * @param value
     * @return
     */
    @Override
    public boolean containsValue(Object value) {
        Node<K, V>[] tab;
        V v;
        if ((tab = table) != null && size > 0) {
            //遍历table每一个位
            for (int i = 0; i < tab.length; ++i) {
                //遍历链表每一位
                // todo 为什么这里不需要区分是链表还是红黑树  e.next是关键
                for (Node<K, V> e = tab[i]; e != null; e = e.next) {
                    //匹配到了value 返回true
                    if ((v = e.value) == value || (value != null && value.equals(v))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * AbstractMap中的属性，默认修饰符 需要同包才能访问  所以这里需要重新定义下
     */
    transient Set<K>        keySet;
    transient Collection<V> values;


    @Override
    public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new KeySet();
            keySet = ks;
        }
        return ks;
    }

    final class KeySet extends AbstractSet<K> {
        @Override
        public boolean contains(Object o) {
            return containsKey(o);
        }

        @Override
        public boolean remove(Object key) {
            return removeNode(hash(key), key, null, false, true) != null;
        }

        @Override
        public void clear() {
            HashMapL.this.clear();
        }

        @Override
        public Spliterator<K> spliterator() {
            return new KeySpliterator<>(HashMapL.this, 0, -1, 0, 0);
        }

        @Override
        public void forEach(Consumer<? super K> action) {
            Node<K, V>[] tab;
            if (action == null) {
                throw  new NullPointerException();
            }
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K, V> e = tab[i]; e != null; e = e.next) {
                        action.accept(e.key);
                    }
                }
                //校验map是否在遍历过程中有结构调整
                if (modCount != mc) {
                    throw new ConcurrentModificationException();
                }
            }
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return size;
        }
    }

    public static void main(String[] args) {
        System.out.println(tableSizeFor(65));
    }

    @Override
    public Collection<V> values() {
        Collection<V> vs = values;
        if (vs == null) {
            vs = new Values();
            values = vs;
        }
        return vs;
    }

    final class Values extends AbstractCollection<V> {
        @Override
        public boolean contains(Object o) {
            return containsValue(o);
        }

        @Override
        public void clear() {
            HashMapL.this.clear();
        }

        @Override
        public Spliterator<V> spliterator() {
            return new ValueSpliterator<> (HashMapL.this, 0, -1, 0, 0);
        }

        @Override
        public void forEach(Consumer<? super V> action) {
            Node<K, V> [] tab;
            if (action == null) {
                throw new NullPointerException();
            }
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K, V> e = tab[i]; e !=null; e = e.next) {
                        action.accept(e.value);
                    }
                }
                if (modCount != mc) {
                    throw new ConcurrentModificationException();
                }
            }
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public int size() {
            return size;
        }
    }

    /**
     * 返回此map中的set集合。该集合受到映射的支持，因此对映射的更改反映在集合中，反之亦然。
     * 如果在对集合进行迭代时修改了映射(除了通过迭代器自己的删除操作，或者通过迭代器返回的映射条目上的setValue操作)，
     * 那么迭代的结果是未定义的。
     * 这个集合支持元素删除，它通过迭代器从映射中删除相应的映射。
     * remove、Set.remove、removeAll、retainAll和clear操作。它不支持add或addAll操作。
     * @return
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Map.Entry<K,V>> es;
        return (es = entrySet) == null ? (entrySet = new HashMapL.EntrySet()) : es;
    }

    final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            Object key = e.getKey();

            Node<K, V> candidate = getNode(hash(key), key);
            return candidate != null && candidate.equals(e);
        }

        @Override
        public boolean remove(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Entry<?, ?>) o;
                Object key = e.getKey();
                Object value = e.getValue();
                return removeNode(hash(key), key, value, true, true) != null;
            }
            return false;
        }

        @Override
        public void clear() {
            HashMapL.this.clear();
        }

        @Override
        public Spliterator<Entry<K, V>> spliterator() {
            return new EntrySpliterator<>(HashMap.this, 0, -1, 0, 0);
        }

        @Override
        public void forEach(Consumer<? super Entry<K, V>> action) {
            Node<K,V>[] tab;
            if (action == null) {
                throw new NullPointerException();
            }
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K,V> e = tab[i]; e != null; e = e.next) {
                        action.accept(e);
                    }
                }
                if (modCount != mc) {
                    throw new ConcurrentModificationException();
                }
            }
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public int size() {
            return size;
        }
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? defaultValue : e.value;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return putVal(hash(key), key, value, true, true);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return removeNode(hash(key), key, value,true, true) != null;
    }

    /**
     * map中存在传入的key：oldValue，将value更新为newValue
     * @param key
     * @param oldValue
     * @param newValue
     * @return
     */
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        Node<K, V> e;
        V v;
        if ((e = getNode(hash(key), key)) != null
                && ((v = e.value) == oldValue || (v != null && v.equals(oldValue)))) {
            e.value = newValue;
            afterNodeAccess(e);
            return true;
        }
        return false;
    }

    @Override
    public V replace(K key, V value) {
        Node<K,V> e;
        if ((e = getNode(hash(key), key)) != null) {
            V oldValue = e.value;
            e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
        return null;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        //todo
        return null;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        //todo
        return null;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        //todo
        return null;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        //todo
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Node<K,V>[] tab;
        if (action == null) {
            throw new NullPointerException();
        }
        if (size > 0 && (tab = table) != null) {
            int mc = modCount;
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K,V> e = tab[i]; e != null; e = e.next) {
                    action.accept(e.key, e.value);
                }
            }
            if (modCount != mc) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        //todo
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        //todo
    }

    /**
     * These methods are also used when serializing HashSets
     * @return
     */
    final float loadFactor() {
        return loadFactor;
    }

    final int capacity() {
        return (table != null) ?  table.length : (threshold > 0) ? threshold : DEFAULT_INITIAL_CAPACITY;
    }


    /**
     * 实现了
     * 将HashMap实例的状态保存到一个流中(序列化)
     *
     * 首先输出HashMap的容量(bucket数组的长度 int)，然后输出元素个数(int，键-值映射的数量)，
     * 最后输出每个key-value映射。键-值映射的输出没有特定的顺序。
     * @param s
     * @throws Exception
     */
    private void writeObject(java.io.ObjectOutputStream s) throws Exception {
        int buckets = capacity();
        s.defaultWriteObject();
        s.writeInt(buckets);
        s.writeInt(size);
        internalWriteEntries(s);
    }

    private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        reinitialize();

        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new InvalidObjectException("Illegal load factor: " + loadFactor);
        }
        //读取并忽略桶的数量 ,在下面通过当前map的负载因子，重新计算桶大小
        s.readInt();
        //读取实际size
        int mappings = s.readInt();

        if (mappings < 0) {
            throw new InvalidObjectException("Illegal mappings count: " + mappings);
        } else if  (mappings > 0) { //mappings = 0不处理
            //loadFactor范围设置在0.25…4.0之间
            float lf = Math.min(Math.max(0.25f, loadFactor), 4.0f);
            float fc = (float)mappings / lf + 1.0f;
            //需要创建的table大小
            int cap = ((fc < DEFAULT_INITIAL_CAPACITY) ? DEFAULT_INITIAL_CAPACITY :
                    (fc >= MAXINMUM_CAPACIRY) ? MAXINMUM_CAPACIRY : tableSizeFor((int) fc));
            //需要扩容的临界值
            float ft = (float) cap * lf;
            threshold = (cap < MAXINMUM_CAPACIRY && ft < MAXINMUM_CAPACIRY) ? (int) ft : Integer.MAX_VALUE;

            Node<K, V> [] tab = new Node[cap];
            table = tab;

            //从流中读取数据，设置到map中
            for (int i = 0; i < mappings; i++) {
                K key = (K) s.readObject();
                V value = (V) s.readObject();
                putVal(hash(key), key, value, false, false);
            }

        }

    }

    /**
     * 创建一个常规(非树)节点
     *
     * @param hash
     * @param key
     * @param value
     * @param next  当前节点的next节点
     * @return
     */
    Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
        return new Node<>(hash, key, value, next);
    }

    /**
     * 重置map到初始默认状态。由clone和readObject调用。
     */
    void reinitialize() {
        table = null;
        entrySet = null;
        keySet = null;
        values = null;
        modCount = 0;
        threshold = 0;
        size = 0;
    }


    /**
     * 该方法仅被writeObject调用，以确保兼容的顺序。
     * @param s
     * @throws IOException
     */
    void internalWriteEntries(java.io.ObjectOutputStream s) throws IOException {
        Node<K, V>[] tab;
        if (size > 0 && (tab = table) != null) {
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K, V> e = tab[i]; e != null; e = e.next) {
                    s.writeObject(e.key);
                    s.writeObject(e.value);
                }
            }
        }
    }

    static final class TreeNode<K, V> extends LinkedHashMapL.Entry<K, V> {

        TreeNode(int hash, K key, V value, Node<K, V> next) {
            super(hash, key, value, next);
        }
    }
}
