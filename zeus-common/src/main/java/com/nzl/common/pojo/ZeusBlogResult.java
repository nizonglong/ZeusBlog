package com.nzl.common.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

/**
 * @author: nizonglong
 * @date: 2019/7/22 11:28
 * @description: Zeus-Blog自定义响应结构
 * @version: 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ZeusBlogResult {
    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     *
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;

    public static ZeusBlogResult build(Integer status, String msg, Object data) {
        return new ZeusBlogResult(status, msg, data);
    }

    public static ZeusBlogResult ok(Object data) {
        return new ZeusBlogResult(data);
    }

    public static ZeusBlogResult ok() {
        return new ZeusBlogResult(null);
    }


    public static ZeusBlogResult build(Integer status, String msg) {
        return new ZeusBlogResult(status, msg, null);
    }

    public ZeusBlogResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    /**
     * 将json结果集转化为 ZeusBlogResult 对象
     *
     * @param jsonData json数据
     * @param clazz    ZeusBlogResult 中的object类型
     * @return ZeusBlogResult
     */
    public static ZeusBlogResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, ZeusBlogResult.class);
            }

            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;

            if (data.isObject()) {
                obj = MAPPER.readValue(data.traverse(), clazz);
            } else if (data.isTextual()) {
                obj = MAPPER.readValue(data.asText(), clazz);
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json json字符串
     * @return ZeusBlogResult
     */
    public static ZeusBlogResult format(String json) {
        try {
            return MAPPER.readValue(json, ZeusBlogResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return ZeusBlogResult
     */
    public static ZeusBlogResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(java.util.List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }
}
