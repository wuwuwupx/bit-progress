package com.bitprogress.util;

import com.bitprogress.basemodel.IJson;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * json转换工具类
 */
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    public static final String EMPTY_JSON = "{}";

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 如果需要序列化的对象为空，直接返回空字符串
     *
     * @param o 序列化的对象
     * @return 序列化后的字符串
     */
    public static String serializeObject(Object o) {
        if (o == null) {
            return "";
        }
        if (o instanceof IJson iJson) {
            return iJson.toJson();
        }
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error("serialize {} errorMessage {} ", o, e.getMessage(), e);
            throw new RuntimeException("序列化异常" + e.getMessage());
        }
    }

    /**
     * 反序列化json字符串为对象
     *
     * @param json   json字符串
     * @param target 目标类型
     * @return 反序列化后的对象
     */
    public static <T> T deserializeObject(String json, Class<T> target) {
        try {
            return mapper.readValue(json, target);
        } catch (JsonProcessingException e) {
            logger.error("deserializeObject {} errorMessage {} ", json, e.getMessage(), e);
            throw new RuntimeException("反序列化异常" + e.getMessage());
        }
    }

    /**
     * 反序列化json字符串为对象，用于泛型转换
     *
     * @param json          json字符串
     * @param typeReference 类型辅助
     * @param <T>           目标类型
     * @return 反序列化后的对象
     */
    public static <T> T deserializeObject(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            logger.error("deserializeObject {} errorMessage {} ", json, e.getMessage(), e);
            throw new RuntimeException("反序列化异常" + e.getMessage());
        }
    }

    /**
     * 反序列化json字符串为list
     *
     * @param json json字符串
     * @param <T>  目标类型
     * @return 反序列化后的list
     */
    public static <T> List<T> deserializeList(String json, TypeReference<List<T>> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            logger.error("deserializeList {} errorMessage {} ", json, e.getMessage(), e);
            throw new RuntimeException("反序列化异常" + e.getMessage());
        }
    }

    /**
     * 反序列化json字符串为set
     *
     * @param json json字符串
     * @param <T>  目标类型
     * @return 反序列化后的set
     */
    public static <T> Set<T> deserializeSet(String json, TypeReference<Set<T>> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            logger.error("deserializeSet {} errorMessage {} ", json, e.getMessage(), e);
            throw new RuntimeException("反序列化异常" + e.getMessage());
        }
    }

    /**
     * 反序列化json字符串为map
     *
     * @param json json字符串
     * @param <T>  目标类型
     * @return 反序列化后的map
     */
    public static <T, R> Map<T, R> deserializeMap(String json, TypeReference<Map<T, R>> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            logger.error("deserializeSet {} errorMessage {} ", json, e.getMessage(), e);
            throw new RuntimeException("反序列化异常" + e.getMessage());
        }
    }

}
