package com.yht.nowcode.heap;

import org.junit.Test;

/**
 * 一块金条切成两半，需要花费和长度数值一样的铜板。比如长度为20的金条，不管切成长度多大的两半，都要花费20个铜板。
 * 一群人想整分整块金条，怎么分最省铜板？
 * 例如: 给定数组{10，20，30}，代表一共3个人，整块金条长度为10 + 20 + 30 = 60
 * 金条要分成10，20，30三个部分。
 * 分割方法1:
 *      先将60分成10和50，花费60
 *      再将50分成20和30，花费50
 *      一共花费110个铜板
 * 分割方法2：
 *      先把60分成30和30，花费60
 *      再将一个30分成10和20，花费30
 *      一共花费90个铜板
 *
 *      输入一个数组，返回分割的最小代价
 *
 *      思考的过程：该题实际上可以变化为树的叶子节点组合根节点，最后变成一颗二叉树的过程，
 *          一开始感觉组合的顺序是一个大根堆结构，但是两种分割方法都可以是大根堆结构，听完解题思路之后豁然开朗：
 *          可以通过小根堆结构实现该组合过程，一开始将数组中的数据都放入到小根堆结构中，然后依次弹出两个元素，
 *          准备一个变量累计两个元素相加的和，然后将该和压入堆中，直到堆中剩一个元素
 *          （实际上为哈夫曼编码问题）
 */
public class CutGold {

    public int cutGold(int[] goldArr){
        if(goldArr == null || goldArr.length == 0)
            return 0;
        if(goldArr.length == 1)
            return goldArr[0];
        if(goldArr.length == 2) {
            return goldArr[1] + goldArr[0];
        }

        SmallRootHeap smallRootHeap = new SmallRootHeap();
        int size = goldArr.length;
        for(int i = 0; i < size; i++) {
            smallRootHeap.add(goldArr[i]);
        }
        int result = 0;
        while (smallRootHeap.size() > 1) {
            int temSum = smallRootHeap.pop() + smallRootHeap.pop();
            result += temSum;
            smallRootHeap.add(temSum);
        }
        return result;
    }

    @Test
    public void testCutGold() {
        int[] golds = {10, 20, 30};
        System.out.println(cutGold(golds));
    }

}
