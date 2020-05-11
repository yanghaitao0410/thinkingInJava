package thinkingInJava.twelvechapter;

/**
 * 异常丢失情况2：
 */
public class ExceptionSilencer {
    public static void main(String[] args) {
        try {
            throw new RuntimeException();
        } finally {
            return;
        }
    }
}
