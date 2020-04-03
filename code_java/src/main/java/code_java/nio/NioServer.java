package code_java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yht
 * @create 2018/12/5
 */
public class NioServer {
    private int port;

    //存储用户名称
    private static HashSet<String> usersSet = new HashSet<>();

    private static final String USER_EXIST = "系统提示：该昵称已经存在，请换一个昵称";

    //分隔符
    private static String USER_CONTENT_SPILIT = "#@#";

    private Selector selector;

    private Charset charset = StandardCharsets.UTF_8;

    public NioServer(int port) {
        this.port = port;
        try {
            //打开管道和轮询
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            selector = Selector.open();

            serverSocketChannel.bind(new InetSocketAddress(port));
            //设置为非阻塞 默认为阻塞是为了向下兼容
            serverSocketChannel.configureBlocking(false);
            //将选择器注册到管道中 参数类型为接受客户端请求
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("服务已启动，监听端口是：" + this.port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listener() throws IOException, InterruptedException {
        while (true) {
            //在轮询，当前有多少客户端正在连接
            int wait = selector.select();
            if(wait == 0) {
                continue;
            }
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                    iterator.remove();
                    process(key);
            }
        }
    }

    private void process(SelectionKey key) throws IOException {
        //确定客户端已经做好连接准备,向客户端发送数据
        //客户端刚连接过来是accept状态
        if(key.isAcceptable()) {
            //获取当前管道
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            //获取连接客户端管道
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            //注册下一次通信状态为读取
            client.register(selector, SelectionKey.OP_READ);

            //将此对应的channel设置为准备接受其他客户端请求
            key.interestOps(SelectionKey.OP_ACCEPT);

            client.write(charset.encode("请输入你的昵称") );

        }
        //处理来自客户端的数据请求，客户端向服务器发送的数据
        if(key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder builder = new StringBuilder();
            while (client.read(buffer) > 0) {
                buffer.flip();
                builder.append(charset.decode(buffer));
                System.out.println(builder.toString());
            }
            //将此对应的channel设置为接受下一次请求
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    public static void main(String[] args) {
        NioServer server = new NioServer(8080);
        try {
            server.listener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
