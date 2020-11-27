package source_learn.collection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
     * <p>
     * 这里没有指定长度
     */
    public ArrayListL() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 构造一个list，该list包含指定集合的​​元素，元素的顺序和c的迭代器返回保存一致。
     * todo 没有设置size
     *
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
            elementData = (size == 0) ? EMPTY_ELEMENTDATA : Arrays.copyOf(elementData, size);
        }
    }

    /**
     * 增加这个ArrayList实例的容量，以确保它可以容纳minCapacity参数指定的元素数目。
     *
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
     * 确保list可以存下minCapacity数量的元素
     *
     * @param minCapacity
     */
    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    /**
     * list中容量不足 需要扩容
     * 并且modCount自增  该属性表示list改变结构的次数
     *
     * @param minCapacity
     */
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
     *
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
     * list从后往前定位元素位置
     *
     * @param o
     * @return
     */
    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 浅拷贝克隆 不复制list里面存的元素
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            ArrayListL<?> v = (ArrayListL<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            //这个异常不应该产生 因为实现了Cloneable接口
            throw new InternalError(e);
        }
    }

    /**
     * 返回一个数组，该数组包含list中的所有元素，并按正确的顺序(从第一个元素到最后一个元素)。
     * 返回的数组将是“安全的”，因为list不维护对它的引用。(换句话说，这个方法必须分配一个新的数组)。
     * 因此，调用者可以自由地修改返回的数组。
     * 此方法充当基于数组和基于list的api之间的桥梁。
     *
     * @return
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    public <T> T[] toArray(T[] a) {
        //传入数组长度比list中元素个数要小，返回新数组
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        //数组够用的情况下 直接拷贝
        System.arraycopy(elementData, 0, a, 0, size);

        //传入的a比list个数大 将size处置空
        //防止出现传入的数组是有数据的  拷贝之后不知道list元素边界的情况
        //所以正常使用的情况下 尽量不要传入已经有数据的数组来接收list中的元素
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    /**
     * ArrayList内部使用 返回index位置的元素
     *
     * @param index
     * @return
     */
    E elementData(int index) {
        return (E) elementData[index];
    }

    @Override
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }

    /**
     * 替换list中index位置元素
     *
     * @param index
     * @param element
     * @return
     */
    @Override
    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        //设置元素 并且size++
        elementData[size++] = e;
        return true;
    }

    /**
     * 在index位置插入一个元素 原来index位置到最后的元素需要向后移动一位
     *
     * @param index
     * @param element
     */
    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        ensureCapacityInternal(size + 1);
        //原来index位置到最后的元素需要向后移动一位
        System.arraycopy(elementData, index, elementData, index + 1, size - index);

        elementData[index] = element;
        size++;
    }

    /**
     * 移除并返回list中index位置的元素
     * index超过list的size范围，抛异常
     *
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E remove(int index) {
        rangeCheck(index);

        modCount++;
        E oldValue = elementData(index);
        //index后面元素个数，需要将他们向前移动一位
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }

        //让这个位置元素的实例没有引用指向，以便垃圾回收
        elementData[--size] = null;

        return oldValue;
    }

    /**
     * 移除list中的对象o
     *
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            //假如o=null 移除list中第一个null
            for (int index = 0; index < size; index++) {
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 私有移除方法:跳过边界检查且不返回已删除值
     *
     * @param index
     */
    private void fastRemove(int index) {
        modCount++;
        int numMoves = size - index - 1;
        if (numMoves > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoves);
        }

        elementData[--size] = null;
    }

    /**
     * 删除list所有元素。这个方法被调用后，list将为空。
     */
    @Override
    public void clear() {
        modCount++;

        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }

        size = 0;
    }

    /**
     * 直接在list后面追加c
     *
     * @param c
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    /**
     * 在list的index位置插入c的数据
     *
     * @param index
     * @param c
     * @return
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);

        //将index开始，到最后的元素移动c.length个位置
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
        }

        //将c中元素插入到index位置
        System.arraycopy(a, 0, elementData, index, numNew);
        size += numNew;
        return numNew != 0;
    }

    /**
     * 移除list中fromIndex~toIndex元素
     *
     * @param fromIndex
     * @param toIndex
     */
    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        modCount++;
        int numMoved = size - toIndex;
        //将toIndex到list末尾的元素移动到fromIndex处
        System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);

        //数组的新size
        int newSize = size - (toIndex - fromIndex);
        //将新size后的元素置空
        for (int i = newSize; i < size; i++) {
            elementData[i] = null;
        }
        size = newSize;
    }

    /**
     * 校验index是否在size以内
     *
     * @param index
     */
    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    /**
     * 校验index是否 > 0并且 在size以内
     *
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * 构造一个IndexOutOfBoundsException详细信息消息。
     * 在error handling code的许多可能的重构中，这种“outlining”在服务器和客户端虚拟机中都执行得最好。
     */
    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    /**
     * 删除传入的元素
     *
     * @param c
     * @return
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);

        return batchRemove(c, false);
    }

    /**
     * 保留传入的元素
     *
     * @param c
     * @return
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);

        return batchRemove(c, true);
    }

    /**
     * 删除或保留传入的元素c
     * 通过complement控制
     *
     * @param c
     * @param complement true 保留  false  删除
     * @return
     */
    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] elementData = this.elementData;
        //读指针（快指针）
        int r = 0,
                //写指针（慢指针） 要删除的元素  使用后面的不删除元素覆盖
                w = 0;
        boolean modified = false;

        try {
            for (; r < size; r++) {
                //removeAll方法complement = false
                //这块表示将保留的元素向前移动
                if (c.contains(elementData[r]) == complement) {
                    elementData[w++] = elementData[r];
                }

            }
        } finally {
            /**
             * 保持与AbstractCollection的行为兼容性，即使c.contains() throws。
             * 正常情况是不会执行这个if的
             */
            if (r != size) {
                System.arraycopy(elementData, r, elementData, w, size - r);
                w += size - r;
            }

            //最终调整size  将多余元素置空
            if (w != size) {
                for (int i = w; i < size; i++) {
                    elementData[i] = null;
                    modCount += size - w;
                    size = w;
                    modified = true;
                }
            }
        }
        return modified;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        int expectedModCount = modCount;
        s.defaultWriteObject();

        s.writeInt(size);

        for (int i = 0; i < size; i++) {
            s.writeObject(elementData[i]);
        }

        /**
         * 在序列化过程中有改变list结构的操作 抛出异常
         */
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;

        s.defaultReadObject();

        s.readInt();

        if (size > 0) {
            ensureCapacityInternal(size);

            Object[] a = elementData;

            // Read in all elements in the proper order.
            for (int i = 0; i < size; i++) {
                a[i] = s.readObject();
            }
        }
    }


}
