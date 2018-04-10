package thinkingInJava._13chapter;

/**
 * 类似c中printf占位输出
 */
public class SimpleFormat {

    public static void main(String [] args) {
        int x = 5;
        double y = 5.34234;
        //The old way
        System.out.println("Row 1 : [" + x + " " + y + "]");

        //The new way
        System.out.format("Row 1 : [%d %f]\n", x, y);

        //or
        System.out.printf("Row 1 : [%d %f] \n", x, y);
    }
}
