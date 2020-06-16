package code_java.collection;

/**
 * @Desc
 * @Author water
 * @date 2020/6/15
 **/
public class Key {

    private static int keyFinalizeCount;

    public static int getKeyFinalizeCount() {
        return keyFinalizeCount;
    }

    @Override
    protected void finalize() throws Throwable {
        keyFinalizeCount++;
    }
}
