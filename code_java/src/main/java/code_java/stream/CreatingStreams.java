package code_java.stream;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 生成流的各种方法
 */
public class CreatingStreams {

    public static void main(String[] args) throws IOException {
        Path currentDir = Paths.get("code_java/src/main/resource/word.txt");
        //toAbsolutePath()获取实际路径
        System.out.println(currentDir.toAbsolutePath());
        String contents = new String(Files.readAllBytes(currentDir), StandardCharsets.UTF_8);
        Stream<String> words = Stream.of(contents.split("\\PL+"));
        show("words", words);

        //将数据转为流
        Stream<String> song = Stream.of("gently", "down", "the", "stream");
        show("song", song);

        //创建空流
        Stream<String> silence = Stream.empty();
        show("silence", silence);

        //generate(Supplier<T> s) 创建无限流，填充的值为lamada表达式 Supplier get()提供的值
        Stream<String> echos = Stream.generate(() -> "Echo");
        show("echos", echos);

        Stream<Double> randoms = Stream.generate(Math::random);
        show("ramdom", randoms);

        //iterate(seed, lamada) 产生无限序列 从seed开始  下一位由lamada表达式计算得出
        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE, x -> x.add(BigInteger.ONE));
        show("integers", integers);

        //正则表达式可以将匹配得元素转化为流
        Stream<String> wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(contents);
        show("wordsAnotherWay", wordsAnotherWay);

        //lines(path, [cs]) 返回一个包含文件中所有行的Stream
        try (Stream<String> lines = Files.lines(currentDir, StandardCharsets.UTF_8)){
            show("lines", lines);
        }
    }

    public static <T> void show(String title, Stream<T> stream) {
        final int  SIZE = 10;
        //stream.limit(n) 获取长度为n的新流
        //Collectors.toList() 将元素转化为集合
        List<T> firstElements = stream.limit(SIZE + 1).collect(Collectors.toList());
        System.out.print(title + ": ");
        for(int i = 0; i < firstElements.size(); i++) {
            if(i > 0) {
                System.out.print(",");
            }
            if(i < SIZE) {
                System.out.print(firstElements.get(i));
            } else {
                System.out.print("...");
            }
        }
        System.out.println();
    }
}
