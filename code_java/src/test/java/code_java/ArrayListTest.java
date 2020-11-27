package code_java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    public void removeAllTest() {
        List<Integer> list = new ArrayList<>(16);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        list.removeAll(Stream.of(2, 4).collect(Collectors.toList()));

        list.stream().forEach(System.out::println);
    }

}
