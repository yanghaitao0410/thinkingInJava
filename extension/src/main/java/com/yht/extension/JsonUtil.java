package com.yht.extension;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    /**
     * clone object, just for simple pojo
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> T clonePojo(T object) {
        try {
            String templateStr = JSON.toJSONString(object);
            return JSONObject.parseObject(templateStr, (Class<T>)object.getClass());
        } catch (Exception e) {
            log.error("clone object error.", e);
        }
        return null;
    }
}
