package code_java.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 支持多线程连接的服务器
 * @author yht
 * @create 2018/11/24
 */
public class ThreadEchoServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8189)){
            int i = 1;
            while (true) {
                Socket incoming = server.accept();
                System.out.println("Spawning " + i++);
                Runnable runnable = new ThreadedEchoHandler(incoming);
                Thread thread = new Thread(runnable);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class ThreadedEchoHandler implements Runnable{
    private Socket incoming;

    public ThreadedEchoHandler(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {
        try (InputStream inputStream = incoming.getInputStream();
             OutputStream outputStream = incoming.getOutputStream()) {
            Scanner in = new Scanner(inputStream, StandardCharsets.UTF_8.name());
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
