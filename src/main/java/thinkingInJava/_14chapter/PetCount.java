package thinkingInJava._14chapter;

import org.junit.Test;
import thinkingInJava._14chapter.entity.*;

import java.util.HashMap;

/**
 * 使用静态 instanceof 确定实例类型计数
 */
public class PetCount {
    static class PetCounter extends HashMap<String, Integer> {
        public void count(String type) {
            Integer quantity = get(type);
            if (quantity == null) {
                put(type, 1);
            } else {
                put(type, quantity + 1);
            }
        }
    }

    public static void countPets(Pet pet, PetCounter counter) {

        if (pet instanceof Pet) {
            counter.count("Pet");
        }
        if (pet instanceof Dog) {
            counter.count("Dog");
        }
        if (pet instanceof Mutt) {
            counter.count("Mutt");
        }
        if (pet instanceof Pug) {
            counter.count("Pug");
        }
        if (pet instanceof Cat) {
            counter.count("Cat");
        }
        if (pet instanceof Manx) {
            counter.count("Manx");
        }
        if (pet instanceof Cymric) {
            counter.count("Cymric");
        }
        if (pet instanceof Rodent) {
            counter.count("Rodent");
        }
        if (pet instanceof Rat) {
            counter.count("Rat");
        }
        if (pet instanceof Mouse) {
            counter.count("Mouse");
        }
        if (pet instanceof Hamster) {
            counter.count("Hamster");
        }

    }

    @Test
    public void testForNameCreator() {
        PetCreator creator = new ForNameCreator();
        PetCounter counter = new PetCounter();
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
        PetCounter counter = new PetCounter();
        for (Pet pet : creator.createArray(20)) {
            System.out.print(pet.getClass().getSimpleName() + " ");
            PetCount.countPets(pet, counter);
        }
        System.out.println();
        System.out.println(counter);
    }

}
