package code_java.collection;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Desc
 * @Author water
 * @date 2020/3/11
 **/
public class JSONObjectLearn {

    @Test
    public void test() {
        JSONObject jsonObject = JSONObject.parseObject("{\"error_msg\":\"操作成功\",\"error_code\":0}");
        Assert.assertEquals("0", jsonObject.getString("error_code"));
    }
}
