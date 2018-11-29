package code_java.io;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.zip.CRC32;

/**
 * @author yht
 * @create 2018/11/24
 */
public class MamoryMapTest {

    public static void main(String[] args) {
        System.out.println("Input Stream:");
        long start = System.currentTimeMillis();
        //获取文件路径
        Path fileName = Paths.get("E:", "study", "base.java_api", "constant-values.html");
        long crcValue = checkSumInputStream(fileName);
        long end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println(end - start + " milliseconds");

        System.out.println("Buffered Input Stream: ");

        start = System.currentTimeMillis();
        //获取文件路径
        crcValue = checkSumBufferedInputStream(fileName);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println(end - start + " milliseconds");

        System.out.println("Random Access File: ");

        start = System.currentTimeMillis();
        //获取文件路径
        crcValue = checkSumRandomAccessFile(fileName);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println(end - start + " milliseconds");

        System.out.println("Mapped File: ");

        start = System.currentTimeMillis();
        //获取文件路径
        crcValue = checkSumMappedFile(fileName);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println(end - start + " milliseconds");
    }

    private static long checkSumMappedFile(Path fileName) {
        CRC32 crc32 = new CRC32();
        try (FileChannel channel = FileChannel.open(fileName)) {
            int length = (int) channel.size();
            //map(MapModel, position, length)
            //从通道中获得一个ByteBuffer position 映射文件的位置 length 映射大小
            //MapModel 映射模式：
            // FileChannel.MapMode.READ_ONLY 只读  READ_WRITE 可读可写 PRIVATE 缓冲区可写，但不会修改文件
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);

            for(int p = 0; p < length; p++) {
                int c = buffer.get();
                crc32.update(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc32.getValue();
    }

    private static long checkSumRandomAccessFile(Path fileName) {
        CRC32 crc32 = new CRC32();
        //File toFile() 将Path转换为File类
        try (RandomAccessFile file = new RandomAccessFile(fileName.toFile(), "r")) {
            long length = file.length();
            for (long p = 0; p < length; p++) {
                file.seek(p);
                int c = file.readByte();
                crc32.update(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc32.getValue();
    }

    private static long checkSumBufferedInputStream(Path fileName) {
        CRC32 crc = new CRC32();
        try (InputStream in = new BufferedInputStream(Files.newInputStream(fileName))) {
            int c;
            while ((c = in.read()) != -1) {
                crc.update(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc.getValue();
    }

    private static long checkSumInputStream(Path fileName) {
        CRC32 crc = new CRC32();
        //返回一个文件输入流
        try (InputStream in = Files.newInputStream(fileName)) {

            int c;
            while ((c = in.read()) != -1) {
                crc.update(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc.getValue();
    }
}
