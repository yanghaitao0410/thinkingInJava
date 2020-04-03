package code_java;

import java.lang.reflect.Field;

/**
 * @author yht
 * @create 2018/12/7
 */
public class SwapInteger {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer i1 = 1, i2 = 2;
        System.out.printf("before i1: %d, i2:%d\n", i1, i2);
        swap(i1, i2);
        System.out.printf("after i1: %d, i2:%d\n", i1, i2);
    }

    private static void swap(Integer i1, Integer i2) throws NoSuchFieldException, IllegalAccessException {
        int tmp = i1;
        //基本类型传递的是值的副本，非基本类型传递的是引用的副本，这里进行的操作只对副本有影响，对原始的引用无影响，
        // 若想修改原始引用指向的内存地址中值，需要通过反射的方式直接修改
        Field field = Integer.class.getDeclaredField("value");
        //需要将accessible设置为true表示不进行java语言访问检查
        field.setAccessible(true);
        field.set(i1, i2.intValue());
        //这里传入的第二个参数会自动装箱，因为 128> tmp> -127 会返回Integer缓存数组中的之前值为1位置的值，
        // 但是因为上面已经改成2了所以set的其实是2
//        field.set(i2, tmp);
        //想交换成功需要创建新对象，不使用缓存
        field.set(i2, new Integer(tmp));
    }


}
