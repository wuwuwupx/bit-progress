package com.bitprogress.json;

import com.bitprogress.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtilsTest {

    public static void main(String[] args) {
//        testSerializeObject();
//        testDeserializeObject();
//        testSerializeObjectAlways();
//        testDeserializeObjectWithUnknownProperties();
//        testDeserializeObjectByType();
        testDeserializeObjectByType3();
//        testDeserializeList();
//        testDeserializeSet();
//        testDeserializeMap();
    }

    private static void testSerializeObject() {
        TestObject obj = new TestObject("test", 123);
        String json = JsonUtils.serializeObject(obj);
        assert json != null;
        System.out.println(json);
        System.out.println("testSerializeObject passed");
    }

    private static void testSerializeObjectAlways() {
        TestObject obj = new TestObject("test", null);
        String json = JsonUtils.serializeObject(obj, "", true);
        String json1 = JsonUtils.serializeObject(obj, false);
        System.out.println(json);
        System.out.println(json1);
        System.out.println("testSerializeObjectAlways passed");
    }

    private static void testDeserializeObject() {
        String json = "{\"name\":\"test\",\"value\":123}";
        TestObject obj = JsonUtils.deserializeObject(json, TestObject.class);
        assert obj != null;
        assert "test".equals(obj.getName());
        assert 123 == obj.getValue();
        System.out.println("testDeserializeObject passed");
    }

    private static void testDeserializeObjectByType() {
        String json = "[{\"name\":\"test1\",\"value\":123},{\"name\":\"test2\",\"value\":456}]";
        List<TestObject> list = testDeserializeObjectByType(json, List.class, TestObject.class);
        list.forEach(obj -> System.out.println(obj.getName() + " " + obj.getValue()));
    }

    private static <T, R> T testDeserializeObjectByType(String json, Class<T> tClass, Class<R> rClass) {
        JavaType javaType = JsonUtils.getTypeFactory().constructParametricType(tClass, rClass);
        return JsonUtils.deserializeObject(json, javaType);
    }

    private static void testDeserializeObjectByType3() {
        String json = "{\"name\":\"test\",\"data\":[{\"name\":\"test1\",\"value\":123},{\"name\":\"test2\",\"value\":456}],\"data1\":{\"name\":\"test\",\"value\":123}}";
//        testDeserializeObjectByType3(json, TestObject.class);
        List<TestObject> list = testDeserializeObjectByType3(json, TestObject.class);
        list.forEach(obj -> System.out.println(obj.getName() + " " + obj.getValue()));
    }

    private static <R> List<R> testDeserializeObjectByType3(String json, Class<R> rClass) {
        TestOuter<List<R>> testOuter = JsonUtils.deserializeObject(json, TestOuter.class, List.class, rClass);
        System.out.println(testOuter.getName());
        return testOuter.getData();
    }

    private static void testDeserializeObjectWithUnknownProperties() {
        String json = "{\"name\":\"test\",\"value\":123,\"unknown\":\"unknown\"}";
        TestObject obj = JsonUtils.deserializeObject(json, TestObject.class);
        String s = JsonUtils.serializeObject(obj);
        System.out.println(s);
    }

    private static void testDeserializeList() {
        String json = "[{\"name\":\"test1\",\"value\":123},{\"name\":\"test2\",\"value\":456}]";
        List<TestObject> list = JsonUtils.deserializeObject(json, new TypeReference<ArrayList<TestObject>>() {});
        list.forEach(obj -> System.out.println(obj.getName() + " " + obj.getValue()));
    }

    private static void testDeserializeSet() {
        String json = "[{\"name\":\"test1\",\"value\":123},{\"name\":\"test2\",\"value\":456}]";
        Set<TestObject> set = JsonUtils.deserializeObject(json, new TypeReference<Set<TestObject>>() {});
        assert set != null;
        assert set.size() == 2;
        System.out.println("testDeserializeSet passed");
    }

    private static void testDeserializeMap() {
        String json = "{\"key1\":{\"name\":\"test1\",\"value\":123},\"key2\":{\"name\":\"test2\",\"value\":456}}";
        Map<String, TestObject> map = JsonUtils.deserializeObject(json, new TypeReference<Map<String, TestObject>>() {});
        assert map != null;
        assert map.size() == 2;
        assert "test1".equals(map.get("key1").getName());
        assert 123 == map.get("key1").getValue();
        assert "test2".equals(map.get("key2").getName());
        assert 456 == map.get("key2").getValue();
        System.out.println("testDeserializeMap passed");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class TestOuter<T> {
        private String name;
        private T data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestObject {
        private String name;
        private Integer value;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestObjectA {
        private String name;
        private Integer value;

    }

}