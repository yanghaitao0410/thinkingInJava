package code_java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Desc
 * @Author water
 * @date 2020/8/17
 **/
public class StreamTest {

    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        list.add("2342342");
        list.add("dfghdfgh");
        list.add("2342342");
        list.add("fgtresg45");
        list.add("546uytyhgtrhe");
        list.add("546uytyhgtrhe");
        list.add("fgtresg45");

//        System.out.println(list.stream().reduce((r1, r2) ->  r1.concat(",").concat(r2)).get());
//
//        System.out.println(list.stream().distinct().collect(Collectors.toList()));
//        System.out.println(list.stream().distinct().collect(Collectors.toList()));
//        System.out.println(list.stream().distinct().collect(Collectors.toList()));

        String str = list.stream().map(x -> x).reduce((x1, x2) -> x1.concat(",").concat(x2)).get();
        System.out.println("str" + str);

    }

}
