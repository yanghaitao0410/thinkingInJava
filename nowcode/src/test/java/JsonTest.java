import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * @Desc
 * @Author water
 * @date 2019/11/19
 **/
public class JsonTest {

    /**
     * 测试替换json字符串中单引号为双引号
     */
    @Test
    public void testReplaceSingleQuotes() {
        String singleQuotesJsonStr =
                "[{'t': 10, 'a': 100, 'n': 1, 'r': 0, 'rn': 0, 'va': 0, 'vn': 0, 'vr': 0}, {'t': 11, 'a': 30, 'n': 1, 'r': 30, 'rn': 1, 'va': 0, 'vn': 0, 'vr': 0}, {'t': 13, 'a': 1, 'n': 1, 'r': 0, 'rn': 0, 'va': 0, 'vn': 0, 'vr': 0}, {'t': 14, 'a': 1, 'n': 1, 'r': 0, 'rn': 0, 'va': 0, 'vn': 0, 'vr': 0}, {'t': 15, 'a': 20, 'n': 2, 'r': 0, 'rn': 0, 'va': 0, 'vn': 0, 'vr': 0}, {'t': 16, 'a': 2, 'n': 2, 'r': 0, 'rn': 0, 'va': 0, 'vn': 0, 'vr': 0}]";
        singleQuotesJsonStr = singleQuotesJsonStr.replaceAll("'", "\"");
        JSONArray jsonArray = JSONArray.parseArray(singleQuotesJsonStr);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonObject.getInteger("t"));
    }
}
