package thinkingInJava._14chapter;

import org.junit.Test;
import thinkingInJava._14chapter.entity.Pet;
import thinkingInJava._14chapter.typeinfo.ForNameCreator;
import thinkingInJava._14chapter.typeinfo.LiteralPetCreator;
import thinkingInJava._14chapter.typeinfo.PetCount;
import thinkingInJava._14chapter.typeinfo.PetCreator;

import static thinkingInJava._14chapter.typeinfo.PetCount.countPets;

/**
 * @author yht
 * @create 2020/5/11
 */
public class PetCountTest {

    @Test
    public void testForNameCreator() {
        PetCreator creator = new ForNameCreator();
        PetCount.PetCounter counter = new PetCount.PetCounter();
        for (Pet pet : creator.createArray(20)) {
            System.out.print(pet.getClass().getSimpleName() + " ");
            countPets(pet, counter);
        }
        System.out.println();
        System.out.println(counter);
    }

    @Test
    public void testLiterPetCreator() {
        PetCreator creator = new LiteralPetCreator();
        PetCount.PetCounter counter = new PetCount.PetCounter();
        for (Pet pet : creator.createArray(20)) {
            System.out.print(pet.getClass().getSimpleName() + " ");
            countPets(pet, counter);
        }
        System.out.println();
        System.out.println(counter);
    }
}
