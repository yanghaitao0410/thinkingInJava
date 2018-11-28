package code_java.socket;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 客户端建立socket连接
 * @author yht
 * @create 2018/11/24
 */
public class SocketTest {
    public static void main(String[] args) {
        try (Socket socket = new Socket("time-a.nist.gov", 13)) {
            //设置超时时间毫秒
            socket.setSoTimeout(10000);
            Scanner in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8.name());
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (InterruptedIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
