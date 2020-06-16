package code_java.collection;

/**
 * @Desc
 * @Author water
 * @date 2020/6/15
 **/
public class MyObject {
    private static int valueFinalizeCount;
    private int[] bigArray = new int[10000];

    public static int getValueFinalizeCount() {
        return valueFinalizeCount;
    }

    @Override
    protected void finalize() throws Throwable {
        valueFinalizeCount++;
    }

}
