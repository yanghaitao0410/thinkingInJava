package code_java;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yht
 * @create 2020/3/4
 */
public class BooleanTest {


    @Test
    public void testBoolean() {
        Assert.assertEquals(true, !Boolean.TRUE.equals(null));
    }
}
