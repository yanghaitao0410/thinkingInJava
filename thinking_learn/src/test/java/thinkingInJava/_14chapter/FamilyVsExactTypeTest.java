package thinkingInJava._14chapter;

import org.junit.Test;
import thinkingInJava._14chapter.typeinfo.Base;
import thinkingInJava._14chapter.typeinfo.Derived;
import thinkingInJava._14chapter.typeinfo.FamilyVsExactType;

/**
 * @author yht
 * @create 2020/5/11
 */
public class FamilyVsExactTypeTest {

    @Test
    public void test() {
        FamilyVsExactType.test(new Base());
        FamilyVsExactType.test(new Derived());
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
