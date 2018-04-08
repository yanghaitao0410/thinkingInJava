package com.yht.nowcode.sort;

import org.junit.Test;

import static com.yht.nowcode.sort.CompareUtil.*;
import static com.yht.nowcode.sort.CompareUtil.printArray;

/**
 * 堆排序由两个步骤组成：
 *  step1：建立大根堆的过程  O(N)
 *  step2：将大根堆堆顶元素与该堆最后元素交换，然后不包含现在最后元素，重新调整为大根堆的过程 O(logn)
 *
 *  sp1:大根堆为完全二叉树（脑补），根在其所在子树中“值最大”，
 *  具体实现存放在数组中
 *      通过下面公式定位其父节点和子节点：
 *          假设当前元素的在数组中的下标为i
 *          当前元素的父节点为：(i - 1) / 2
 *          当前元素的左子数：2 * i + 1
 *          当前元素的右子数：2 * i + 2
 *
 *  sp2:完全二叉树定义
 *      二叉树为满二叉树或在成为满二叉树的道路上
 *      填入子节点总是从左向右依次填入，不可跳过节点
 *
 */
public class HeapSort {

    public void heapSort(int[] arr) {
        if(arr == null || arr.length < 2)
            return;
        //建立大根堆
        for(int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }

        int size = arr.length;
        CompareUtil.swop(arr, 0, --size);
        while (size > 0) {
            heapify(arr, 0, size);
            CompareUtil.swop(arr, 0, --size);
        }


    }

    /**
     * 将现在堆顶元素移动到合适位置，变为大根堆
     * @param arr
     * @param index
     * @param size
     */
    private void heapify(int[] arr, int index, int size) {
        //左子节点位置
        int left = 2 * index + 1;
        //若左子节点在堆的范围之内，进入循环
        while(left < size) {
            /*若右子节点在堆的范围之内，并大于左子节点，
                那么largest为右子节点的下标，否则largest为左子节点的下标
                进入该while循环就意味着当前节点的左子节点存在
            */
            int largest = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
            //largest为当前节点和该节点左右孩子最大的下标
            largest = arr[largest] > arr[index] ? largest : index;
            //若发现largest还是当前节点，那么说明以当前节点为根的子数就是大根堆，直接跳出循环
            if(largest == index){
                break;
            }
            //将当前节点和子节点中较大的值的位置进行交换
            CompareUtil.swop(arr, index, largest);
            //将当前节点的index和左子节点的位置更新，继续循环
            index = largest;
            left = 2 * index + 1;
        }

    }

    /**
     * 将当前数组调成大根堆形式
     * @param arr
     * @param index
     */
    public void heapInsert(int[] arr, int index) {
        while(arr[index] > arr[(index - 1) / 2]) { //若当前节点比父节点大
            CompareUtil.swop(arr, index, (index - 1) / 2); //将当前节点与父节点交换
            index = (index - 1) / 2; //更新当前节点的位置，继续看当前位置和父节点的大小
        }
    }

    @Test
    public void test1() {
        //boolean compare = compareMethod(randArr());
        //System.out.println(compare ? "success!!" : "Fucking fucked");

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
            CompareUtil.rightMethod(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        heapSort(arr);
        printArray(arr);
    }
}















