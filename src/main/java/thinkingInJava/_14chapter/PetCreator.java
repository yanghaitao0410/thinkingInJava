package thinkingInJava._14chapter;

import thinkingInJava._14chapter.entity.Pet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class PetCreator {
    private Random random = new Random(47);

    public abstract List<Class<? extends Pet>> types();

    /**
     * create one random Pet
     * @return
     */
    public Pet randomPet() {
        int n = random.nextInt(types().size());
        try {
            return types().get(n).newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Pet[] createArray(int size) {
        Pet[] pets = new Pet[size];
        for(int i = 0; i < size; i++) {
            pets[i] = randomPet();
        }
        return pets;
    }

    public ArrayList<Pet> arrayList(int size) {
        ArrayList<Pet> list = new ArrayList<>();
        Collections.addAll(list, createArray(size));
        return list;
    }

}
