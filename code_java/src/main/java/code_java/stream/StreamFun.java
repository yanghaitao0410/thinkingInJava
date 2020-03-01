package code_java.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * 流转换、处理方法
 */
public class StreamFun {
    List<String> wordList;

    @Before
    public void startUp() {
        wordList = new ArrayList<>(Arrays.asList(new String[]{"dsafa", "sdfasd", "sdfa", "sdfasdfsdfsdfasdf"}));
    }

    @Test
    public void streamDemo1() {

        //Stream<T> filter(Predicate<? super T> predicate) 产生一个满足传入lamada表达式的流
        Stream<String> longWords = wordList.stream().filter(x -> x.length() > 5);

        //<R> Stream<R> map(Function<? super T,  ? extends R> mapper)
        //产生一个将mapper应用于流中所有元素的流
        Stream<String> lowercaseWords = wordList.stream().map(String::toLowerCase);

        //产生一个包含所有单词首字母的流
        Stream<String> firstLetters = wordList.stream().map(x -> x.substring(0, 1));

        //在字符串流上映射返回流的方法，得到一个包含流的流
        Stream<Stream<String>> result = wordList.stream().map(w -> letters(w));

        //<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R> mapper)
        //产生一个将mapper应用于当前流中所有元素所产生的结果连接到一起的一个流
        Stream<String> flatResult = wordList.stream().flatMap(w -> letters(w));

        flatResult.forEach(System.out::println);

        //产生一个包含100个随机数的流
        Stream<Double> randoms = Stream.generate(Math::random).limit(100);

        //Stream<T> skip(n) 跳过n个元素 返回剩下的流
        Stream<String> words = wordList.stream().skip(1);

        //Stream<T> concat(stream1, stream2) 将两个流连接起来
        //注意：第一个流不能是无限流，否则第二个流永远都不会得到处理机会
        Stream<String> combined = Stream.concat(letters("hello"), letters("world"));

        //Stream<T> distinct() 返回流中重复数据去掉的新流，还是原来的顺序
        Stream<String> uniqueWords = Stream.of("merrily", "merrily", "merrily", "gently").distinct();

        //流的排序
        Stream<String> longestFirst = wordList.stream().sorted(Comparator.comparing(String::length).reversed());
    }

    @Test
    public void testPeekStream() {
        //Stream<T> peek(Consumer<? super T> action)
        //产生一个流，与当前流中元素相同，在获取其中每个元素的时候，将其传递给action
        Object[] powers = Stream.iterate(1.0, x -> x * 2)
                .peek(e -> System.out.println("Fetching " + e)).limit(20).toArray();

        System.out.println("========");

        for(Object object : powers) {
            System.out.println(object);
        }
    }

    /**
     * 流的终结操作
     */
    @Test
    public void terminalOperationFunction() {
        //max|min(Comparator<? super T> comparator)
        //分别产生这个流的最大元素和最小元素，使用给定比较器定义的排序规则，如果这个流为空，会产生一个空的Optional对象
        Optional<String> largest = wordList.stream().max(String::compareToIgnoreCase);
        System.out.println("largest: " + largest.orElse(""));

        Stream<String> filterStream = wordList.stream().filter(x -> x.startsWith("Q"));
        //findFirst() 返回第一个元素
        Optional<String> startsWithQ1 = filterStream.findFirst();
        System.out.println("startsWithQ1: ".concat(startsWithQ1.orElse("")));

        //findAny() 返回所有满足条件的元素
        Optional<String> startsWithQ2 = filterStream.findAny();

        //boolean anyMatch | allMatch | noneMatch (Predicate< ? super T> predicate)
        //存在、全部、没有 满足predicate条件的元素
        boolean existStartsWithQ = wordList.stream().anyMatch(x -> x.startsWith("Q"));

    }



    /**
     * 返回包含传入字符串每个字符的流
     * @param s
     * @return
     */
    public static Stream<String> letters(String s) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            result.add(s.substring(i, i + 1));
        }
        return result.stream();
    }

}
