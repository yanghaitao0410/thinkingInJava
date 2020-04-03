package code_java.collection;

import org.junit.Test;

import java.util.*;

/**
 * @Desc
 * @Author water
 * @date 2020/3/9
 **/
public class MapLearn {

    @Test
    public void putNullTest() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("sdf", null);
        Collection<String> keySet = paramMap.keySet();
        List<String> list = new ArrayList<String>(keySet);
        list.stream().forEach(System.out::println);
    }
}
