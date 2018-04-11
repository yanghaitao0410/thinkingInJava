package thinkingInJava._14chapter;

import org.junit.Test;
import thinkingInJava._14chapter.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用Class.forName创建Class类型
 */
public class ForNameCreator extends PetCreator {

    private static List<Class<? extends Pet>> types = new ArrayList<>();

    private static String[] typeNames = {
            Mutt.class.getName(),
            Pug.class.getName(),
            EgyptianMau.class.getName(),
            Manx.class.getName(),
            Cymric.class.getName(),
            Rat.class.getName(),
            Mouse.class.getName(),
            Hamster.class.getName()
    };

    @SuppressWarnings("unchecked")
    private static void loader() {
        try {
            for (String name : typeNames) {
                types.add((Class<? extends Pet>)Class.forName(name));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static{
        loader();
    }

    @Override
    public List<Class<? extends Pet>> types() {
        return types;
    }

    @Test
    public void testClass() {
        System.out.println(Mutt.class.getName());
    }
}
