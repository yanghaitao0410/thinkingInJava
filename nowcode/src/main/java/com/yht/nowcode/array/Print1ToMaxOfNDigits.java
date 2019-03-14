package com.yht.nowcode.array;

/**
 * 输入数字n， 按顺序打印出从1到n位的十进制数。
 * 例如输入3：打印出1， 2， 3 到999
 *
 * 思路：由于n可能非常大，因此不能直接使用int表示数字，可以使用char数组进行存储
 * 使用回溯法得到所有的数
 * todo 试一下用long
 */
public class Print1ToMaxOfNDigits {

    private static long printCount = 0;

    public static void print1ToMaxOfNDifits(int n) {
        if(n <= 0) {
            return;
        }
        char [] number = new char[n];
        print1ToMaxOfNDifits(number, 0);
    }

    /**
     * 构造数字
     * @param number 数字数组
     * @param digit 当前数字构造到的位置
     */
    private static void print1ToMaxOfNDifits(char[] number, int digit) {
        if(digit == number.length) {
            printNumber(number);
            System.out.print(" ");
            if(printCount++ % 10 == 0) {
                System.out.println();
            }
            return;
        }
        //每位都是从0到9
        for(int i = 0; i < 10; i++) {
            number[digit] = (char) (i + '0');
            print1ToMaxOfNDifits(number, digit + 1);
        }

    }

    private static void printNumber(char[] number) {
        int index = 0;
        while(index < number.length && number[index] == '0'){
            index++;
        }
        while(index < number.length){
            System.out.print(number[index++]);
        }
    }

    public static void main(String[] args) {
        print1ToMaxOfNDifits(10);
    }

}
