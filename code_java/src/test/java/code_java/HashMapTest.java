package code_java;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * @author yht
 * @create 2018/12/6
 */
public class HashMapTest {

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<Integer, String>(2, 0.75f);
        map.put(5, "C");

        new Thread("Thread1") {
            public void run() {
                map.put(7, "B");
                System.out.println(map);
            }
        }.start();
        new Thread("Thread2") {
            public void run() {
                map.put(3, "A");
                System.out.println(map);
            }
        }.start();
    }

    @Test
    public void testSerializabie() throws IOException {
        HashMap hashMap = new HashMap();
        hashMap.put("sadf", "sdfasd");
        hashMap.put("sad", "sdfasd");
        hashMap.put("sa", "sdfasd");
        ObjectOutputStream outputStream = new ObjectOutputStream(new ByteArrayOutputStream());
        outputStream.writeObject(hashMap);
    }


}
