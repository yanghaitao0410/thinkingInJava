package com.yht.nowcode.stack_queue.catdogqueue;

public class PetEnterQueue {
    private Pet pet;
    private int count;

    public PetEnterQueue (Pet pet, int count) {
        this.pet = pet;
        this.count = count;
    }

    public Pet getPet() {
        return pet;
    }

    public int getCount() {
        return count;
    }
}
