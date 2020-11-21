package source_learn.collection;

import java.io.Serializable;
import java.util.*;

/**
 * @Desc
 * @Author water
 * @date 2020/11/11
 **/
public class ArrayListL<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable {

    private static final long serialVersionUID = 8683452581122892189L;

    /**
     * 默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 用于空实例的共享空数组实例。
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 共享的空数组实例用于默认大小的空实例。我们将它与空的ELEMENTDATA区分开，以知道当第一个元素被添加时应该膨胀多少。
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 存储ArrayList元素的数组缓冲区。
     * ArrayList的容量是此数组缓冲区的长度。
     * 添加第一个元素时，任何具有elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * 的空ArrayList都将扩展为DEFAULT_CAPACITY。
     */
    transient Object[] elementData;

    private int size;

    /**
     * 构造一个指定容量的空list
     *
     * @param initialCapacity 指定容量
     */
    public ArrayListL(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    /**
     * 构造一个初始长度为10的空list
     *
     * 这里没有指定长度
     */
    public ArrayListL() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 构造一个list，该list包含指定集合的​​元素，元素的顺序和c的迭代器返回保存一致。
     *  todo 没有设置size
     * @param c
     */
    public ArrayListL(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            //c.toArray可能（不正确）不返回Object []（请参阅6260652）
            if (elementData.getClass() != Object[].class) {
                elementData = Arrays.copyOf(elementData, size, Object[].class);
            }
        } else {
            //c是空集合 elementData设置为空list
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    /**
     * 将此ArrayList实例的容量调整为list的当前大小。 应用程序可以使用此操作来最小化ArrayList实例的存储。
     */
    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            //size 不为零直接调整elementData长度
            //可以这么做需要元素在elementData中从0开始依次排列
            elementData = (size == 0)  ? EMPTY_ELEMENTDATA : Arrays.copyOf(elementData, size);
        }
    }

    /**
     * 增加这个ArrayList实例的容量，以确保它可以容纳minCapacity参数指定的元素数目。
     * @param minCapacity
     */
    public void ensureCapacity(int minCapacity) {
        //默认的空list，最小是默认大小。最小扩张设置为0
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) ? 0 : DEFAULT_CAPACITY;

        //传入的参数比最小扩张大 才可增加容量
        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    /**
     *
     * @param minCapacity
     */
    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        if (minCapacity - elementData.length > 0) {
            grow(minCapacity);
        }
    }


    /**
     * 要分配的数组的最大大小。一些虚拟机在数组中保留一些头字。
     * 尝试分配更大的数组可能会导致异常 OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 增加容量，确保list至少可以容纳minCapacity个元素
     * @param minCapacity
     */
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        //先扩容一半
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        //扩容后还比传入参数小，直接使用传入参数
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        //新容量比数组预设最大容量还大，返回真正的最大容量
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            newCapacity = hugeCapacity(minCapacity);
        }
        //elementData原数据放到新数组中
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     *
     * @param minCapacity
     * @return
     */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(0) >= 0;
    }

    @Override
    public int indexOf(Object o) {
        //list可以存null  返回第一个null的位置
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            //o不为null 使用o的equals方法寻找元素
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }

        //没有找到返回-1
        return -1;
    }

    /**
     * 从后往前定位元素位置
     * @param o
     * @return
     */
    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--) {
                if (elementData[i]==null) {
                    return i;
                }
            }
        } else {
            for (int i = size-1; i >= 0; i--) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }
}
