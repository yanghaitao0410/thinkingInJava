package com.yht.nowcode.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * 设计一种结构：有如下功能
 *      insert(key):将key加入到该结构，做到不重复加入。
 *      delete（key）：将原本在结构中的某个key移除
 *      getRandom()：等概率随机返回结构中的任意一个key
 *
 *      要求：3个方法的时间复杂度都为O(1)
 *
 *      思路：内部维护两个Map和一个int类型的index
 *          dataMap     key :  index
 *          resultMap     index : key
 *          填入数据后index++
 *          getRandom()直接使用返回resultMap中
 *              key为(int)Math.Random()*index的value  等概率生成【0， index-1】的int值
 *
 *          delete 方法删除对应key后，将index-1位置上的值移到删除位置的index上，然后index--
 *              为了确保0~index-1都有数，然后随机这上面就可以了
 */
public class RandomPool {
    private Map<String, Integer> dataMap;
    private Map<Integer, String> resultMap;
    private Integer index;

    public RandomPool() {
        dataMap = new HashMap<>();
        resultMap = new HashMap<>();
        index = 0;
    }

    public void insert(String key) {
        dataMap.put(key, index);
        resultMap.put(index, key);
        index++;
    }

    public boolean delete(String key) {
        Integer oldIndex = dataMap.get(key);
        if(oldIndex == null) {
            return false;
        }
        //若要删除的元素就是index序列的最后一个，直接删除，index--
        if(oldIndex == index - 1) {
            resultMap.remove(dataMap.get(key));
            dataMap.remove(key);
        }else {//删除index序列中间某个元素，直接将最后元素的位置更新
            String endKey = resultMap.get(index - 1);

            dataMap.put(endKey, oldIndex);
            resultMap.put(oldIndex, endKey);
            dataMap.remove(key);
            resultMap.remove(index - 1);
        }
        index--;
        return true;
    }

    public String getRandom() {
        if(dataMap.isEmpty()) {
            throw new RuntimeException("pool is empty");
        }
        return resultMap.get((int)(Math.random() * index));
    }


}
