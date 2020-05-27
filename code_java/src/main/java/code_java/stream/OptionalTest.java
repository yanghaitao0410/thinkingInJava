package code_java.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * optional 包装类相关api
 */
public class OptionalTest {
    public static void main(String[] args) {
        try {
            String contents = new String(
                    Files.readAllBytes(Paths.get("code_java/src/main/resource/word.txt")), StandardCharsets.UTF_8);
            List<String> wordList = Arrays.asList(contents.split("\\PL+"));

            Optional<String> optionalValue = wordList.stream()
                    .filter(s -> s.contains("da"))
                    .findFirst();
            //orElse(String string) | orElseGet(Supplier<? extends T> other) | orElseThrow (Exception)
            // 返回当前Optional值，若为空：返回string | 产生调用other的结果 | 抛出异常
            System.out.println(optionalValue.orElse("No word") + " contains da");

            //Optional.empty() 返回一个空Optional
            Optional<String> optionalString = Optional.empty();
            String result = optionalString.orElse("N/A");
            System.out.println("result: " + result);
            result = optionalString.orElseGet(() -> Locale.getDefault().getDisplayName());
            System.out.println("result: " + result);

            try {
                result = optionalString.orElseThrow(IllegalStateException::new);
                System.out.println("result: " + result);
            } catch (Throwable t) {
                t.printStackTrace();
            }

            optionalValue = wordList.stream().filter(s -> s.contains("red")).findFirst();
            optionalValue.ifPresent(s -> System.out.println(s + " contains red"));

            Set<String> results = new HashSet<>();
            //void ifPresent(Consumer<? super T> consumer)
            //如果当前Optional不为空，那么就将它的值传给consumer
            optionalValue.ifPresent(results::add);
            System.out.println("Set result:" + results);
            Optional<Boolean> adder = optionalValue.map(results::add);
            System.out.println("Optional adder:" + adder);

            //<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper)
            //产生将mapper应用于当前的Optional值所产生的结果，或者在当前Optional为空时，返回一个空Optional
            System.out.println(inverse(4.0).flatMap(OptionalTest::squareRoot));
            System.out.println(inverse(0.0).flatMap(OptionalTest::squareRoot));
            System.out.println(inverse(-1.0).flatMap(OptionalTest::squareRoot));

            Optional<Double> result2 = Optional.of(-4.0).flatMap(OptionalTest::inverse).flatMap(OptionalTest::squareRoot);
            System.out.println(result2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回x的倒数的Optional包装类
     * @param x
     * @return
     */
    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    /**
     * 返回x的平方的Optional包装类
     * @param x
     * @return
     */
    public static Optional<Double> squareRoot(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}
