package code_java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yht
 * @create 2020/2/3
 */
public class ArrayListTest {

    @Test
    public void test() {
        List<Integer> list = new ArrayList(20);
        list.add(3, 1);
        System.out.println(list.size());
        list.stream().forEach(System.out::println);
    }
}
