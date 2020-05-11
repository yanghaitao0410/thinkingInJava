package com.yht.nowcode.heap;

import com.yht.nowcode.sort.CompareUtil;

/**
 * 小根堆List
 */
public class SmallRootHeap {
    private int[] heap;
    private int index; //代表数据个数
    public SmallRootHeap() {
        heap = new int[10];
        index = 0;
    }

    public void add(int num) {
        if(index == heap.length) {
            int [] tem = new int[heap.length * 2];
            System.arraycopy(heap, 0, tem, 0, heap.length);
            heap = tem;
        }
        heap[index++] = num;
        heapSort();
    }



    /**
     * 将数组进行小根堆排序
     */
    private void heapSort(){
        heapInsert();
        int size = index;
        CompareUtil.swop(heap, 0, --size);
        while(size > 0) {
            heapify(0, size);
            CompareUtil.swop(heap,0, --size);
        }
    }

    /**
     * 将数组调整为小根堆结构
     */
    private void heapInsert() {
        for(int i = index - 1 ; i >= 0; i--) {
            heapInsert(i);
        }
    }

    private void heapInsert(int index){
        /**
         * 在当前节点存在父节点，并且比父节点小的时候，与父节点交换，并继续比较
         */
        while((index - 1) / 2 >= 0 && heap[index] < heap[(index - 1) / 2]) {
            CompareUtil.swop(heap, index, (index - 1) / 2);
            index = (index - 1 ) / 2;
        }
    }

    /**
     * 将index位置上元素进行调整，使得那一条支线变成小根堆结构
     *
     */
    private void heapify(int index, int size) {
        while (index * 2 + 1 < size) {
            int smallestIndex = index * 2 + 2 < size && heap[index * 2 + 2] < heap[index * 2 + 1] ? index * 2 + 2 : index * 2 + 1;
            smallestIndex = heap[smallestIndex] < heap[index] ? smallestIndex : index;
            if(smallestIndex == index) {
                break;
            }
            CompareUtil.swop(heap, smallestIndex, index);
            index = smallestIndex;
        }
    }

    public boolean isEmpty() {
        return index == 0;
    }

    public int peek() {
        if(isEmpty()) {
            throw new RuntimeException("heap is empty");
        }
        return heap[0];
    }

    public int pop() {
        if(isEmpty()) {
            throw new RuntimeException("heap is empty");
        }
        int result = heap[index - 1];
        heap[--index] = 0;
        return result;
    }


    public int size() {
        return index;
    }


    public static void main(String[] args) {
        //boolean compare = compareMethod(randArr());
        //System.out.println(compare ? "success!!" : "Fucking fucked");

        SmallRootHeap smallRootHeap = new SmallRootHeap();
        for(int i = 0; i < 500; i++) {
            smallRootHeap.add((int) ((100 + 1) * Math.random()) - (int) (100 * Math.random()));
        }
        while(!smallRootHeap.isEmpty()) {
            System.out.print(smallRootHeap.pop() + " ");
        }
    }

}
