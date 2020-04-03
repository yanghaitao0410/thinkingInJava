package code_java.stream;

import java.util.stream.IntStream;

/**
 * @author yht
 * @create 2020/2/24
 */
public class IntStreamLearn {

    public static void main(String[] args) {
        //range(startInclusive, endExclusive) 返回从startInclusive(包括)到endExclusive(不包括)的每个数增量为1的递增IntStream。
        int sum = IntStream.range(1, 5)
                .filter(e -> e > 2) //选出大于2的数

                //返回由该流的元素组成的流，当元素从结果流中使用时，对每个元素额外执行提供的操作。
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(e -> e * e)
                .peek(e -> System.out.println("Mapped value: " + e))
                .sum();

        System.out.println(sum);
    }
}
