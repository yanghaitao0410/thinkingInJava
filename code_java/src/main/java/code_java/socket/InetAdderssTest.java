package code_java.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author yht
 * @create 2018/11/24
 */
public class InetAdderssTest {
    public static void main(String[] args) throws UnknownHostException {
        //通过主机名获取网络地址
        InetAddress[] addersses = InetAddress.getAllByName("www.horstmann.com");
        InetAddress[] addersses2 = InetAddress.getAllByName("google.com");
        for (InetAddress a : addersses) {
            System.out.println(a);
        }
        for (InetAddress a : addersses2) {
            System.out.println(a);
        }
        System.out.println();
        //获得本地主机地址
        InetAddress localHostAddress = InetAddress.getLocalHost();
        System.out.println(localHostAddress);
    }

}
