package com.nzl.common.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.http.HttpStatus;

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
public class ZeusResponseBean {
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

    public static ZeusResponseBean build(Integer status, String msg, Object data) {
        return new ZeusResponseBean(status, msg, data);
    }

    public static ZeusResponseBean ok(Object data) {
        return new ZeusResponseBean(data);
    }

    public static ZeusResponseBean ok() {
        return new ZeusResponseBean(null);
    }


    public static ZeusResponseBean build(Integer status, String msg) {
        return new ZeusResponseBean(status, msg, null);
    }

    public ZeusResponseBean(Object data) {
        this.status = HttpStatus.OK.value();
        this.msg = HttpStatus.OK.getReasonPhrase();
        this.data = data;
    }

    /**
     * 将json结果集转化为 ZeusBlogResult 对象
     *
     * @param jsonData json数据
     * @param clazz    ZeusBlogResult 中的object类型
     * @return ZeusBlogResult
     */
    public static ZeusResponseBean formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, ZeusResponseBean.class);
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
    public static ZeusResponseBean format(String json) {
        try {
            return MAPPER.readValue(json, ZeusResponseBean.class);
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
    public static ZeusResponseBean formatToList(String jsonData, Class<?> clazz) {
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
