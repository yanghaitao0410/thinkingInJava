package thinkingInJava._14chapter;

import org.junit.Test;
import thinkingInJava._14chapter.entity.Pet;
import thinkingInJava.util.MapUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 使用动态的 instanceof 计数 （推荐）
 * boolean class.isInstance()
 * 易扩展,若增加了实现类，只需要修改LiteralPetCreator中的allTypes
 * 其他代码无须修改
 */
public class PetCount2 {

    static class PetCounter extends LinkedHashMap<Class<? extends Pet>, Integer> {
        public PetCounter() {
            super(MapUtil.initMap(LiteralPetCreator.allTypes, 0));
        }

        public void count(Pet pet) {
            for(Map.Entry<Class<? extends Pet>, Integer> pair : entrySet()) {
                if(pair.getKey().isInstance(pet)) {
                    put(pair.getKey(), pair.getValue() + 1);
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("{");
            for(Map.Entry<Class<? extends Pet>, Integer> pair : entrySet()) {
                builder.append(pair.getKey().getSimpleName())
                        .append("=").append(pair.getValue()).append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
            builder.append("}");
            return builder.toString();
        }
    }

    @Test
    public void test() {
        PetCounter counter = new PetCounter();
        LiteralPetCreator creator = new LiteralPetCreator();
        for(Pet pet : creator.createArray(20)) {
            System.out.print(pet.getClass().getSimpleName() + " ");
            counter.count(pet);
        }
        System.out.println();
        System.out.println(counter);
    }

}
