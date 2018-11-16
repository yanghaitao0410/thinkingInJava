package code_java.lamada;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;

public class demo {

    public static void main(String[] args) {
        String[] arr = {"ds", "sdsdfa", "x"};
        Arrays.sort(arr, (x, y) -> x.length() - y.length());
        System.out.println(arr);
        ActionListener listener = event ->
                System.out.println("The time is " + new Date());
        Timer t = new Timer(1000, System.out::println);
        assert arr instanceof String[];

    }

}
