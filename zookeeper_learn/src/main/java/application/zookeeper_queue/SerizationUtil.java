package application.zookeeper_queue;

import org.apache.zookeeper.server.ByteBufferOutputStream;

import java.io.*;

/**
 * @author yht
 * @create 2018/11/29
 */
public class SerizationUtil {

    public static byte[] toByteArr(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(object);
        return byteArrayOutputStream.toByteArray();
    }

    public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
        return inputStream.readObject();
    }
}
