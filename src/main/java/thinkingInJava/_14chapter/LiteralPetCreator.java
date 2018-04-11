package thinkingInJava._14chapter;

import org.junit.Test;
import thinkingInJava._14chapter.entity.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 使用类字面量常量创建Class类型
 */
public class LiteralPetCreator extends PetCreator {

    public static final List<Class<? extends Pet>> allTypes = Collections.unmodifiableList(
            Arrays.asList(
                    Pet.class, Dog.class, Cat.class, Rodent.class, Mutt.class,
                    Pug.class, EgyptianMau.class, Manx.class, Cymric.class,
                    Rat.class, Mouse.class, Hamster.class
            ));

    private static final List<Class<? extends Pet>> types =
            allTypes.subList(allTypes.indexOf(Mutt.class), allTypes.size());

    @Override
    public List<Class<? extends Pet>> types() {
        return types;
    }

    @Test
    public void test() {
        System.out.println(types);
    }
}
