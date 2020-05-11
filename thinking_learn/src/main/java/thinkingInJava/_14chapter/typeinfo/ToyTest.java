package thinkingInJava._14chapter.typeinfo;

import java.lang.reflect.Field;

interface HasBatteries {
}

interface Warerproof {
}

interface Shoots {
}

class Toy {
    Toy() {
    }

    Toy(int i) {
    }
}

class FancyToy extends Toy implements HasBatteries, Warerproof, Shoots {
    FancyToy(){
     super(1);
    }
}

public class ToyTest {
    static void printInfo(Class cc) {
        System.out.println("Class name : " + cc.getName() + " is interface ? [" + cc.isInterface() + "]");
        System.out.println("Simple name :" + cc.getSimpleName());   // 不含包名的类名
        System.out.println("Canonical name : " + cc.getCanonicalName()); //全限定类名
    }
    public static void main(String [] args) {
        Class c = null;
        try {
            /**
             * 在传递给forName()的字符串中，必须使用全限定名（包含包名）
             */
            c = Class.forName("thinkingInJava._14chapter.typeinfo.FancyToy");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find FancyToy");
            System.exit(1);
        }
        printInfo(c);
        for(Class face : c.getInterfaces()) {
            printInfo(face);
        }
        Class up = c.getSuperclass();
        Object obj = null;
        try {
            /**
             * 虚拟构造器：创建实例
             *      不知道类的具体类型，但是无论如何要正确创建该类
             *  在本例中，up仅仅只是一个Class的引用，在编译期不具备任何更进一步的类型信息
             *  当创建新实例的时候，会得到Object引用，但这个引用指向的是Toy对象
             *  newInstance()来创建的类，必须带有默认的构造器。
             *
             */
            obj = up.newInstance();
        } catch (InstantiationException e) {
            System.out.println("Can't instantiate");
            System.exit(1);
        } catch (IllegalAccessException e) {
            System.out.println("Can't access");
        }
        printInfo(obj.getClass());

        System.out.println("--------------------------");
        printClass("java.lang.String");

        System.out.println("--------------------------");
        printClass("org.apache.poi.ss.usermodel.CellType");
    }

    /**
     * 递归打印输入类所在继承体系中的所有类
     * @param className 类名：需要包含包名
     */
    public static void printClass(String className) {
        try {
            Class clazz = Class.forName(className);
            Class superClass = clazz.getSuperclass();

            if(superClass != null) {
                printClass(superClass.getName());
            }
            System.out.println("className :" + clazz.getSimpleName());
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields) {
                System.out.println(field.getType() + " " + field.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
