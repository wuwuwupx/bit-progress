package com.bitprogress.util;

import com.bitprogress.basemodel.IJson;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * json转换工具类
 */
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 默认的ObjectMapper，只序列化非空字段
     */
    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();
    private static final ObjectMapper ALWAYS_MAPPER = new ObjectMapper();


    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String EMPTY_JSON = "{}";

    static {
        DEFAULT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        DEFAULT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        DEFAULT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        DEFAULT_MAPPER.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        DEFAULT_MAPPER.registerModule(new JavaTimeModule());

        ALWAYS_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        ALWAYS_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        ALWAYS_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        ALWAYS_MAPPER.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        ALWAYS_MAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * 获取默认的 TypeFactory
     *
     * @return TypeFactory
     */
    public static TypeFactory getTypeFactory() {
        return getTypeFactory(false);
    }

    /**
     * 获取默认的 TypeFactory
     *
     * @return TypeFactory
     */
    public static TypeFactory getTypeFactory(boolean isAlways) {
        ObjectMapper objectMapper = isAlways ? ALWAYS_MAPPER : DEFAULT_MAPPER;
        return objectMapper.getTypeFactory();
    }

    /**
     * 如果需要序列化的对象为空，直接返回空字符串
     *
     * @param o 序列化的对象
     * @return 序列化后的字符串
     */
    public static String serializeObject(Object o) {
        return serializeObject(o, EMPTY_JSON, false);
    }

    /**
     * 如果需要序列化的对象为空，直接返回空字符串
     *
     * @param o        序列化的对象
     * @param isAlways 默认值
     * @return 序列化后的字符串
     */
    public static String serializeObject(Object o, boolean isAlways) {
        return serializeObject(o, EMPTY_JSON, isAlways);
    }

    /**
     * 如果需要序列化的对象为空，直接返回空字符串
     *
     * @param o           序列化的对象
     * @param defaultJson 默认值
     * @return 序列化后的字符串
     */
    public static String serializeObject(Object o, String defaultJson) {
        return serializeObject(o, defaultJson, false);
    }

    /**
     * 如果需要序列化的对象为空，直接返回空字符串
     *
     * @param o 序列化的对象
     * @return 序列化后的字符串
     */
    public static String serializeObject(Object o, String defaultJson, boolean isAlways) {
        if (o == null) {
            return defaultJson;
        }
        if (o instanceof IJson iJson) {
            return iJson.toJson();
        }
        try {
            ObjectMapper objectMapper = isAlways ? ALWAYS_MAPPER : DEFAULT_MAPPER;
            return objectMapper.writeValueAsString(o);
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
            return DEFAULT_MAPPER.readValue(json, target);
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
            return DEFAULT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            logger.error("deserializeObject {} errorMessage {} ", json, e.getMessage(), e);
            throw new RuntimeException("反序列化异常" + e.getMessage());
        }
    }

    /**
     * 反序列化json字符串为对象
     *
     * @param json   json字符串
     * @param target 目标类型
     * @return 反序列化后的对象
     */
    public static <T> T deserializeObject(String json, JavaType target) {
        try {
            return DEFAULT_MAPPER.readValue(json, target);
        } catch (JsonProcessingException e) {
            logger.error("deserializeObject {} errorMessage {} ", json, e.getMessage(), e);
            throw new RuntimeException("反序列化异常" + e.getMessage());
        }
    }

    /**
     * 反序列化json字符串为多层嵌套的泛型对象，仅针对每层一个泛型参数的情况
     *
     * @param json        json字符串
     * @param outerClass  目标类型
     * @param middleClass 中间类型
     * @param innerClass  内部类型
     * @return 反序列化后的对象
     * @throws IllegalArgumentException 如果 Middle 不符合 Outer 的泛型参数 T，或 Inner 不符合 Middle 的泛型参数 U
     */
    public static <T, R, U> T deserializeObject(String json,
                                                Class<T> outerClass,
                                                Class<R> middleClass,
                                                Class<U> innerClass) {
        // 预检 middleClass 是否符合 outerClass 的泛型参数
        matchGenericType(outerClass, middleClass);

        // 预检 innerClass 是否符合 middleClass 的泛型参数
        matchGenericType(middleClass, innerClass);

        // 中间层包含内层
        JavaType middleType = getTypeFactory()
                .constructParametricType(middleClass, innerClass);
        // 外层包含中间层
        JavaType outerType = TypeFactory
                .defaultInstance()
                .constructParametricType(outerClass, middleType);
        return deserializeObject(json, outerType);
    }

    /**
     * 匹配泛型参数类型
     *
     * @param outerClass 外层类型
     * @param innerClass 需要匹配的泛型类型
     */
    private static <T, R> void matchGenericType(Class<T> outerClass, Class<R> innerClass) {
        matchGenericTypes(outerClass, innerClass);
    }

    /**
     * 匹配泛型参数类型
     *
     * @param outerClass   外层类型
     * @param innerClasses 泛型类型
     */
    private static <T> void matchGenericTypes(Class<T> outerClass, Class<?>... innerClasses) {
        if (innerClasses == null || innerClasses.length == 0) {
            throw new IllegalArgumentException("The provided inner classes must not be empty");
        }
        TypeVariable<Class<T>>[] outerTypeVariables = outerClass.getTypeParameters();
        String outerClassName = outerClass.getName();
        if (outerTypeVariables.length == 0) {
            throw new IllegalArgumentException(outerClassName + " does not support generics");
        }
        for (Class<?> innerClass : innerClasses) {
            boolean matchFound = false;
            for (TypeVariable<Class<T>> outerTypeVariable : outerTypeVariables) {
                Type[] bounds = outerTypeVariable.getBounds();
                for (Type bound : bounds) {
                    if ((bound instanceof Class && ((Class<?>) bound).isAssignableFrom(innerClass))
                            || (bound instanceof AnnotatedType && ((AnnotatedType) bound).getType().getTypeName().equals(innerClass.getName()))) {
                        matchFound = true;
                        break;
                    }
                }
                if (matchFound) {
                    break;
                }
            }
            if (!matchFound) {
                String message = innerClass.getName() + " does not match " + outerClassName + "'s generic type";
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * 反序列化json字符串为list
     *
     * @param json          json字符串
     * @param typeReference 类型辅助
     * @return 反序列化后的list
     */
    public static <T> List<T> deserializeList(String json, TypeReference<List<T>> typeReference) {
        try {
            return DEFAULT_MAPPER.readValue(json, typeReference);
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
            return DEFAULT_MAPPER.readValue(json, typeReference);
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
            return DEFAULT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            logger.error("deserializeSet {} errorMessage {} ", json, e.getMessage(), e);
            throw new RuntimeException("反序列化异常" + e.getMessage());
        }
    }

}
