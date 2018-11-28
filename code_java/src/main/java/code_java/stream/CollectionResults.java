package code_java.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流中数据的处理
 */
public class CollectionResults {
    public static void main(String[] args) throws IOException {

        //获取流元素的迭代器
        Iterator<Integer> iterator = Stream.iterate(0, n -> n + 1).limit(10).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        //获取Object类型数组
        Object[] numbers = Stream.iterate(0, n -> n + 1).limit(10).toArray();
        System.out.println("Object array:" + numbers);

        try {
            Integer number = (Integer) numbers[0];
            System.out.println("number:" + number);
            Integer[] numbers2 = (Integer[]) numbers;
        } catch (ClassCastException e) {
            System.out.println(e);
        }

        //获取指定类型数组
        Integer[] number3 = Stream.iterate(0, n -> n + 1).limit(10).toArray(Integer[]::new);
        System.out.println("Integer array" + number3);

        //返回set集合
        Set<String> noVowelSet = noVowels().collect(Collectors.toSet());
        show("noVowelSet", noVowelSet);

        //指定集合实例类型
        TreeSet<String> noVowelTreeSet = noVowels().collect(Collectors.toCollection(TreeSet::new));
        show("noVowelTreeSet", noVowelTreeSet);

        //将流中元素连接在一起
        String result = noVowels().limit(10).collect(Collectors.joining());
        System.out.println("joining: " + result);

        //将流中元素连接在一起,每个元素之间增加分割
        result = noVowels().limit(10).collect(Collectors.joining(", "));
        System.out.println("joining with commas:" + result);

        //static<T> Collector<T, ?, IntSummaryStatistics> summarizingInt(ToIntFunction<? super T> mapper)
        //产生能够生成IntSummaryStatistics对象的收集器,
        //可获得将mapper应用于每个元素后所产生的结果的个数、总和、平均值、最大值和最小值
        IntSummaryStatistics summary = noVowels().collect(Collectors.summarizingInt(String::length));
        double averageWordLength = summary.getAverage();
        double maxWordLength = summary.getMax();
        System.out.println("averageWordLength: " + averageWordLength);
        System.out.println("maxWordLength: " + maxWordLength);

        //void forEach(Consumer<? super T> action)
        //在流的每个元素上调用action
        System.out.println("forEach: ");
        noVowels().limit(10).forEach(System.out::println);
    }

    private static <T> void show(String label, Set<T> set) {
        System.out.println(label + ":" + set.getClass().getName());
        System.out.println("[" + set.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]");

    }

    private static Stream<String> noVowels() throws IOException {
        String contents = new String(
                Files.readAllBytes(Paths.get("code_java/src/main/resource/word.txt")), StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));
        Stream<String> words = wordList.stream();
        return words.map(s -> s.replaceAll("[aeiouAEIOU]", ""));
    }
}
