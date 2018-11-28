package code_java.socket;

import javax.swing.*;
import java.awt.*;

/**
 * 可中断Socket
 * @author yht
 * @create 2018/11/24
 */
public class InterruptibleSocketTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame jFrame = new InterruptibleSocketFrame();
            jFrame.setTitle("InterruptibleSocketTest");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setVisible(true);
        });
    }
}
