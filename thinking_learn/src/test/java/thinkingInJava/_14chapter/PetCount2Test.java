package thinkingInJava._14chapter;

import org.junit.Test;
import thinkingInJava._14chapter.entity.Pet;
import thinkingInJava._14chapter.typeinfo.LiteralPetCreator;
import thinkingInJava._14chapter.typeinfo.PetCount2;

/**
 * @author yht
 * @create 2020/5/11
 */
public class PetCount2Test {

    @Test
    public void test() {
        PetCount2.PetCounter counter = new PetCount2.PetCounter();
        LiteralPetCreator creator = new LiteralPetCreator();
        for(Pet pet : creator.createArray(20)) {
            System.out.print(pet.getClass().getSimpleName() + " ");
            counter.count(pet);
        }
        System.out.println();
        System.out.println(counter);
    }
}
