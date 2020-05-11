package thinkingInJava._13chapter.fommatter;

import java.io.PrintStream;
import java.util.Formatter;

public class Turtle {
    private String name;
    /**
     * Formatter:一个翻译器，将需要格式化的字符串与数据翻译成需要的结果
     * 构造方法：传入最终输出位置
     *      可以是PrintStream(eg:System.out)、OutputStream、File
     */
    private Formatter formatter;
    public Turtle(String name, Formatter formatter) {
        this.name = name;
        this.formatter = formatter;
    }

    public void move(int x, float y) {
        formatter.format("%s The Turtle is at (%d, %f)\n", name, x, y);
    }

    public static void main(String[] args) {
        PrintStream outAlias = System.out; //使用别名也是可以的
        Turtle tommy = new Turtle("Tommy", new Formatter(System.out));
        Turtle terry = new Turtle("Terry", new Formatter(outAlias));
        tommy.move(0, 0);
        terry.move(3, 3);
        tommy.move(5, 5);
        terry.move(5, 4);

        /** 运行结果：
         *   Tommy The Turtle is at (0, 0)
             Terry The Turtle is at (3, 3)
             Tommy The Turtle is at (5, 5)
             Terry The Turtle is at (5, 4)
         */
    }
}
