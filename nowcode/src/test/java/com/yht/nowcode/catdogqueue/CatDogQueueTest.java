package com.yht.nowcode.catdogqueue;

import org.junit.Test;

public class CatDogQueueTest {

    @Test
    public void test() {
        CatDogQueue queue = new CatDogQueue();
        queue.add(new Dog("dog1"));
        queue.add(new Cat("cat1"));
        queue.add(new Cat("cat2"));
        queue.add(new Dog("dog2"));
        queue.add(new Cat("cat3"));
        queue.add(new Dog("dog3"));
        queue.add(new Cat("cat4"));
        queue.add(new Dog("dog4"));
        queue.add(new Cat("cat5"));
        queue.add(new Dog("dog5"));
        queue.add(new Dog("dog6"));
        queue.add(new Cat("cat6"));

        System.out.println("peek:" + queue.peek().getType());

        while(!queue.isDogEmpty()) {
            System.out.format("%s ", queue.pollDog().getType());
        }
        System.out.println();
        while(!queue.isCatEmpty()) {
            System.out.format("%s ", queue.pollCat().getType());
        }

//        while(!queue.isEmpty()) {
//            System.out.format("%s ", queue.pollAll().getType());
//        }
    }
}
