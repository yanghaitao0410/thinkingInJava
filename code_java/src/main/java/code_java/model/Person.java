package code_java.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Desc
 * @Author water
 * @date 2020/6/9
 **/
public class Person {
    private String name;
    private int age;
    @JSONField(name = "FName")
    private String FName;
    private String FAge;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getFAge() {
        return FAge;
    }

    public void setFAge(String FAge) {
        this.FAge = FAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Person person = (Person) o;

        if (age != person.age)
            return false;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (age/4);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
