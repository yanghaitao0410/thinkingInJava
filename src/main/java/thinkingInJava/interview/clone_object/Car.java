package thinkingInJava.interview.clone_object;

import java.io.Serializable;

public class Car implements Serializable {

    private static final long serialVersionUID = -1062196479165924086L;

    private String brand;       // 品牌

    private int maxSpeed;       // 最高时速

    public Car(String brand, int maxSpeed) {

        this.brand = brand;

        this.maxSpeed = maxSpeed;

    }

    public String getBrand() {

        return brand;

    }

    public void setBrand(String brand) {

        this.brand = brand;

    }

    public int getMaxSpeed() {

        return maxSpeed;

    }

    public void setMaxSpeed(int maxSpeed) {

        this.maxSpeed = maxSpeed;

    }

    @Override

    public String toString() {

        return "Car [brand=" + brand + ", maxSpeed=" + maxSpeed + "]";

    }
}
