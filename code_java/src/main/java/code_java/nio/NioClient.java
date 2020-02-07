package code_java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author yht
 * @create 2018/12/5
 */
public class NioClient {

    private int port;
    private Selector selector;
    private SocketChannel client;
    private Charset charset = StandardCharsets.UTF_8;
    public NioClient(int port) {
        this.port = port;
        try {
            client = SocketChannel.open(new InetSocketAddress(this.port));
            client.configureBlocking(false);
            selector = Selector.open();
            client.register(selector, SelectionKey.OP_WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() throws IOException, InterruptedException {
        while (true) {
            client.write(charset.encode("asdfljasdfladsf"));
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public static void main(String[] args) {
        NioClient client = new NioClient(8080);
        try {
            client.sendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
