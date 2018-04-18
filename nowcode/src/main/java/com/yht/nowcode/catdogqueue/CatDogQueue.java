package com.yht.nowcode.catdogqueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 实现一种猫狗队列的结构，要求实现如下功能：
 *      add方法：将Cat实例或Dog实例放入队列中
 *      pollAll方法：将队列中的所有实例按照进入队列的先后顺序依次弹出
 *      pollCat方法：将队列中的Cat实例按照进入队列的先后顺序依次弹出
 *      pollDog方法：将队列中的Dog实例按照进入队列的先后顺序依次弹出
 *      isEmpty方法：检查队列中是否还有dog或cat实例
 *      isDogEmpty方法：检查队列中是否还有dog实例
 *      isCatEmpty方法：价查队列中是否还有cat实例
 *
 *      实现思路：内部维护两个队列，并有一个序列号
 *          当add时将序列号存入对应实例中，序列号自增
 *          出队时，pollCat、pollDog分别返回自己队列中第一个入队元素
 *          pollAll时，比较catQueue和dogQueue中队首元素的序列号，谁小返回谁
 *
 */
public class CatDogQueue {
    private Queue<PetEnterQueue> catQueue;
    private Queue<PetEnterQueue> dogQueue;
    private int index;

    public CatDogQueue() {
        catQueue = new LinkedList<>();
        dogQueue = new LinkedList<>();
        index = 0;
    }

    public boolean isDogEmpty() {
        return dogQueue.isEmpty();
    }

    public boolean isCatEmpty() {
        return catQueue.isEmpty();
    }

    public boolean isEmpty() {
        return dogQueue.isEmpty() && catQueue.isEmpty();
    }

    public void add(Pet pet) {
        if(pet instanceof Cat) {
            catQueue.add(new PetEnterQueue(pet, index++));
        }
        if(pet instanceof Dog) {
            dogQueue.add(new PetEnterQueue(pet, index++));
        }
    }

    public Dog pollDog() {
        if(isDogEmpty()) {
            throw new RuntimeException("dog is null in Queue");
        }
        return (Dog) dogQueue.poll().getPet();
    }

    public Cat pollCat() {
        if(isCatEmpty()) {
            throw new RuntimeException("Cat is null in Queue");
        }
        return (Cat) catQueue.poll().getPet();
    }

    public Pet pollAll() {
        if(!isDogEmpty() && !isCatEmpty()) {
            return dogQueue.peek().getCount() < catQueue.peek().getCount() ? dogQueue.poll().getPet() : catQueue.poll().getPet();
        }
        if(!isDogEmpty()) {
            return dogQueue.poll().getPet();
        }
        if(!isCatEmpty()) {
            return catQueue.poll().getPet();
        }
        throw new RuntimeException("Queue is empty");
    }

    public Dog peekDog() {
        return (Dog) dogQueue.peek().getPet();
    }

    public Cat peekCat() {
        return (Cat) catQueue.peek().getPet();
    }

    public Pet peek() {
        return catQueue.peek().getCount() < dogQueue.peek().getCount() ? peekCat() : peekDog();
    }

}
