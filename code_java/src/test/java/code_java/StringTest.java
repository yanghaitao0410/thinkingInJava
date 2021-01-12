package code_java;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Desc
 * @Author water
 * @date 2020/4/7
 **/
public class StringTest {

    @Test
    public void testFormat() {
        System.out.println(String.format("sdfas%s", ""));

        List<String> list = new LinkedList<>();
        list.addAll(Stream.of("3019418400,4515412198,8516417261,9714413371,5916410212,4610413803,3819315001,5714318316,8211118473,6214315840,6317411953,5919412618,1518319036,3016117824,9117419832,0518417341,8215310971,6910119057,8207104755,8718413831".split(",")).collect(Collectors.toList()));

        System.out.println(list);
    }

    @Test
    public void testFormat2() {
        System.out.println(String.format("xcvasd:%s sdfas", null));
    }

}
