package thinkingInJava;

import org.junit.Test;

public class StringTest {

    @Test
    public void test() {
        String s1 = "Programming";

        String s2 = new String("Programming");

        String s3 = "Program" + "ming";

        System.out.println(s1 == s2); //false

        System.out.println(s1 == s3); //true

        System.out.println(s1 == s1.intern()); //true
    }
}


