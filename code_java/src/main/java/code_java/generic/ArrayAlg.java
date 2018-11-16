package code_java.generic;

import org.junit.Test;

import java.time.LocalDate;

public class ArrayAlg {

    /**
     * 泛型方法
     *      在修饰符（这里是public static）后比正常方法多一个类型变量<T extends Comparator>
     *      返回值是Pair<T>
     * @param arr
     * @param <T>
     * @return Pair<T>
     */
    public static <T extends Comparable> Pair<T> minmax(T[] arr) {
        if(arr == null || arr.length == 0)
            return null;
        T min = arr[0];
        T max = arr[0];
        for(int i = 1; i < arr.length; i++) {
            if(min.compareTo(arr[i]) > 0) {
                min = arr[i];
            }
            if(max.compareTo(arr[i]) < 0) {
                max = arr[i];
            }
        }
        return new Pair<T>(min, max);
    }

    @Test
    public void test() {
        LocalDate[] birthdays = {
                LocalDate.of(1906, 12, 9),
                LocalDate.of(1904, 12, 9),
                LocalDate.of(1907, 4, 9),
                LocalDate.of(1902, 5, 9),
        };
        Pair<LocalDate> mm = ArrayAlg.minmax(birthdays);
        byte[] bytes = {'s', 's', 's'};
        System.out.printf("min = %s\n", mm.getFirst());
        System.out.printf("max = %s\n", mm.getSecond());
    }
}
