package com.nzl.common.util;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.NoArgsConstructor;
import com.alibaba.fastjson.JSONObject;

/**
 * @author: nizonglong
 * @date: 2020/3/16 13:23
 * @desc: Json和Object的互相转换，转List必须Json最外层加[]，转Object，Json最外层不要加[]
 * @version: 0.1
 **/
@NoArgsConstructor
public class JsonConvertUtil {
    /**
     * JSON 转 Object
     */
    public static <T> T jsonToObject(String pojo, Class<T> clazz) {
        return JSONObject.parseObject(pojo, clazz);
    }

    /**
     * Object 转 JSON
     */
    public static <T> String objectToJson(T t){
        return JSONObject.toJSONString(t);
    }
}
