package code_java;

import code_java.model.Person;
import com.alibaba.fastjson.JSONObject;

import com.alibaba.fastjson.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JSONObjectLearnTest {
    @Test
    public void test() {
        JSONObject jsonObject = JSONObject.parseObject("{\"error_msg\":\"操作成功\",\"error_code\":0}");

        List<String> targetMerchantIdList = jsonObject.getObject("targetMerchantIdList", new TypeReference<List<String>>(){});


        Assert.assertEquals("0", jsonObject.getString("error_code"));


    }

    @Test
    public void test2() {
        Person person = new Person();
        person.setFAge("20");
        person.setFName("sdf");
        System.out.println(JSONObject.toJSONString(person));

    }
}