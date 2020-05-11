package com.yht.nowcode.sort;

public class MaxGap {

    public int maxGap(int [] arr) {
        int[] minAndMax = getMaxAndMin(arr);
        int len = arr.length;
        if(minAndMax[0] == minAndMax[1]) {
            return 0;
        }

        boolean [] hasNum = new boolean[len + 1];
        int [] maxs = new int[len + 1];
        int [] mins = new int[len + 1];

        int bid = 0;
        for(int i = 0; i < len; i++) {
            bid = bucket(arr[i], len, minAndMax[0], minAndMax[1]);
            maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], arr[i]) : arr[i];
            mins[bid] = hasNum[bid] ? Math.min(mins[bid], arr[i]) : arr[i];
            hasNum[bid] = true;
        }
        int result = 0;
        int lastMax = maxs[0];
        for(int i = 1; i <= len; i++) {
            if(hasNum[i]) {
                result = Math.max(result, mins[i] - lastMax);
                lastMax = maxs[i];
            }
        }
        return result;
    }

    /**
     * 获取桶号
     * @param num 当前数
     * @param len 桶的个数
     * @param min 数组最大值
     * @param max 数组最小值
     * @return
     */
    public int bucket(int num, int len, int min, int max) {
        return (num-min) /((max - min) / len);
    }

    /**
     * 返回数组的最大值和最小值
     * @param arr
     * @return
     */
    public int[] getMaxAndMin(int[] arr) {
        int[] result = new int[]{arr[0], arr[0]};
        for(int tmp : arr) {
            result[0] = tmp < result[0] ? tmp : result[0];
            result[1] = tmp > result[1] ? tmp : result[1];
        }
        return result;
    }

    public static int bucket(long num, long len, long min, long max) {
        return (int) ((num - min) * len / (max - min));
    }

    public static void main(String[] args) {
        MaxGap gap = new MaxGap();
        System.out.println(gap.bucket(79, 10, 0, 100));
        System.out.println(bucket(79L, 10L, 0L, 100L));
    }

}
