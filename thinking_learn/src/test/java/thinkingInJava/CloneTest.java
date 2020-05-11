package thinkingInJava;

import org.junit.Test;
import thinkingInJava.interview.clone_object.Car;
import thinkingInJava.interview.clone_object.MyUtil;
import thinkingInJava.interview.clone_object.Person;

public class CloneTest {

    @Test
    public void test() {
        try {
            Person p1 = new Person("Hao LUO", 33, new Car("Benz", 300));
            Person p2 = MyUtil.cloneObject(p1);   // 深度克隆
            p2.getCar().setBrand("BYD");
            // 修改克隆的Person对象p2关联的汽车对象的品牌属性
            // 原来的Person对象p1关联的汽车不会受到任何影响
            // 因为在克隆Person对象时其关联的汽车对象也被克隆了
            System.out.println(p1);
            System.out.println(p2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
