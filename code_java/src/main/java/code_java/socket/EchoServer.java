package code_java.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 服务端 单线程 同一时间只能有一个客户端连接
 *
 * @author yht
 * @create 2018/11/24
 */
public class EchoServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8189)) {
            //accept()阻塞方法 监听客户端
            try (Socket incoming = server.accept()){
                InputStream inputStream = incoming.getInputStream();
                OutputStream outputStream = incoming.getOutputStream();

                try (Scanner in = new Scanner(inputStream, StandardCharsets.UTF_8.name())){
                    PrintWriter printWriter = new PrintWriter(
                            new OutputStreamWriter(outputStream, StandardCharsets.UTF_8.name()), true);
                    printWriter.println("Hello! Enter BYE to exit.");
                    boolean done = false;
                    while (!done && in.hasNextLine()) {
                        String line = in.nextLine();
                        printWriter.println("Echo: " + line);
                        if(line.trim().equals("BYE")){
                            done = true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
