package thinkingInJava.TenChapter;

import org.junit.Test;

/**
 * 若需要创建某一个类的内部类的实例，需要在new表达式中：通过外部类的引用.new
 * 在拥有外部类对象之前是不可能创建内部类对象的，
 * 因为内部类的对象会暗暗的连接到创建它的外部类对象上，可以访问其外部类对象的所有成员
 */
public class DotNew {
    public class Inner{}

    @Test
    public void test1() {
        DotNew dotNew = new DotNew();
        DotNew.Inner dni = dotNew.new Inner();
    }

}
