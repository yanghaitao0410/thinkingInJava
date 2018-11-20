package code_java.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CountLongWords {
    public static void main(String[] args) throws IOException {
        //Paths.get("") 两种方式 绝对路径 相对路径从项目根目录开始  .表示当前路径
        String contents = new String(
                Files.readAllBytes(Paths.get("code_java/src/main/resource/petstore-v1.xml")), StandardCharsets.UTF_8);
//                Files.readAllBytes(Paths.get("E:\\idea\\thinkingInJava\\code_java\\target\\classes\\petstore-v1.xml")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("\\PL+"));

        long count = 0;
        for(String word : words) {
            if(word.length() > 5) {
                count++;
            }
        }
        System.out.println(count);

        //long count()返回流中元素的数量
        count = words.stream().filter(x -> x.length() > 5).count();
        System.out.println(count);

        //获取并行流
        count = words.parallelStream().filter(x -> x.length() > 5).count();
        System.out.println(count);
    }
}
