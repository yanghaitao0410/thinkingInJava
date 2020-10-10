package code_java;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author water
 * @date 2020/9/23
 **/
public class ClassTest {

    @Test
    public void classTest() {
        List<String> list = new ArrayList<>();
        System.out.println(list.getClass().getName());
    }
}
