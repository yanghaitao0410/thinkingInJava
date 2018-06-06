package thinkingInJava._14chapter.typeinfo;

import org.junit.Test;

class Base {}
class Derived extends Base {}

/**
 * <p>
 * instanceOf和isInstance()保持了类型的概念
 *      true 传入的对象是这个类或是这个类的派生类
 * ==和equals()比较的是实际的Class对象，没有考虑继承
 *      true 传入的类是这个确切的类型
 * </p>
 */
public class FamilyVsExactType {

    static void test(Object x) {
        String xname = "\"" + x.getClass().getSimpleName() + ":entity\"";
        System.out.format("Testing %s of type %s \n", xname, x.getClass());
        System.out.format("%s instanceof Base %s \n", xname, String.valueOf(x instanceof Base));
        System.out.format("%s instanceof Derived %s \n", xname, (x instanceof Derived));
        System.out.format("Base isInstance(%s) %s\n", xname, Base.class.isInstance(x));
        System.out.format("Derived isInstance(%s) %s\n", xname, Derived.class.isInstance(x));
        System.out.format("%s.getClass() == Base.class %s\n", xname, (x.getClass() == Base.class));
        System.out.format("%s.getClass() == Derived.class %s\n", xname, (x.getClass() == Derived.class));
        System.out.format("%s.getClass().equals(Base.class) %s\n", xname, (x.getClass().equals(Base.class)));
        System.out.format("%s.getClass().equals(Derived.class) %s\n", xname, (x.getClass().equals(Derived.class)));
        System.out.println();
    }

    @Test
    public void test() {
        test(new Base());
        test(new Derived());
    }

    /*
    * 运行结果：
    *   Testing "Base:entity" of type class thinkingInJava._14chapter.typeinfo.Base
        "Base:entity" instanceof Base true
        "Base:entity" instanceof Derived false
        Base isInstance("Base:entity") true
        Derived isInstance("Base:entity") false
        "Base:entity".getClass() == Base.class true
        "Base:entity".getClass() == Derived.class false
        "Base:entity".getClass().equals(Base.class) true
        "Base:entity".getClass().equals(Derived.class) false

        Testing "Derived:entity" of type class thinkingInJava._14chapter.typeinfo.Derived
        "Derived:entity" instanceof Base true
        "Derived:entity" instanceof Derived true
        Base isInstance("Derived:entity") true
        Derived isInstance("Derived:entity") true
        "Derived:entity".getClass() == Base.class false
        "Derived:entity".getClass() == Derived.class true
        "Derived:entity".getClass().equals(Base.class) false
        "Derived:entity".getClass().equals(Derived.class) true
    * */

}
