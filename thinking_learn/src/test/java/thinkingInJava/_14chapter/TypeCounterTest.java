package thinkingInJava._14chapter;

import org.junit.Test;
import thinkingInJava._14chapter.entity.Pet;
import thinkingInJava._14chapter.typeinfo.LiteralPetCreator;
import thinkingInJava._14chapter.typeinfo.PetCreator;
import thinkingInJava._14chapter.typeinfo.TypeCounter;

public class TypeCounterTest {

    @Test
    public void test() {
        TypeCounter counter = new TypeCounter(Pet.class);
        PetCreator creator = new LiteralPetCreator();
        for(Pet pet : creator.createArray(20)) {
            System.out.print(pet.getClass().getSimpleName() + " ");
            counter.count(pet);
        }
        System.out.println();
        System.out.println(counter);
    }
}
