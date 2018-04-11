package thinkingInJava.util;

import thinkingInJava._14chapter.entity.Pet;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {

    public static Map<Class<? extends Pet>, Integer> initMap(List<Class<? extends Pet>> list, int defalutValue) {
        Map<Class<? extends Pet>, Integer> map = new LinkedHashMap<>();
        for(int i = 0; i < list.size(); i++) {
            map.put(list.get(i), defalutValue);
        }
        return map;
    }
}
