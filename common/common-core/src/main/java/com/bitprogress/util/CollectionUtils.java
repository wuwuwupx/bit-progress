package com.bitprogress.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;

/**
 * @author wpx
 * Created on 2020/11/18 16:17
 * 集合工具类
 * 流的操作可能导致性能下降
 */
public class CollectionUtils {

    /**
     * HashTable默认期望容量
     */
    private static final int DEFAULT_EXPECTED_CAPACITY = 16;

    /**
     * HashTable默认负载因子
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;

    private static final String DEFAULT_DELIMITER = ",";

    public enum ComparableType {

        /**
         * key重复时取最大
         */
        MAX,

        /**
         * key重复时取最小
         */
        MIN,

        ;

    }

    public enum OrderType {

        /**
         * 降序排序
         */
        DESC,

        /**
         * 升序排序
         */
        ASC,
        ;

    }

    /**
     * 返回默认容量的hashMap
     *
     * @param <T> key类型
     * @param <R> value类型
     * @return 默认容量的hashMap
     */
    public static <T, R> Map<T, R> emptyMap() {
        return emptyMap(DEFAULT_EXPECTED_CAPACITY);
    }

    /**
     * 计算hashMap期望容量的初始值
     * (int) ((float) expectedSize / loadFactor + 1)
     *
     * @param expectedCapacity 期望容量
     * @param loadFactor       负载因子
     * @return hashMap的初始容量
     */
    public static int getInitialCapacity(int expectedCapacity, float loadFactor) {
        return (int) ((float) expectedCapacity / loadFactor + 1);
    }

    /**
     * 返回期望容量的hashMap
     * 通过默认的负载因子（0.75）进行计算获得hashMap的容量 {@link #getInitialCapacity(int, float)}
     * (int) ((float) expectedSize / 0.75F + 1)
     *
     * @param expectedCapacity 期望容量
     * @param <T>              key类型
     * @param <R>              value类型
     * @return 期望容量的hashMap
     */
    public static <T, R> Map<T, R> emptyMap(int expectedCapacity) {
        return emptyMap(expectedCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 返回指定负载因子和期望容量的hashMap
     * 通过负载因子进行计算获得hashMap的容量 {@link #getInitialCapacity(int, float)}
     * (int) ((float) expectedSize / loadFactor + 1)
     *
     * @param expectedCapacity 期望容量
     * @param <T>              key类型
     * @param <R>              value类型
     * @return 期望容量的hashMap
     */
    public static <T, R> Map<T, R> emptyMap(int expectedCapacity, float loadFactor) {
        int initialCapacity = getInitialCapacity(expectedCapacity, loadFactor);
        return new HashMap<>(initialCapacity, loadFactor);
    }

    /**
     * 创建期望容量为 {@link #DEFAULT_EXPECTED_CAPACITY} 的HashSet
     *
     * @return 期望容量为 16 的HashSet
     */
    public static <T> Set<T> emptySet() {
        return emptySet(DEFAULT_EXPECTED_CAPACITY);
    }

    /**
     * 返回期望容量的 HashSet
     * 通过默认的负载因子 {@link #DEFAULT_LOAD_FACTOR} 进行计算获得HashSet的容量 {@link #getInitialCapacity(int, float)}
     * (int) ((float) expectedCapacity / {@link #DEFAULT_LOAD_FACTOR} + 1)
     *
     * @param expectedCapacity 期望容量
     * @return 期望容量的 HashSet
     */
    public static <T> Set<T> emptySet(int expectedCapacity) {
        return emptySet(expectedCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 返回指定负载因子和期望容量的 HashSet
     * 通过负载因子进行计算获得HashMap的容量 {@link #getInitialCapacity(int, float)}
     * (int) ((float) expectedCapacity / loadFactor + 1)
     *
     * @param expectedCapacity 期望容量
     * @return 期望容量的 HashSet
     */
    public static <T> Set<T> emptySet(int expectedCapacity, float loadFactor) {
        int initialCapacity = getInitialCapacity(expectedCapacity, loadFactor);
        return new HashSet<>(initialCapacity, loadFactor);
    }

    /**
     * 创建默认容量 {@link #DEFAULT_EXPECTED_CAPACITY} 的 ArrayList
     *
     * @return 容量为 16 的 ArrayList
     */
    public static <T> List<T> emptyList() {
        return emptyList(DEFAULT_EXPECTED_CAPACITY);
    }

    /**
     * 创建指定容量的 ArrayList
     *
     * @param initialCapacity 初始化容量
     */
    public static <T> List<T> emptyList(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    /**
     * 根据传入的数据创建 ArrayList
     *
     * @param ts 传入的值
     */
    @SafeVarargs
    public static <T> List<T> asList(T... ts) {
        return asList(t -> true, ts);
    }

    /**
     * 将满足表达式的 元素 创建为新的 ArrayList
     *
     * @param predicate 判断表达式
     * @param ts        传入元素
     */
    @SafeVarargs
    public static <T> List<T> asList(Predicate<T> predicate, T... ts) {
        if (Objects.isNull(ts)) {
            return emptyList();
        }
        List<T> list = emptyList(ts.length);
        for (T t : ts) {
            if (predicate.test(t)) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 将传入的集合和元素创建为新的 ArrayList
     *
     * @param collection 传入的集合
     * @param ts         传入的元素
     * @return 新的ArrayList
     */
    @SafeVarargs
    public static <T> List<T> newList(Collection<T> collection, T... ts) {
        boolean has = Objects.nonNull(ts);
        int size = size(collection) + (has ? ts.length : 0);
        List<T> newList = emptyList(size);
        if (isNotEmpty(collection)) {
            newList.addAll(collection);
        }
        if (has) {
            newList.addAll(Arrays.asList(ts));
        }
        return newList;
    }

    /**
     * 将传入的多个集合创建为新的 ArrayList
     *
     * @param collections 传入的集合数组
     * @return 新的ArrayList
     */
    @SafeVarargs
    public static <T> List<T> newList(Collection<T>... collections) {
        int size = 0;
        for (Collection<T> collection : collections) {
            if (isNotEmpty(collection)) {
                size += collection.size();
            }
        }
        List<T> newList = emptyList(size);
        for (Collection<T> collection : collections) {
            if (isNotEmpty(collection)) {
                newList.addAll(collection);
            }
        }
        return newList;
    }

    @SafeVarargs
    public static <T> Set<T> asSet(T... ts) {
        return asSet(t -> true, ts);
    }

    /**
     * 将传入的 元素数组中满足判断表达式的元素创建为新的 HashMap
     *
     * @param predicate 判断表达式
     * @param ts        元素数组
     * @return 新的 HashMap
     */
    @SafeVarargs
    public static <T> Set<T> asSet(Predicate<T> predicate, T... ts) {
        if (Objects.isNull(ts)) {
            return emptySet();
        }
        Set<T> set = emptySet(ts.length);
        for (T t : ts) {
            if (predicate.test(t)) {
                set.add(t);
            }
        }
        return set;
    }

    /**
     * 将传入的集合和元素整合为新的 HashMap
     *
     * @param collection 传入的集合
     * @param ts         传入的元素数组
     * @return 整合后的 HashMap
     */
    @SafeVarargs
    public static <T> Set<T> newSet(Collection<T> collection, T... ts) {
        boolean has = Objects.nonNull(ts);
        int size = size(collection) + (has ? ts.length : 0);
        Set<T> newSet = emptySet(size);
        if (isNotEmpty(collection)) {
            newSet.addAll(collection);
        }
        if (has) {
            newSet.addAll(Arrays.asList(ts));
        }
        return newSet;
    }

    /**
     * 将传入的多个集合整合为新的 HashMap
     *
     * @param collections 需要整合的集合数组
     * @return 整合后的 HashMap
     */
    @SafeVarargs
    public static <T> Set<T> newSet(Collection<T>... collections) {
        int size = 0;
        for (Collection<T> collection : collections) {
            if (isNotEmpty(collection)) {
                size += collection.size();
            }
        }
        Set<T> newSet = emptySet(size);
        for (Collection<T> collection : collections) {
            if (isNotEmpty(collection)) {
                newSet.addAll(collection);
            }
        }
        return newSet;
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return true：集合为空，false：集合不为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否为空
     *
     * @param map 需要校验的map
     * @return true：map为空，false：map不为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断集合是否不为空
     *
     * @param collection 检查的集合
     * @return boolean true：不为空，false：为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断map是否不为空
     *
     * @param map 检查的集合
     * @return boolean true：不为空，false：为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 获取集合的 元素数量
     *
     * @param collection 集合
     * @return 集合的元素数量
     */
    public static int size(Collection<?> collection) {
        return isEmpty(collection) ? 0 : collection.size();
    }

    /**
     * 集合是否包含元素
     *
     * @param collection 检查的集合
     * @param obj        目标元素
     * @return true：集合包含目标元素，false：集合不包含目标元素
     */
    public static boolean contains(Collection<?> collection, Object obj) {
        return isNotEmpty(collection) && collection.contains(obj);
    }

    /**
     * 集合是否不包含元素
     * 集合为空即不包含
     *
     * @param collection 检查的集合
     * @param obj        目标元素
     * @return true：集合不包含目标元素，false：集合包含目标元素
     */
    public static boolean notContains(Collection<?> collection, Object obj) {
        return !contains(collection, obj);
    }

    /**
     * 集合是否包含
     *
     * @param collection     目标集合
     * @param tobeCollection 待检测的集合
     * @return true：集合包含某一元素，false：集合不包含任何元素
     */
    public static boolean containsAny(Collection<?> collection, Collection<?> tobeCollection) {
        if (isEmpty(tobeCollection)) {
            return true;
        }
        if (isEmpty(collection)) {
            return false;
        }
        for (Object o : tobeCollection) {
            if (collection.contains(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 集合是否包含元素
     *
     * @param collection     检查的集合
     * @param tobeCollection 目标元素
     * @return true：集合包含所有元素，false：集合未包含所有元素
     */
    public static boolean containsAll(Collection<?> collection, Collection<?> tobeCollection) {
        if (isEmpty(tobeCollection)) {
            return true;
        }
        if (isEmpty(collection)) {
            return false;
        }
        for (Object o : tobeCollection) {
            if (!collection.contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 转换数组为list，用于类型转换，非类型转换直接用 Arrays的asList 方法
     *
     * @param array    需要转换的数组
     * @param function 转换函数
     * @param <T>      数组元素类型
     * @param <R>      转换后的元素类型
     * @return <R>类型的集合
     * @see Arrays#asList(Object[])
     */
    public static <T, R> List<R> arrayToList(T[] array, Function<T, R> function) {
        if (Objects.isNull(array)) {
            return emptyList();
        }
        return toList(map(Arrays.stream(array), function));
    }

    /**
     * 返回list中第一个匹配的元素下标
     *
     * @param list   集合
     * @param object 目标元素
     * @param <T>    集合元素类型
     * @return 匹配的元素下标
     */
    public static <T> int indexOf(List<T> list, T object) {
        if (isEmpty(list)) {
            return -1;
        }
        return list.indexOf(object);
    }

    /**
     * 返回list中最后一个匹配的元素下标
     *
     * @param list   集合
     * @param object 目标元素
     * @param <T>    集合元素类型
     * @return 匹配的元素下标
     */
    public static <T> int lastIndexOf(List<T> list, T object) {
        if (isEmpty(list)) {
            return -1;
        }
        return list.lastIndexOf(object);
    }

    /**
     * 返回list所有匹配的元素下标，仅匹配集合的元素
     *
     * @param list   集合
     * @param object 目标元素
     * @param <T>    集合元素类型
     * @return 匹配的元素下标
     */
    public static <T> int[] allIndexOf(List<T> list, Object object) {
        return allIndexOf(list, t -> Objects.equals(t, object));
    }

    /**
     * 返回list所有匹配的元素下标，表达式
     * 可以匹配元素集合的成员变量
     *
     * @param list      集合
     * @param predicate 匹配条件
     * @param <T>       集合元素类型
     * @return 匹配的元素下标
     */
    public static <T> int[] allIndexOf(List<T> list, Predicate<T> predicate) {
        AtomicInteger i = new AtomicInteger();
        return list.stream().mapToInt(o -> {
            int index = i.getAndIncrement();
            if (predicate.test(o)) {
                return index;
            }
            return -1;
        }).filter(index -> index != -1).toArray();
    }

    /**
     * 转换集合为数组
     *
     * @param collection 需要转换的集合
     * @return 转换后的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> collection) {
        if (isEmpty(collection)) {
            return (T[]) new Object[0];
        }
        T[] target = (T[]) new Object[size(collection)];
        return collection.toArray(target);
    }

    /**
     * 将 #{ts} 的元素添加到 #{targetList}
     *
     * @param targetList 被操作集合
     * @param ts         需要添加的元素
     */
    @SafeVarargs
    public static <T> List<T> add(List<T> targetList, T... ts) {
        return add(targetList, t -> true, ts);
    }

    /**
     * 将不为空的 #{ts} 的元素添加到 #{targetList}
     *
     * @param targetList 被操作集合
     * @param ts         需要添加的元素
     */
    @SafeVarargs
    public static <T> List<T> addNonNull(List<T> targetList, T... ts) {
        return add(targetList, Objects::nonNull, ts);
    }

    /**
     * 将 #{ts} 的元素添加到 #{targetList}
     *
     * @param targetList 被操作集合
     * @param predicate  需要执行的断言函数，断言函数返回true才添加元素
     * @param ts         需要添加的元素
     */
    @SafeVarargs
    public static <T> List<T> add(List<T> targetList, Predicate<T> predicate, T... ts) {
        if (ts == null) {
            return targetList;
        }
        if (targetList == null) {
            targetList = emptyList();
        }
        for (T t : ts) {
            if (predicate.test(t)) {
                targetList.add(t);
            }
        }
        return targetList;
    }

    /**
     * 将 #{ts} 的元素添加到 #{targetSet}
     *
     * @param targetSet 被操作集合
     * @param ts        需要添加的元素
     */
    @SafeVarargs
    public static <T> Set<T> add(Set<T> targetSet, T... ts) {
        return add(targetSet, t -> true, ts);
    }

    /**
     * 将不为空的 #{ts} 的元素添加到 #{targetSet}
     *
     * @param targetSet 被操作集合
     * @param ts        需要添加的元素
     */
    @SafeVarargs
    public static <T> Set<T> addNonNull(Set<T> targetSet, T... ts) {
        return add(targetSet, Objects::nonNull, ts);
    }

    /**
     * 由于值传递问题，在方法中初始化的类是需要返回的
     * 将 #{ts} 的元素添加到 #{targetSet}
     *
     * @param targetSet 被操作集合
     * @param predicate 需要执行的断言函数，断言函数返回true才添加元素
     * @param ts        需要添加的元素
     */
    @SafeVarargs
    public static <T> Set<T> add(Set<T> targetSet, Predicate<T> predicate, T... ts) {
        if (ts == null) {
            return targetSet;
        }
        if (targetSet == null) {
            targetSet = emptySet();
        }
        for (T t : ts) {
            if (predicate.test(t)) {
                targetSet.add(t);
            }
        }
        return targetSet;
    }

    /**
     * 将 #{sourceList} 的元素添加到 #{targetList}
     *
     * @param targetList 被操作集合
     * @param sourceList 需要添加的元素集合
     */
    public static <T> List<T> addAll(List<T> targetList, List<T> sourceList) {
        if (isEmpty(sourceList)) {
            return targetList;
        }
        if (targetList == null) {
            targetList = emptyList();
        }
        targetList.addAll(sourceList);
        return targetList;
    }

    /**
     * 将 #{targetSet} 的元素添加到 #{sourceSet}
     *
     * @param targetSet 被操作集合
     * @param sourceSet 需要添加的元素集合
     */
    public static <T> Set<T> addAll(Set<T> targetSet, Set<T> sourceSet) {
        if (isEmpty(sourceSet)) {
            return targetSet;
        }
        if (targetSet == null) {
            targetSet = emptySet();
        }
        targetSet.addAll(sourceSet);
        return targetSet;
    }

    /**
     * 将 #{sourceMap} 的元素添加到 #{targetMap}
     *
     * @param targetMap 被操作集合
     * @param sourceMap 需要删除的元素集合
     */
    public static <T, R> Map<T, R> putAll(Map<T, R> targetMap, Map<T, R> sourceMap) {
        if (isEmpty(sourceMap)) {
            return targetMap;
        }
        if (targetMap == null) {
            targetMap = emptyMap();
        }
        targetMap.putAll(sourceMap);
        return targetMap;
    }

    /**
     * 从 #{collection} 中移除的元素
     *
     * @param collection 被操作集合
     * @param ts         需要移除的元素
     */
    @SafeVarargs
    public static <T> void remove(Collection<T> collection, T... ts) {
        if (isEmpty(collection) || ts == null) {
            return;
        }
        for (T t : ts) {
            collection.remove(t);
        }
    }

    /**
     * 从 #{collection} 中移除的元素
     *
     * @param collection 被操作集合
     * @param predicate  判断表达式
     * @param ts         需要移除的元素
     */
    @SafeVarargs
    public static <T> void remove(Collection<T> collection, Predicate<T> predicate, T... ts) {
        if (isEmpty(collection) || ts == null) {
            return;
        }
        for (T t : ts) {
            if (predicate.test(t)) {
                collection.remove(t);
            }
        }
    }

    /**
     * 从 #{collection} 中移除所有 #{removeCollection} 的元素
     * #{collection} 或 #{removeCollection} 为空都不需要进行操作
     *
     * @param collection       被操作集合
     * @param removeCollection 需要删除的元素集合
     */
    public static <T> void removeAll(Collection<T> collection, Collection<T> removeCollection) {
        if (isEmpty(collection) || isEmpty(removeCollection)) {
            return;
        }
        collection.removeAll(removeCollection);
    }

    /**
     * 清空集合
     *
     * @param collection 被操作集合
     */
    public static <T> void clear(Collection<T> collection) {
        if (isEmpty(collection)) {
            return;
        }
        collection.clear();
    }

    /**
     * 避免map为null的情况下发生NPE
     *
     * @param map map
     * @param key 获取的key
     * @param <T> key类型
     * @param <R> value类型
     * @return 获取的值
     */
    public static <T, R> R getForMap(Map<T, R> map, T key) {
        return isEmpty(map) ? null : map.get(key);
    }

    /**
     * 避免map为null的情况下发生NPE
     * 不存在则返回 {defaultValue}
     *
     * @param map          map
     * @param key          获取的key
     * @param defaultValue 默认值
     * @param <T>          key类型
     * @param <R>          value类型
     * @return 获取的值
     */
    public static <T, R> R getForMap(Map<T, R> map, T key, R defaultValue) {
        R value = getForMap(map, key);
        return Objects.isNull(value) ? defaultValue : value;
    }

    /**
     * 避免map为null的情况下发生NPE
     * 表达式结果为false则返回 {defaultValue}
     *
     * @param map          map
     * @param key          获取的key
     * @param defaultValue 默认值
     * @param <T>          key类型
     * @param <R>          value类型
     * @return 获取的值
     */
    public static <T, R> R getForMap(Map<T, R> map, T key, Predicate<R> predicate, R defaultValue) {
        R value = getForMap(map, key);
        return predicate.test(value) ? value : defaultValue;
    }

    /**
     * 避免map为null的情况下发生NPE
     * key 不存在则返回 {defaultValue}
     * key 存在则直接返回 value
     *
     * @param map          map
     * @param key          获取的key
     * @param defaultValue 默认值
     * @param <T>          key类型
     * @param <R>          value类型
     * @return 获取的值
     */
    public static <T, R> R getForMapContainsKey(Map<T, R> map, T key, R defaultValue) {
        return isEmpty(map) ? null : map.getOrDefault(key, defaultValue);
    }

    /**
     * 对集合的每个元素进行操作
     *
     * @param collection 需要操作的集合
     * @param consumer   操作函数
     */
    public static <T> void foreach(Collection<T> collection, Consumer<T> consumer) {
        if (isEmpty(collection)) {
            return;
        }
        collection.forEach(consumer);
    }

    /**
     * 对集合的元素筛选后进行操作
     * 与 peekList(filter(list)) 一样
     *
     * @param collection 需要操作的集合
     * @param predicate  匹配函数
     * @param consumer   操作函数
     */
    public static <T> void foreach(Collection<T> collection, Predicate<T> predicate, Consumer<T> consumer) {
        if (isEmpty(collection)) {
            return;
        }
        collection.forEach(item -> {
            if (predicate.test(item)) {
                consumer.accept(item);
            }
        });
    }

    /**
     * 获取集合转换后的 stream
     *
     * @param collection 集合
     * @param function   转换函数
     * @param <T>        原集合元素类型
     * @param <R>        转换后流元素类型
     * @return 转换类型后的流
     */
    public static <T, R> Stream<R> map(Collection<T> collection, Function<T, R> function) {
        if (isEmpty(collection)) {
            return Stream.empty();
        }
        return collection.stream().map(function);
    }

    /**
     * 获取转换后的 stream
     *
     * @param stream   流
     * @param function 转换函数
     * @param <T>      原流元素类型
     * @param <R>      转换后流元素类型
     * @return 转换类型后的流
     */
    public static <T, R> Stream<R> map(Stream<T> stream, Function<T, R> function) {
        return stream.map(function);
    }

    /**
     * 获取转换后的 stream
     *
     * @param collection 流
     * @param function   转换函数
     * @param <T>        原流元素类型
     * @return 转换类型后的流
     */
    public static <T> IntStream mapToInt(Collection<T> collection, ToIntFunction<T> function) {
        if (isEmpty(collection)) {
            return IntStream.empty();
        }
        return mapToInt(collection.stream(), function);
    }

    /**
     * 获取转换后的 stream
     *
     * @param stream   流
     * @param function 转换函数
     * @param <T>      原流元素类型
     * @return 转换类型后的流
     */
    public static <T> IntStream mapToInt(Stream<T> stream, ToIntFunction<T> function) {
        return stream.mapToInt(function);
    }

    /**
     * 获取转换后的 stream
     *
     * @param collection 流
     * @param function   转换函数
     * @param <T>        原流元素类型
     * @return 转换类型后的流
     */
    public static <T> LongStream mapToLong(Collection<T> collection, ToLongFunction<T> function) {
        if (isEmpty(collection)) {
            return LongStream.empty();
        }
        return mapToLong(collection.stream(), function);
    }

    /**
     * 获取转换后的 stream
     *
     * @param stream   流
     * @param function 转换函数
     * @param <T>      原流元素类型
     * @return 转换类型后的流
     */
    public static <T> LongStream mapToLong(Stream<T> stream, ToLongFunction<T> function) {
        return stream.mapToLong(function);
    }

    /**
     * 获取集合运算后的 stream
     *
     * @param collection 集合
     * @param consumer   运算函数
     * @param <T>        原集合元素类型
     * @return 转换类型后的流
     */
    public static <T> Stream<T> peek(Collection<T> collection, Consumer<T> consumer) {
        if (isEmpty(collection)) {
            return Stream.empty();
        }
        return collection.stream().peek(consumer);
    }

    /**
     * 获取运算后的 stream
     *
     * @param stream   流
     * @param consumer 运算函数
     * @param <T>      原集合元素类型
     * @return 转换类型后的流
     */
    public static <T> Stream<T> peek(Stream<T> stream, Consumer<T> consumer) {
        return stream.peek(consumer);
    }

    /**
     * 获取集合筛选后的stream
     *
     * @param collection 集合
     * @param predicate  筛选函数
     * @param <T>        集合的元素类型
     * @return 筛选后的流
     */
    public static <T> Stream<T> filter(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection)) {
            return Stream.empty();
        }
        return collection.stream().filter(predicate);
    }

    /**
     * 获取流筛选后的stream
     *
     * @param stream    流
     * @param predicate 筛选函数
     * @param <T>       集合的元素类型
     * @return 筛选后的流
     */
    public static <T> Stream<T> filter(Stream<T> stream, Predicate<T> predicate) {
        return stream.filter(predicate);
    }

    /**
     * 转换元素为流
     *
     * @param stream 流
     * @param mapper 筛选函数
     * @return 筛选后的流
     */
    public static <T, R> Stream<R> flatMap(Stream<T> stream, Function<T, Stream<R>> mapper) {
        return stream.flatMap(mapper);
    }

    /**
     * 转换元素为流
     *
     * @param stream 流
     * @param mapper 筛选函数
     * @return 筛选后的流
     */
    public static <T, R> Set<R> flatMapSet(Stream<T> stream, Function<T, Stream<R>> mapper) {
        return toSet(stream.flatMap(mapper));
    }

    /**
     * 转换元素为流
     *
     * @param stream 流
     * @param mapper 筛选函数
     * @return 筛选后的流
     */
    public static <T, R> List<R> flatMapList(Stream<T> stream, Function<T, Stream<R>> mapper) {
        return toList(stream.flatMap(mapper));
    }

    /**
     * 返回多个集合的不重复并集
     *
     * @param collections 集合
     * @param <T>         集合元素类型
     * @return 并集
     */
    @SafeVarargs
    public static <T> Set<T> unionSet(Collection<T>... collections) {
        if (Objects.isNull(collections) || collections.length == 0) {
            return emptySet();
        }
        return flatMapSet(Arrays.stream(collections), Collection::stream);
    }

    /**
     * 返回多个集合的并集
     *
     * @param collections 集合
     * @param <T>         集合元素类型
     * @return 并集
     */
    @SafeVarargs
    public static <T> List<T> unionList(Collection<T>... collections) {
        if (Objects.isNull(collections) || collections.length == 0) {
            return emptyList();
        }
        return flatMapList(Arrays.stream(collections), Collection::stream);
    }

    /**
     * 返回多个集合的不重复并集
     *
     * @param collections 集合
     * @param <T>         集合元素类型
     * @return 并集
     */
    @SafeVarargs
    public static <T> Set<T> unionSet(Predicate<T> predicate, Collection<T>... collections) {
        if (Objects.isNull(collections) || collections.length == 0) {
            return emptySet();
        }
        return toSet(filter(flatMap(filter(Arrays.stream(collections), Objects::nonNull), Collection::stream), predicate));
    }

    /**
     * 统计集合中符合匹配条件的元素个数
     *
     * @param collection 集合
     * @param predicate  匹配函数
     * @param <T>        集合元素类型
     * @return 符合匹配条件的元素个数
     */
    public static <T> long filterCount(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection)) {
            return 0;
        }
        return filter(filter(collection, Objects::nonNull), predicate).count();
    }

    /**
     * 统计集合中符合匹配条件的元素个数
     *
     * @param collection 集合
     * @param predicate  匹配函数
     * @return 符合匹配条件的元素个数
     */
    public static int filterSumInt(Collection<Integer> collection, Predicate<Integer> predicate) {
        if (isEmpty(collection)) {
            return 0;
        }
        return filter(filter(collection, Objects::nonNull), predicate).mapToInt(Integer::intValue).sum();
    }

    /**
     * 统计集合中符合匹配条件的元素个数
     *
     * @param collection 集合
     * @param predicate  匹配函数
     * @return 符合匹配条件的元素个数
     */
    public static long filterSumLong(Collection<Long> collection, Predicate<Long> predicate) {
        if (isEmpty(collection)) {
            return 0;
        }
        return filter(collection, predicate).mapToLong(Long::longValue).sum();
    }

    /**
     * 检查集合是否有某一元素符合特定的匹配条件
     *
     * @param collection 需要
     * @param predicate  匹配函数
     * @param <T>        集合的元素类型
     * @return 返回集合是否符合特定的匹配条件
     */
    public static <T> boolean anyMatch(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().anyMatch(predicate);
    }

    /**
     * 检查集合是否所有元素都符合特定的匹配条件
     *
     * @param collection 需要
     * @param predicate  匹配表达式
     * @param <T>        集合的元素类型
     * @return 返回集合是否符合特定的匹配条件
     */
    public static <T> boolean allMatch(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().allMatch(predicate);
    }

    /**
     * 检查集合是否所有元素都不符合特定的匹配条件
     *
     * @param collection 检查的集合
     * @param predicate  匹配表达式
     * @param <T>        集合的元素类型
     * @return 返回集合是否符合特定的匹配条件
     */
    public static <T> boolean noneMatch(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().noneMatch(predicate);
    }

    /**
     * 连接字符串
     *
     * @param collection 需要连接的集合
     * @return 连接后的字符串
     */
    public static String join(Collection<String> collection) {
        return join(collection, DEFAULT_DELIMITER);
    }

    /**
     * 连接字符串
     *
     * @param collection 需要连接的集合
     * @param delimiter  连接符
     * @return 连接后的字符串
     */
    public static String join(Collection<String> collection, String delimiter) {
        if (isEmpty(collection)) {
            return "";
        }
        return String.join(delimiter, collection);
    }

    /**
     * 连接字符串
     * 默认连接符 {@link #DEFAULT_DELIMITER}
     *
     * @param collection 需要连接的集合
     * @param function   转换函数（需要转换为字符串的函数）
     * @param <T>        集合的元素类型
     * @return 连接后的字符串
     */
    public static <T> String join(Collection<T> collection, Function<T, String> function) {
        return join(collection, function, DEFAULT_DELIMITER);
    }

    /**
     * 连接字符串
     *
     * @param collection 需要连接的集合
     * @param function   转换函数（需要转换为字符串的函数）
     * @param delimiter  连接符
     * @param <T>        集合的元素类型
     * @return 连接后的字符串
     */
    public static <T> String join(Collection<T> collection, Function<T, String> function, String delimiter) {
        if (isEmpty(collection)) {
            return "";
        }
        return join(map(collection, function), delimiter);
    }

    /**
     * 连接字符串
     *
     * @param stream 需要连接的字符串流
     * @return 连接后的字符串
     */
    public static String join(Stream<String> stream) {
        return join(stream, DEFAULT_DELIMITER);
    }

    /**
     * 连接字符串
     *
     * @param stream    需要连接的字符串流
     * @param delimiter 连接符
     * @return 连接后的字符串
     */
    public static String join(Stream<String> stream, String delimiter) {
        return stream.collect(joining(delimiter));
    }

    /**
     * 连接字符串，忽略null和""的情况
     * 默认连接符 {@link #DEFAULT_DELIMITER}
     *
     * @param stream 需要链接的字符流
     * @return 连接后的字符串
     */
    public static String joinNotEmpty(Stream<String> stream) {
        return joinNotEmpty(stream, DEFAULT_DELIMITER);
    }

    /**
     * 连接字符串，忽略null和""的情况
     *
     * @param stream    需要链接的字符流
     * @param delimiter 连接符
     * @return 连接后的字符串
     */
    public static String joinNotEmpty(Stream<String> stream, String delimiter) {
        return join(filter(stream, StringUtils::isNotEmpty), delimiter);
    }

    /**
     * 连接字符串，忽略null和""的情况
     * 默认连接符 {@link #DEFAULT_DELIMITER}
     *
     * @param collection 需要连接的集合
     * @param function   转换函数（需要转换为字符串的函数）
     * @param <T>        集合的元素类型
     * @return 连接后的字符串
     */
    public static <T> String joinNotEmpty(Collection<T> collection, Function<T, String> function) {
        return joinNotEmpty(collection, function, DEFAULT_DELIMITER);
    }

    /**
     * 连接字符串，忽略null和""的情况
     *
     * @param collection 需要连接的集合
     * @param function   转换函数（需要转换为字符串的函数）
     * @param delimiter  连接符
     * @param <T>        集合的元素类型
     * @return 连接后的字符串
     */
    public static <T> String joinNotEmpty(Collection<T> collection, Function<T, String> function, String delimiter) {
        if (isEmpty(collection)) {
            return "";
        }
        return join(filter(map(collection, function), StringUtils::isNotEmpty), delimiter);
    }

    /**
     * 对集合分组并进行字符串连接，默认连接符 {@link #DEFAULT_DELIMITER}
     *
     * @param collection      需要连接的集合
     * @param groupFunction   转换函数（需要转换为字符串的函数）
     * @param mappingFunction 转换函数（需要转换为字符串的函数）
     * @param <T>             集合的元素类型
     * @return 连接后的字符串
     */
    public static <T, R> Map<R, String> groupJoin(Collection<T> collection,
                                                  Function<T, R> groupFunction,
                                                  Function<T, String> mappingFunction) {
        return groupJoin(collection, groupFunction, mappingFunction, DEFAULT_DELIMITER);
    }

    /**
     * 对集合分组并进行字符串连接，默认连接符 {@link #DEFAULT_DELIMITER}
     *
     * @param stream          需要连接的集合
     * @param groupFunction   转换函数（需要转换为字符串的函数）
     * @param mappingFunction 转换函数（需要转换为字符串的函数）
     * @param <T>             集合的元素类型
     * @return 连接后的字符串
     */
    public static <T, R> Map<R, String> groupJoin(Stream<T> stream,
                                                  Function<T, R> groupFunction,
                                                  Function<T, String> mappingFunction) {
        return groupJoin(stream, groupFunction, mappingFunction, DEFAULT_DELIMITER);
    }

    /**
     * 对集合分组并进行字符串连接
     *
     * @param collection      需要连接的集合
     * @param groupFunction   转换函数（需要转换为字符串的函数）
     * @param mappingFunction 转换函数（需要转换为字符串的函数）
     * @param delimiter       连接符
     * @param <T>             集合的元素类型
     * @return 连接后的字符串
     */
    public static <T, R> Map<R, String> groupJoin(Collection<T> collection,
                                                  Function<T, R> groupFunction,
                                                  Function<T, String> mappingFunction,
                                                  String delimiter) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return groupJoin(collection.stream(), groupFunction, mappingFunction, delimiter);
    }

    /**
     * 对集合分组并进行字符串连接
     *
     * @param stream          需要连接的集合
     * @param groupFunction   转换函数（需要转换为字符串的函数）
     * @param mappingFunction 转换函数（需要转换为字符串的函数）
     * @param delimiter       连接符
     * @param <T>             集合的元素类型
     * @return 连接后的字符串
     */
    public static <T, R> Map<R, String> groupJoin(Stream<T> stream,
                                                  Function<T, R> groupFunction,
                                                  Function<T, String> mappingFunction,
                                                  String delimiter) {
        return stream.collect(groupingBy(groupFunction, mapping(mappingFunction, joining(delimiter))));
    }

    /**
     * 对collection分组
     *
     * @param collection    需要分组的集合
     * @param groupFunction 分组的function
     * @return Map<R, . List < C>>
     */
    public static <T, R> Map<R, List<T>> group(Collection<T> collection, Function<T, R> groupFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return group(collection.stream(), groupFunction);
    }

    /**
     * 对collection分组
     *
     * @param stream        需要分组的集合
     * @param groupFunction 分组的function
     * @return Map<R, . List < C>>
     */
    public static <T, R> Map<R, List<T>> group(Stream<T> stream, Function<T, R> groupFunction) {
        return stream.collect(groupingBy(groupFunction));
    }

    /**
     * 对集合分组后的元素取某个字段
     *
     * @param collection      需要分组的集合
     * @param groupFunction   分组的function
     * @param mappingFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C> Map<R, List<C>> group(Collection<T> collection,
                                                  Function<T, R> groupFunction,
                                                  Function<T, C> mappingFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return group(collection.stream(), groupFunction, mappingFunction);
    }

    /**
     * 对集合分组后的元素取某个字段
     *
     * @param stream          需要分组的集合
     * @param groupFunction   分组的function
     * @param mappingFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C> Map<R, List<C>> group(Stream<T> stream,
                                                  Function<T, R> groupFunction,
                                                  Function<T, C> mappingFunction) {
        return stream.collect(groupingBy(groupFunction, mapping(mappingFunction, Collectors.toList())));
    }

    /**
     * 对集合分组后的元素取某个字段
     *
     * @param collection      需要分组的集合
     * @param groupFunction   分组的function
     * @param mappingFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C> Map<R, Set<C>> groupSet(Collection<T> collection,
                                                    Function<T, R> groupFunction,
                                                    Function<T, C> mappingFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return groupSet(collection.stream(), groupFunction, mappingFunction);
    }

    /**
     * 对集合分组后的元素取某个字段
     *
     * @param stream          需要分组的集合
     * @param groupFunction   分组的function
     * @param mappingFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C> Map<R, Set<C>> groupSet(Stream<T> stream,
                                                    Function<T, R> groupFunction,
                                                    Function<T, C> mappingFunction) {
        return stream.collect(groupingBy(groupFunction, mapping(mappingFunction, Collectors.toSet())));
    }

    /**
     * 对集合进行分组
     * 默认升序排序
     *
     * @param collection    需要分组的集合
     * @param groupFunction 分组元素
     * @param <T>           集合的元素类型
     * @param <R>           分组的元素类型
     * @return 分组后的集合
     */
    public static <T, R extends Comparable<R>> List<List<T>> groupValue(Collection<T> collection,
                                                                        Function<T, R> groupFunction) {
        return groupValue(collection, groupFunction, Function.identity());
    }

    /**
     * 对集合进行分组
     * 默认升序排序
     *
     * @param collection      需要分组的集合
     * @param groupFunction   分组元素
     * @param mappingFunction 取值元素
     * @param <T>             集合的元素类型
     * @param <R>             分组的元素类型
     * @param <C>             取值的元素类型
     * @return 分组后的集合
     */
    public static <T, R extends Comparable<R>, C> List<List<C>> groupValue(Collection<T> collection,
                                                                           Function<T, R> groupFunction,
                                                                           Function<T, C> mappingFunction) {
        return groupValue(collection, groupFunction, mappingFunction, OrderType.ASC);
    }

    /**
     * 对集合进行分组
     *
     * @param collection      需要分组的集合
     * @param groupFunction   分组元素
     * @param mappingFunction 取值元素
     * @param <T>             集合的元素类型
     * @param <R>             分组的元素类型
     * @param <C>             取值的元素类型
     * @return 分组后的集合
     */
    public static <T, R extends Comparable<R>, C> List<List<C>> groupValue(Collection<T> collection,
                                                                           Function<T, R> groupFunction,
                                                                           Function<T, C> mappingFunction,
                                                                           OrderType orderType) {
        if (isEmpty(collection)) {
            return emptyList();
        }
        Map<R, List<C>> map = group(collection, groupFunction, mappingFunction);
        return toList(map.keySet().stream().sorted(getComparator(k -> k, orderType)), map::get);
    }

    /**
     * 对集合进行分组
     *
     * @param collection    需要分组的集合
     * @param groupFunction 分组元素
     * @return 分组后的集合
     */
    public static <T, R> Map<R, Integer> groupIntCount(Collection<T> collection, Function<T, R> groupFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return groupIntCount(collection.stream(), groupFunction);
    }

    /**
     * 对集合进行分组
     *
     * @param stream        需要分组的集合
     * @param groupFunction 分组元素
     * @return 分组后的集合
     */
    public static <T, R> Map<R, Integer> groupIntCount(Stream<T> stream, Function<T, R> groupFunction) {
        return stream.collect(groupingBy(groupFunction, reducing(0, r -> 1, Integer::sum)));
    }

    /**
     * 对集合进行分组
     *
     * @param collection    需要分组的集合
     * @param groupFunction 分组元素
     * @return 分组后的集合
     */
    public static <T, R> Map<R, Long> groupCount(Collection<T> collection, Function<T, R> groupFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return groupCount(collection.stream(), groupFunction);
    }

    /**
     * 对集合进行分组
     *
     * @param stream        需要分组的集合
     * @param groupFunction 分组元素
     * @return 分组后的集合
     */
    public static <T, R> Map<R, Long> groupCount(Stream<T> stream, Function<T, R> groupFunction) {
        return stream.collect(groupingBy(groupFunction, counting()));
    }

    /**
     * 双重分组
     *
     * @param collection        需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C> Map<R, Map<C, List<T>>> dualGroup(Collection<T> collection,
                                                              Function<T, R> groupFunction,
                                                              Function<T, C> dualGroupFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return dualGroup(collection.stream(), groupFunction, dualGroupFunction);
    }

    /**
     * 双重分组
     *
     * @param stream            需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C> Map<R, Map<C, List<T>>> dualGroup(Stream<T> stream,
                                                              Function<T, R> groupFunction,
                                                              Function<T, C> dualGroupFunction) {
        return stream.collect(groupingBy(groupFunction, groupingBy(dualGroupFunction)));
    }

    /**
     * 双重分组
     *
     * @param collection        需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C, V> Map<R, Map<C, List<V>>> dualGroup(Collection<T> collection,
                                                                 Function<T, R> groupFunction,
                                                                 Function<T, C> dualGroupFunction,
                                                                 Function<T, V> valueFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return dualGroup(collection.stream(), groupFunction, dualGroupFunction, valueFunction);
    }

    /**
     * 双重分组
     *
     * @param stream            需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C, V> Map<R, Map<C, List<V>>> dualGroup(Stream<T> stream,
                                                                 Function<T, R> groupFunction,
                                                                 Function<T, C> dualGroupFunction,
                                                                 Function<T, V> valueFunction) {
        return stream.collect(groupingBy(groupFunction,
                groupingBy(dualGroupFunction, mapping(valueFunction, Collectors.toList()))));
    }

    /**
     * 双重分组
     *
     * @param collection        需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C, V> Map<R, Map<C, Set<V>>> dualGroupSet(Collection<T> collection,
                                                                   Function<T, R> groupFunction,
                                                                   Function<T, C> dualGroupFunction,
                                                                   Function<T, V> valueFunction) {
        return isEmpty(collection)
                ? emptyMap()
                : dualGroupSet(collection.stream(), groupFunction, dualGroupFunction, valueFunction);
    }

    /**
     * 双重分组
     *
     * @param stream            需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C, V> Map<R, Map<C, Set<V>>> dualGroupSet(Stream<T> stream,
                                                                   Function<T, R> groupFunction,
                                                                   Function<T, C> dualGroupFunction,
                                                                   Function<T, V> valueFunction) {
        return stream.collect(groupingBy(groupFunction,
                groupingBy(dualGroupFunction, mapping(valueFunction, Collectors.toSet()))));
    }

    /**
     * 双重分组
     *
     * @param collection        需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C> Map<R, Map<C, Long>> dualGroupCount(Collection<T> collection,
                                                                Function<T, R> groupFunction,
                                                                Function<T, C> dualGroupFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return dualGroupCount(collection.stream(), groupFunction, dualGroupFunction);
    }

    /**
     * 双重分组
     *
     * @param stream            需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C> Map<R, Map<C, Long>> dualGroupCount(Stream<T> stream,
                                                                Function<T, R> groupFunction,
                                                                Function<T, C> dualGroupFunction) {
        return stream.collect(groupingBy(groupFunction, groupingBy(dualGroupFunction, counting())));
    }

    /**
     * 双重分组求和
     *
     * @param collection        需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C> Map<R, Map<C, Integer>> dualGroupSumInt(Collection<T> collection,
                                                                    Function<T, R> groupFunction,
                                                                    Function<T, C> dualGroupFunction,
                                                                    ToIntFunction<T> valueFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return dualGroupSumInt(collection.stream(), groupFunction, dualGroupFunction, valueFunction);
    }

    /**
     * 双重分组求和
     *
     * @param stream            需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C> Map<R, Map<C, Integer>> dualGroupSumInt(Stream<T> stream,
                                                                    Function<T, R> groupFunction,
                                                                    Function<T, C> dualGroupFunction,
                                                                    ToIntFunction<T> valueFunction) {
        return stream.collect(groupingBy(groupFunction, groupingBy(dualGroupFunction, summingInt(valueFunction))));
    }

    /**
     * 三重分组
     *
     * @param collection        需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C, K> Map<R, Map<C, Map<K, List<T>>>> tripleGroup(Collection<T> collection,
                                                                           Function<T, R> groupFunction,
                                                                           Function<T, C> dualGroupFunction,
                                                                           Function<T, K> tripleGroupFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return tripleGroup(collection.stream(), groupFunction, dualGroupFunction, tripleGroupFunction);
    }

    /**
     * 三重分组
     *
     * @param stream            需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C, K> Map<R, Map<C, Map<K, List<T>>>> tripleGroup(Stream<T> stream,
                                                                           Function<T, R> groupFunction,
                                                                           Function<T, C> dualGroupFunction,
                                                                           Function<T, K> tripleGroupFunction) {
        return stream.collect(groupingBy(groupFunction, groupingBy(dualGroupFunction, groupingBy(tripleGroupFunction))));
    }

    /**
     * 三重分组
     *
     * @param collection        需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C, K, V> Map<R, Map<C, Map<K, List<V>>>> tripleGroup(Collection<T> collection,
                                                                              Function<T, R> groupFunction,
                                                                              Function<T, C> dualGroupFunction,
                                                                              Function<T, K> tripleGroupFunction,
                                                                              Function<T, V> valueFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return tripleGroup(collection.stream(), groupFunction, dualGroupFunction, tripleGroupFunction, valueFunction);
    }

    /**
     * 三重分组
     *
     * @param stream            需要分组的集合
     * @param groupFunction     分组的function
     * @param dualGroupFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C, K, V> Map<R, Map<C, Map<K, List<V>>>> tripleGroup(Stream<T> stream,
                                                                              Function<T, R> groupFunction,
                                                                              Function<T, C> dualGroupFunction,
                                                                              Function<T, K> tripleGroupFunction,
                                                                              Function<T, V> valueFunction) {
        return stream.collect(groupingBy(groupFunction, groupingBy(dualGroupFunction,
                groupingBy(tripleGroupFunction, mapping(valueFunction, Collectors.toList())))));
    }

    /**
     * 将集合内的元素类型进行函数运算
     *
     * @param stream   传入的集合
     * @param consumer <T> 集合类型
     * @return List<T> 运算后的list
     */
    public static <T> List<T> peekList(Stream<T> stream, Consumer<T> consumer) {
        return toList(peek(stream, consumer));
    }

    /**
     * 将集合内的元素类型进行函数运算
     *
     * @param collection 传入的集合
     * @param consumer   <T> 集合类型
     * @return List<T> 运算后的list
     */
    public static <T> List<T> peekList(Collection<T> collection, Consumer<T> consumer) {
        if (isEmpty(collection)) {
            return emptyList();
        }
        return toList(peek(collection, consumer));
    }

    /**
     * 将集合内的元素类型进行函数运算
     *
     * @param stream   传入的list
     * @param consumer <T> 集合类型
     * @return Set<T> 运算后的的set
     */
    public static <T> Set<T> peekSet(Stream<T> stream, Consumer<T> consumer) {
        return toSet(peek(stream, consumer));
    }

    /**
     * 将集合内的元素类型进行函数运算
     *
     * @param collection 传入的list
     * @param consumer   <T> 集合类型
     * @return Set<T> 运算后的的set
     */
    public static <T> Set<T> peekSet(Collection<T> collection, Consumer<T> consumer) {
        if (isEmpty(collection)) {
            return emptySet();
        }
        return toSet(peek(collection, consumer));
    }

    /**
     * 转换字符串为 list
     * 默认分隔符为 {@link #DEFAULT_DELIMITER}
     *
     * @param str 需要转换的字符串
     * @return 转换后的list
     */
    public static List<String> toList(String str) {
        return toList(str, DEFAULT_DELIMITER);
    }

    /**
     * 转换字符串为 list
     *
     * @param str       需要转换的字符串
     * @param delimiter 字符分隔符
     * @return 转换后的list
     */
    public static List<String> toList(String str, String delimiter) {
        if (StringUtils.isEmpty(str)) {
            return emptyList();
        }
        return toList(map(Arrays.stream(str.split(delimiter)), String::trim));
    }

    /**
     * 将集合的类型转换为list
     *
     * @param map        传入的集合
     * @param biFunction 转换函数
     * @return List<C> 转换后的list
     */
    public static <T, R, C> List<C> toList(Map<T, R> map, BiFunction<T, R, C> biFunction) {
        return toList(map, entry -> biFunction.apply(entry.getKey(), entry.getValue()));
    }

    /**
     * 将集合的类型转换为list
     *
     * @param collection 传入的集合
     * @return List<C> 转换后的list
     */
    public static <T> List<T> toList(Collection<T> collection) {
        if (isEmpty(collection)) {
            return emptyList();
        }
        return toList(collection, Function.identity());
    }

    /**
     * 将集合内的元素类型转换，并将集合的类型转换为list
     *
     * @param collection 传入的集合
     * @param function   <T, C> T传入类型 C 返回类型
     * @return List<C> 转换后的list
     */
    public static <T, C> List<C> toList(Collection<T> collection, Function<T, C> function) {
        if (isEmpty(collection)) {
            return emptyList();
        }
        return toList(map(collection, function));
    }

    /**
     * 将集合的类型转换为list
     *
     * @param stream 传入的流
     * @return List<T> 转换后的list
     */
    public static <T, R> List<R> toList(Stream<T> stream, Function<T, R> function) {
        return toList(map(stream, function));
    }

    /**
     * 将集合的类型转换为list
     *
     * @param stream 传入的流
     * @return List<T> 转换后的list
     */
    public static <T> List<T> toList(Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }

    /**
     * 将集合内的元素类型转换，并将集合的类型转换为set
     *
     * @param map      传入的list
     * @param function 转换函数
     * @return 转换后的list
     */
    public static <T, R, C> List<C> toList(Map<T, R> map, Function<Map.Entry<T, R>, C> function) {
        return isEmpty(map) ? emptyList() : toList(map.entrySet().stream(), function);
    }

    /**
     * 转换字符串为 set
     * 默认分隔符为 {@link #DEFAULT_DELIMITER}
     *
     * @param str 需要转换的字符串
     * @return 转换后的set
     */
    public static Set<String> toSet(String str) {
        return toSet(str, DEFAULT_DELIMITER);
    }

    /**
     * 转换字符串为 set
     *
     * @param str       需要转换的字符串
     * @param delimiter 字符分隔符
     * @return 转换后的set
     */
    public static Set<String> toSet(String str, String delimiter) {
        if (StringUtils.isEmpty(str)) {
            return emptySet();
        }
        return toSet(map(Arrays.stream(str.split(delimiter)), String::trim));
    }

    /**
     * 将集合内的元素类型转换，并将集合的类型转换为set
     *
     * @param collection 传入的list
     * @return Set<T> 转换后的set
     */
    public static <T> Set<T> toSet(Collection<T> collection) {
        if (isEmpty(collection)) {
            return emptySet();
        }
        return toSet(collection, Function.identity());
    }

    /**
     * 将集合内的元素类型转换，并将集合的类型转换为set
     *
     * @param collection 传入的list
     * @param function   <T, C> T传入类型 C 返回类型
     * @return Set<C> 转换后的set
     */
    public static <T, C> Set<C> toSet(Collection<T> collection, Function<T, C> function) {
        if (isEmpty(collection)) {
            return emptySet();
        }
        return toSet(map(collection, function));
    }

    /**
     * 将集合内的元素类型转换，并将集合的类型转换为set
     *
     * @param stream 传入的list
     * @return Set<C> 转换后的set
     */
    public static <T, R> Set<R> toSet(Stream<T> stream, Function<T, R> function) {
        return toSet(map(stream, function));
    }

    /**
     * 将集合内的元素类型转换，并将集合的类型转换为set
     *
     * @param stream 传入的list
     * @return Set<C> 转换后的set
     */
    public static <T> Set<T> toSet(Stream<T> stream) {
        return stream.collect(Collectors.toSet());
    }

    /**
     * 将集合内的元素类型转换，并将集合的类型转换为set
     *
     * @param map      传入的list
     * @param function 转换函数
     * @return Set<C> 转换后的set
     */
    public static <T, R, C> Set<C> toSet(Map<T, R> map, Function<Map.Entry<T, R>, C> function) {
        if (isEmpty(map)) {
            return emptySet();
        }
        return toSet(map.entrySet().stream(), function);
    }

    /**
     * 筛选集合后，返回list
     *
     * @param collection 传入的list
     * @param predicate  <T> T传入类型
     * @return Set<C> 转换后的set
     */
    public static <T> List<T> filterList(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection)) {
            return emptyList();
        }
        return toList(filter(collection, predicate));
    }

    /**
     * 筛选集合后，返回list
     *
     * @param stream       传入的stream
     * @param predicate<T> 传入类型
     * @return Set<C> 转换后的set
     */
    public static <T> List<T> filterList(Stream<T> stream, Predicate<T> predicate) {
        return toList(filter(stream, predicate));
    }

    /**
     * 筛选集合后，返回set
     *
     * @param collection 传入的collection
     * @param predicate  <T> T传入类型
     * @return Set<C> 转换后的set
     */
    public static <T> Set<T> filterSet(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection)) {
            return emptySet();
        }
        return toSet(filter(collection, predicate));
    }

    /**
     * 筛选流后，返回set
     *
     * @param stream       需要操作的流
     * @param predicate<T> 传入类型
     * @return Set<C> 筛选后的set
     */
    public static <T> Set<T> filterSet(Stream<T> stream, Predicate<T> predicate) {
        return toSet(filter(stream, predicate));
    }

    /**
     * 对集合进行求和
     *
     * @param collection 求和的集合
     * @return 集合的和
     */
    public static BigDecimal sumBigDecimal(Collection<BigDecimal> collection) {
        return sumBigDecimal(collection, Function.identity());
    }

    /**
     * 对集合进行求和
     *
     * @param collection 求和的集合
     * @param function   转换函数（需要转换为BigDecimal类型的函数）
     * @param <T>        集合的元素类型
     * @return 集合的和
     */
    public static <T> BigDecimal sumBigDecimal(Collection<T> collection, Function<T, BigDecimal> function) {
        BigDecimal sum = BigDecimal.ZERO;
        if (isNotEmpty(collection)) {
            for (T t : collection) {
                if (Objects.isNull(t)) {
                    continue;
                }
                BigDecimal apply = function.apply(t);
                if (Objects.isNull(apply)) {
                    continue;
                }
                sum = sum.add(apply);
            }
        }
        return sum;
    }

    /**
     * 对集合进行求和
     *
     * @param collection 求和的集合
     * @return 集合的和
     */
    public static <T extends Number> Long sumLong(Collection<T> collection) {
        return sumLong(collection, Function.identity());
    }

    /**
     * 对集合进行求和
     *
     * @param collection 求和的集合
     * @param function   转换函数（转换为Number的子类）
     * @param <T>        集合的元素类型
     * @return 集合的和
     */
    public static <T, R extends Number> Long sumLong(Collection<T> collection, Function<T, R> function) {
        long sum = 0;
        if (isNotEmpty(collection)) {
            for (T t : collection) {
                if (Objects.isNull(t)) {
                    continue;
                }
                R r = function.apply(t);
                if (Objects.isNull(r)) {
                    continue;
                }
                sum += r.longValue();
            }
        }
        return sum;
    }

    /**
     * 对集合进行求和
     *
     * @param collection 求和的集合
     * @return 集合的和
     */
    public static <T extends Number> Double sumDouble(Collection<T> collection) {
        return sumDouble(collection, Function.identity());
    }

    /**
     * 对集合进行求和
     *
     * @param collection 求和的集合
     * @param function   转换函数（转换为Number的子类）
     * @param <T>        集合的元素类型
     * @return 集合的和
     */
    public static <T, R extends Number> Double sumDouble(Collection<T> collection, Function<T, R> function) {
        BigDecimal sum = new BigDecimal("0");
        if (isNotEmpty(collection)) {
            for (T t : collection) {
                if (Objects.isNull(t)) {
                    continue;
                }
                R r = function.apply(t);
                if (Objects.isNull(r)) {
                    continue;
                }
                BigDecimal doubleR = new BigDecimal(String.valueOf(r.doubleValue()));
                sum = sum.add(doubleR);
            }
        }
        return sum.doubleValue();
    }

    /**
     * 对集合进行求和
     *
     * @param collection 求和的集合
     * @return 集合的和
     */
    public static <T extends Number> Integer sumInteger(Collection<T> collection) {
        return sumInteger(collection, Function.identity());
    }

    /**
     * 对集合进行求和
     *
     * @param collection 求和的集合
     * @param function   转换函数（准换为NUmber的子类）
     * @param <T>        集合的元素类型
     * @return 集合的和
     */
    public static <T, R extends Number> Integer sumInteger(Collection<T> collection, Function<T, R> function) {
        int sum = 0;
        if (isNotEmpty(collection)) {
            for (T t : collection) {
                if (Objects.isNull(t)) {
                    continue;
                }
                R r = function.apply(t);
                if (Objects.isNull(r)) {
                    continue;
                }
                sum += r.intValue();
            }
        }
        return sum;
    }

    /**
     * 对集合分组后进行汇总
     *
     * @param collection      需要分组的集合
     * @param groupFunction   分组的function
     * @param mappingFunction 分组后的集合汇总的function
     * @return Map<R, Integer>
     */
    public static <T, R, C extends Number> Map<R, Integer> groupReduceInteger(Collection<T> collection,
                                                                              Function<T, R> groupFunction,
                                                                              Function<T, C> mappingFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return collection.stream().collect(groupingBy(groupFunction, mapping(mappingFunction,
                collectingAndThen(Collectors.toList(), CollectionUtils::sumInteger))));
    }

    /**
     * 对集合分组后进行汇总
     *
     * @param collection      需要分组的集合
     * @param groupFunction   分组的function
     * @param mappingFunction 分组后的集合汇总的function
     * @return Map<R, Long>
     */
    public static <T, R, C extends Number> Map<R, Long> groupReduceLong(Collection<T> collection,
                                                                        Function<T, R> groupFunction,
                                                                        Function<T, C> mappingFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return collection.stream().collect(groupingBy(groupFunction, mapping(mappingFunction,
                collectingAndThen(Collectors.toList(), CollectionUtils::sumLong))));
    }

    /**
     * 对集合分组后进行汇总
     *
     * @param collection      需要分组的集合
     * @param groupFunction   分组的function
     * @param mappingFunction 分组后的集合汇总的function
     * @return Map<R, Double>
     */
    public static <T, R, C extends Number> Map<R, Double> groupReduceDouble(Collection<T> collection,
                                                                            Function<T, R> groupFunction,
                                                                            Function<T, C> mappingFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return collection.stream().collect(groupingBy(groupFunction, mapping(mappingFunction,
                collectingAndThen(Collectors.toList(), CollectionUtils::sumDouble))));
    }

    /**
     * 对集合分组后进行汇总
     *
     * @param collection      需要分组的集合
     * @param groupFunction   分组的function
     * @param mappingFunction 分组后的集合汇总的function
     * @return Map<R, BigDecimal>
     */
    public static <T, R> Map<R, BigDecimal> groupReduceDecimal(Collection<T> collection,
                                                               Function<T, R> groupFunction,
                                                               Function<T, BigDecimal> mappingFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return collection.stream().collect(groupingBy(groupFunction, mapping(mappingFunction,
                collectingAndThen(Collectors.toList(), CollectionUtils::sumBigDecimal))));
    }

    /**
     * 对集合进行排序
     * 默认升序排序
     *
     * @param list 需要排序的集合
     */
    public static <T extends Comparable<T>> void sorted(List<T> list) {
        sorted(list, Function.identity(), OrderType.ASC);
    }

    /**
     * 对集合进行排序
     * 默认升序排序
     *
     * @param list     需要排序的集合
     * @param function 获取排序元素的方法
     */
    public static <T, R extends Comparable<R>> void sorted(List<T> list, Function<T, R> function) {
        sorted(list, function, OrderType.ASC);
    }

    /**
     * 对集合进行排序
     *
     * @param list      需要排序的集合
     * @param function  获取排序元素的方法
     * @param orderType 排序类型
     */
    public static <T, R extends Comparable<R>> void sorted(List<T> list, Function<T, R> function, OrderType orderType) {
        if (isEmpty(list)) {
            return;
        }
        sorted(list, getComparator(function, orderType));
    }

    /**
     * 对集合进行排序
     *
     * @param list       需要排序的集合
     * @param comparator 比较器
     */
    public static <T> void sorted(List<T> list, Comparator<T> comparator) {
        if (isEmpty(list)) {
            return;
        }
        list.sort(comparator);
    }

    /**
     * 对集合进行排序
     * 默认升序排序
     *
     * @param collection 需要排序的集合
     * @param <T>        集合的元素类型
     * @return 排序后的list
     */
    public static <T extends Comparable<T>> List<T> sortedList(Collection<T> collection) {
        return sortedList(collection, OrderType.ASC);
    }

    /**
     * 对集合进行排序
     * 默认升序排序
     *
     * @param stream 需要排序的集合
     * @param <T>    集合的元素类型
     * @return 排序后的list
     */
    public static <T extends Comparable<T>> List<T> sortedList(Stream<T> stream) {
        return sortedList(stream, OrderType.ASC);
    }

    /**
     * 对集合进行排序
     *
     * @param collection 需要排序的集合
     * @param orderType  排序函数
     * @param <T>        集合的元素类型
     * @return 排序后的list
     */
    public static <T extends Comparable<T>> List<T> sortedList(Collection<T> collection, OrderType orderType) {
        return sortedList(collection, orderType, Function.identity());
    }

    /**
     * 对集合进行排序
     *
     * @param stream    需要排序的集合
     * @param orderType 排序函数
     * @param <T>       集合的元素类型
     * @return 排序后的list
     */
    public static <T extends Comparable<T>> List<T> sortedList(Stream<T> stream, OrderType orderType) {
        return sortedList(stream, orderType, Function.identity());
    }

    /**
     * 对集合进行排序
     * 默认升序排序
     *
     * @param collection 需要排序的集合
     * @param function   排序函数
     * @param <T>        集合的元素类型
     * @param <C>        排序元素类型
     * @return 排序后的list
     */
    public static <T, C extends Comparable<C>> List<T> sortedList(Collection<T> collection, Function<T, C> function) {
        return sortedList(collection, OrderType.ASC, function);
    }

    /**
     * 对集合进行排序
     * 默认升序排序
     *
     * @param stream   需要排序的集合
     * @param function 排序函数
     * @param <T>      集合的元素类型
     * @param <C>      排序元素类型
     * @return 排序后的list
     */
    public static <T, C extends Comparable<C>> List<T> sortedList(Stream<T> stream, Function<T, C> function) {
        return sortedList(stream, OrderType.ASC, function);
    }

    /**
     * 对集合进行排序
     * 默认升序排序
     *
     * @param collection 需要排序的集合
     * @param orderType  排序类型
     * @param <T>        集合的元素类型
     * @param <C>        集合排序类型
     * @return 排序后的list
     */
    public static <T, C extends Comparable<C>> List<T> sortedList(Collection<T> collection,
                                                                  OrderType orderType,
                                                                  Function<T, C> function) {
        return isEmpty(collection) ? emptyList() : sortedList(collection.stream(), orderType, function);
    }

    /**
     * 对集合进行排序
     * 默认升序排序
     *
     * @param collection 需要排序的集合
     * @return 排序后的list
     */
    public static <T, C extends Comparable<C>> List<T> sortedList(Collection<T> collection, Comparator<T> comparator) {
        return isEmpty(collection) ? emptyList() : sortedList(collection.stream(), comparator);
    }

    /**
     * 对集合进行排序
     * 默认升序排序
     *
     * @param stream    需要排序的集合
     * @param orderType 排序类型
     * @param <T>       集合的元素类型
     * @param <C>       集合排序类型
     * @return 排序后的list
     */
    public static <T, C extends Comparable<C>> List<T> sortedList(Stream<T> stream,
                                                                  OrderType orderType,
                                                                  Function<T, C> function) {
        return toList(sortedStream(stream, function, orderType));
    }

    /**
     * 对集合进行排序
     * 默认升序排序
     *
     * @param stream 需要排序的集合
     * @return 排序后的list
     */
    public static <T, C extends Comparable<C>> List<T> sortedList(Stream<T> stream, Comparator<T> comparator) {
        return toList(sortedStream(stream, comparator));
    }

    /**
     * 获取集合排序后的 stream
     * 默认升序排序
     *
     * @param collection 集合
     * @param function   转换函数
     * @param <T>        原集合元素类型
     * @param <R>        排序元素类型
     * @return 排序后的流
     */
    public static <T, R extends Comparable<R>> Stream<T> sortedStream(Collection<T> collection,
                                                                      Function<T, R> function) {
        return sortedStream(collection, function, OrderType.ASC);
    }

    /**
     * 获取集合排序后的 stream
     *
     * @param collection 集合
     * @param function   转换函数
     * @param <T>        原集合元素类型
     * @param <R>        排序元素类型
     * @return 排序后的流
     */
    public static <T, R extends Comparable<R>> Stream<T> sortedStream(Collection<T> collection,
                                                                      Function<T, R> function,
                                                                      OrderType orderType) {
        if (isEmpty(collection)) {
            return Stream.empty();
        }
        return sortedStream(collection.stream(), function, orderType);
    }

    /**
     * 获取集合排序后的 stream
     *
     * @param stream   集合
     * @param function 转换函数
     * @param <T>      原集合元素类型
     * @param <R>      排序元素类型
     * @return 排序后的流
     */
    public static <T, R extends Comparable<R>> Stream<T> sortedStream(Stream<T> stream,
                                                                      Function<T, R> function,
                                                                      OrderType orderType) {
        return sortedStream(stream, getComparator(function, orderType));
    }

    /**
     * 获取集合排序后的 stream
     *
     * @param collection 集合
     * @return 排序后的流
     */
    public static <T> Stream<T> sortedStream(Collection<T> collection, Comparator<T> comparator) {
        if (isEmpty(collection)) {
            return Stream.empty();
        }
        return sortedStream(collection.stream(), comparator);
    }

    /**
     * 获取集合排序后的 stream
     *
     * @param stream 集合
     * @return 排序后的流
     */
    public static <T> Stream<T> sortedStream(Stream<T> stream, Comparator<T> comparator) {
        return stream.sorted(comparator);
    }

    /**
     * obj转换为map
     *
     * @param obj 需要转换的obj
     */
    public static Map<String, Object> toMap(Object obj) throws IllegalAccessException {
        if (Objects.isNull(obj)) {
            return emptyMap();
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, Object> map = emptyMap(fields.length);
        boolean accessible;
        for (Field field : fields) {
            accessible = field.isAccessible();
            field.setAccessible(true);
            if (Objects.nonNull(field.get(obj))) {
                map.put(field.getName(), field.get(obj));
            }
            field.setAccessible(accessible);
        }
        return map;
    }

    /**
     * collection转换为map
     * key重复情况下，比较器的类型默认为MAX最大值
     * 转换后map的value类型默认为T，即传入的list类型
     * key重复的情况下，使用keyFunction获取比较器
     *
     * @param collection  传入的collection
     * @param keyFunction 获取map的key的function <T, R> T传入的类型，R转换后map的key类型
     * @return Map<R, T>
     */
    public static <T, R extends Comparable<R>> Map<R, T> toMap(Collection<T> collection, Function<T, R> keyFunction) {
        return toMap(collection, keyFunction, Function.identity());
    }

    /**
     * collection转换为map
     * key重复情况下，比较器的类型默认为MAX最大值
     * 转换后map的value类型默认为T，即传入的list类型
     * key重复的情况下，使用keyFunction获取比较器
     *
     * @param collection    传入的collection
     * @param keyFunction   获取map的key的function <T, R> T传入的类型，R转换后map的key类型
     * @param valueFunction 获取map的value的function <T, C> T传入类型，C转换后map的value类型
     * @return Map<R, T>
     */
    public static <T, R extends Comparable<R>, U> Map<R, U> toMap(Collection<T> collection,
                                                                  Function<T, R> keyFunction,
                                                                  Function<T, U> valueFunction) {
        return toMap(collection, keyFunction, keyFunction, valueFunction);
    }

    /**
     * collection 转换map
     * key重复情况下，比较器的类型默认为MAX最大值
     *
     * @param collection         需要转换的collection
     * @param keyFunction        获取map的key的function <T, R> T传入的类型，R转换后map的key类型
     * @param comparableFunction key重复的情况下获取比较器的function <T, U> T传入的类型，U生成比较器的类型
     * @param valueFunction      获取map的value的function <T, C> T传入类型，C转换后map的value类型
     * @return Map<R, C>
     */
    public static <T, R, U extends Comparable<U>, C> Map<R, C> toMap(Collection<T> collection,
                                                                     Function<T, R> keyFunction,
                                                                     Function<T, U> comparableFunction,
                                                                     Function<T, C> valueFunction) {
        return toMap(collection, keyFunction, comparableFunction, valueFunction, ComparableType.MAX);
    }

    /**
     * collection 转换为map
     *
     * @param collection         需要转换的collection
     * @param keyFunction        获取map的key的function <T, R> T传入的类型，R转换后map的key类型
     * @param comparableFunction key重复的情况下获取比较器的function <T, U> T传入的类型，U生成比较器的类型
     * @param valueFunction      获取map的value的function <T, C> T传入类型，C转换后map的value类型
     * @param type               key重复情况下，比较器的类型，MAX最大值或MIN最小值
     * @return Map<R, C>
     */
    public static <T, C, R, U extends Comparable<U>> Map<R, C> toMap(Collection<T> collection,
                                                                     Function<T, R> keyFunction,
                                                                     Function<T, U> comparableFunction,
                                                                     Function<T, C> valueFunction,
                                                                     ComparableType type) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return checkParamUnRepeat(collection, keyFunction) ? simpleToMap(collection, keyFunction, valueFunction)
                : groupToMap(collection, keyFunction, comparableFunction, valueFunction, type);
    }

    /**
     * 检查集合中元素的某一个参数是否无重复
     *
     * @param collection 检查的集合
     * @param function   转换函数
     * @param <T>        集合的元素类型
     * @param <R>        检查的元素类型
     * @return true：无重复，false：重复
     */
    public static <T, R> boolean checkParamUnRepeat(Collection<T> collection, Function<T, R> function) {
        return isEmpty(collection) || (toSet(collection, function).size() == collection.size());
    }

    /**
     * collection 转换为 map
     * key不重复的情况将collection转换为map的方法
     *
     * @param collection    需要转换的collection
     * @param keyFunction   获取map的key的function <T, R> T传入的类型，R转换后map的key类型
     * @param valueFunction 获取map的value的function <T, C> T传入类型，C转换后map的value类型
     * @return Map<R, C>
     */
    private static <T, C, R> Map<R, C> simpleToMap(Collection<T> collection,
                                                   Function<T, R> keyFunction,
                                                   Function<T, C> valueFunction) {
        return collection.stream().collect(Collectors.toMap(keyFunction, valueFunction));
    }

    /**
     * collection 转换为 map
     * key重复的情况下将list转换为map的方法
     *
     * @param collection         需要转换的collection
     * @param keyFunction        获取map的key的function <T, R> T传入的类型，R转换后map的key类型
     * @param comparableFunction key重复的情况下获取构造器的function <T, U> T传入的类型，U生成比较器的类型
     * @param valueFunction      获取map的value的function <T, C> T传入类型，C转换后map的value类型
     * @param type               key重复情况下，比较器的类型，MAX最大值或MIN最小值，默认为MAX
     * @return Map<R, C>
     */
    private static <T, C, R, U extends Comparable<U>> Map<R, C> groupToMap(Collection<T> collection,
                                                                           Function<T, R> keyFunction,
                                                                           Function<T, U> comparableFunction,
                                                                           Function<T, C> valueFunction,
                                                                           ComparableType type) {
        return filter(collection, Objects::nonNull).collect(groupingBy(keyFunction, collectingAndThen(
                collectingAndThen(getCollector(comparableFunction, type), Optional::get), valueFunction)));
    }

    /**
     * 获取集合的第一个元素
     * 不排序
     *
     * @param collection 集合
     * @return Optional<T>
     */
    public static <T> Optional<T> findFirst(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection)) {
            return Optional.empty();
        }
        return findFirst(collection.stream(), predicate);
    }

    /**
     * 获取集合的第一个元素
     * 不排序
     *
     * @param collection 集合
     * @param predicate  判断表达式
     * @return Optional<T>
     */
    public static <T, R> Optional<R> findFirst(Collection<T> collection,
                                               Predicate<T> predicate,
                                               Function<T, R> function) {
        if (isEmpty(collection)) {
            return Optional.empty();
        }
        return findFirst(collection.stream(), predicate, function);
    }

    /**
     * 获取集合的第一个元素
     * 不排序
     *
     * @param stream 集合
     * @return Optional<T>
     */
    public static <T, R> Optional<R> findFirst(Stream<T> stream, Predicate<T> predicate, Function<T, R> function) {
        return findFirst(filter(stream, predicate), function);
    }

    /**
     * 获取集合的第一个元素
     * 不排序
     *
     * @param stream 集合
     * @return Optional<T>
     */
    public static <T> Optional<T> findFirst(Stream<T> stream, Predicate<T> predicate) {
        return findFirst(filter(stream, predicate));
    }

    /**
     * 获取集合的第一个元素
     * 不排序
     *
     * @param stream 集合
     * @return Optional<T>
     */
    public static <T, R> Optional<R> findFirst(Stream<T> stream, Function<T, R> function) {
        return findFirst(map(stream, function));
    }

    /**
     * 获取集合的第一个元素
     * 不排序
     *
     * @param stream 集合
     * @return Optional<T>
     */
    public static <T> Optional<T> findFirst(Stream<T> stream) {
        return stream.findFirst();
    }

    /**
     * 获取集合的第一个元素
     * 不排序
     *
     * @param collection 集合
     * @return Optional<T>
     */
    public static <T> Optional<T> limitOne(Collection<T> collection) {
        if (isEmpty(collection)) {
            return Optional.empty();
        }
        return collection.stream().findFirst();
    }

    /**
     * 获取筛选集合的第一个元素
     * 不排序
     *
     * @param collection 集合
     * @return Optional<T>
     */
    public static <T> Optional<T> limitFilterOne(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection)) {
            return Optional.empty();
        }
        return filter(collection, predicate).findFirst();
    }

    /**
     * 获取集合的第一个元素
     *
     * @param collection 集合
     * @return Optional<T>
     */
    public static <T, R, C extends Comparable<C>> Optional<R> limitOne(Collection<T> collection,
                                                                       Function<T, C> comparableFunction,
                                                                       Function<T, R> mapFunction,
                                                                       OrderType orderType,
                                                                       Long skip) {
        if (isEmpty(collection)) {
            return Optional.empty();
        }
        return map(sortedStream(collection, comparableFunction, orderType), mapFunction).skip(skip).findFirst();
    }

    /**
     * 获取集合的第一个元素
     * 不排序
     *
     * @param list 集合
     * @return Optional<T>
     */
    public static <T> Optional<T> limitOne(List<T> list) {
        return limitOne(list, 0);
    }

    /**
     * 获取跳过 {skip}个 元素后的第一个元素
     * 不排序
     *
     * @param list 集合
     * @param skip 跳过元素的个数
     * @return Optional<T>
     */
    public static <T> Optional<T> limitOne(List<T> list, long skip) {
        if (isEmpty(list)) {
            return Optional.empty();
        }
        return list.stream().skip(skip).findFirst();
    }

    /**
     * 获取 通过排序方法{function}排序后 的第一个元素
     * 默认不跳过元素
     * 默认升序排序
     *
     * @param list     传入的集合
     * @param function 排序元素
     * @return Optional<T>
     */
    public static <T, C extends Comparable<C>> Optional<T> limitOne(List<T> list, Function<T, C> function) {
        return limitOne(list, function, 0, OrderType.ASC);
    }

    /**
     * 获取 通过排序方法{function}排序后  跳过{skip}个元素后  的第一个元素
     * 默认升序排序
     *
     * @param list     传入的集合
     * @param function 排序元素
     * @param skip     跳过元素个数
     * @return Optional<T>
     */
    public static <T, C extends Comparable<C>> Optional<T> limitOne(List<T> list,
                                                                    Function<T, C> function,
                                                                    long skip) {
        return limitOne(list, function, skip, OrderType.ASC);
    }

    /**
     * 获取 通过排序方式{orderType}和排序方法{function}排序后 的第一个元素
     *
     * @param list      传入集合
     * @param function  排序元素
     * @param orderType 排序方式
     * @return Optional<T>
     */
    public static <T, C extends Comparable<C>> Optional<T> limitOne(List<T> list, Function<T, C> function,
                                                                    OrderType orderType) {
        return limitOne(list, function, 0, orderType);
    }

    /**
     * 获取 通过排序方式{orderType}和排序方法{function}排序后 跳过{skip}个元素后 的第一个元素
     *
     * @param list     传入集合
     * @param function 排序方法
     * @param skip     跳过元素个数
     * @param type     排序方式，默认为DESC
     * @return Optional<T>
     */
    public static <T, C extends Comparable<C>> Optional<T> limitOne(List<T> list,
                                                                    Function<T, C> function,
                                                                    long skip,
                                                                    OrderType type) {
        if (isEmpty(list)) {
            return Optional.empty();
        }
        return list.stream().sorted(getComparator(function, type)).skip(skip).findFirst();
    }

    /**
     * 截取 list的前 {limit} 个元素
     * 不排序，不跳过
     * 返回list
     *
     * @param collection 传入的集合
     * @param limit      获取元素的个数
     * @return List<T>
     */
    public static <T> List<T> limitList(Collection<T> collection, long limit) {
        return limitList(collection, 0, limit);
    }

    /**
     * 截取集合 跳过{skip}个元素后的前 {limit} 个元素
     * 不排序
     *
     * @param collection 传入的集合
     * @param skip       跳过元素的个数
     * @param limit      获取元素的个数
     * @return List<T>
     */
    public static <T> List<T> limitList(Collection<T> collection, long skip, long limit) {
        if (isEmpty(collection)) {
            return emptyList();
        }
        return toList(collection.stream().skip(skip).limit(limit));
    }

    /**
     * 获取 指定 排序方法{function}排序后的前 {limit} 个元素
     * 默认不跳过
     * 默认升序排序
     *
     * @param list     需要截取的list
     * @param function 排序元素
     * @param limit    截取元素个数
     * @return List<T>
     */
    public static <T, C extends Comparable<C>> List<T> limitList(List<T> list, Function<T, C> function, long limit) {
        return limitList(list, function, 0, limit, OrderType.ASC);
    }

    /**
     * 获取 指定排序方法{function}和指定方式排序后的前 {limit} 个元素
     * 默认不跳过
     *
     * @param list      需要截取的list
     * @param function  排序元素
     * @param limit     截取元素个数
     * @param orderType 排序方式
     * @return List<T>
     */
    public static <T, C extends Comparable<C>> List<T> limitList(List<T> list,
                                                                 Function<T, C> function,
                                                                 long limit,
                                                                 OrderType orderType) {
        return limitList(list, function, 0, limit, orderType);
    }

    /**
     * 通过指定元素，指定方式 排序后，跳过skip个元素后，截取list中的前limit个元素
     *
     * @param list     需要截取的list
     * @param function 排序元素
     * @param skip     跳过元素个数
     * @param limit    截取元素个数
     * @param type     排序方式
     * @return List<T>
     */
    public static <T, C extends Comparable<C>> List<T> limitList(List<T> list,
                                                                 Function<T, C> function,
                                                                 long skip,
                                                                 long limit,
                                                                 OrderType type) {
        if (isEmpty(list)) {
            return emptyList();
        }
        return toList(sortedStream(list, function, type).skip(skip).limit(limit));
    }

    /**
     * 截取 跳过 {skip} 个元素后的所有元素
     * 不排序
     *
     * @param list 需要截取的list
     * @param skip 跳过元素个数
     * @return List<T>
     */
    public static <T> List<T> skipList(List<T> list, long skip) {
        return toList(list.stream().skip(skip));
    }

    /**
     * 截取 指定元素排序后 跳过 {skip} 个元素后的所有元素
     * 默认升序排序
     *
     * @param list     需要截取的list
     * @param function 排序元素
     * @param skip     跳过元素个数
     * @return List<T>
     */
    public static <T, C extends Comparable<C>> List<T> skipList(List<T> list, Function<T, C> function, long skip) {
        return skipList(list, function, skip, OrderType.ASC);
    }

    /**
     * 获取 指定元素，指定方式排序后 跳过{skip}个元素后的所有元素
     *
     * @param list     需要截取的list
     * @param function 排序元素
     * @param skip     跳过的元素个数
     * @param type     排序方式
     * @return List<T>
     */
    public static <T, C extends Comparable<C>> List<T> skipList(List<T> list,
                                                                Function<T, C> function,
                                                                long skip,
                                                                OrderType type) {
        if (isEmpty(list)) {
            return emptyList();
        }
        return toList(sortedStream(list, function, type).skip(skip));
    }

    /**
     * 根据收集器类型获取收集器
     *
     * @param function 比较方法
     * @param type     获取收集器类型，MIN：获取最小值，MAX：获取最大值
     * @param <T>      集合的元素类型
     * @param <C>      集合元素中需要比较的成员变量类型
     * @return 收集器
     */
    public static <T, C extends Comparable<C>> Collector<T, ?, Optional<T>> getCollector(Function<T, C> function,
                                                                                         ComparableType type) {
        return ComparableType.MIN == type ? minCollector(function) : maxCollector(function);
    }

    /**
     * 根据比较器类型获取比较器
     *
     * @param function 获取比较器的方法
     * @param type     获取比较器类型，ASC：升序比较器，DESC：降序排序
     * @param <T>      集合的元素类型
     * @param <C>      集合元素中进行比较的成员变量类型
     * @return 对应类型的比较器
     */
    public static <T, C extends Comparable<C>> Comparator<T> getComparator(Function<T, C> function,
                                                                           OrderType type) {
        Comparator<T> comparator = getComparator(function);
        return OrderType.ASC == type ? comparator : comparator.reversed();
    }

    /**
     * 获取比较器
     *
     * @param function 生成比较器的function
     * @return Comparator<T> T类型的比较器
     */
    private static <T, C extends Comparable<C>> Comparator<T> getComparator(Function<T, C> function) {
        return Comparator.comparing(function);
    }

    /**
     * 获取最大值收集器
     *
     * @param function 进行比较的方法
     * @return Collector<T, ?, Optional < T>>
     */
    private static <T, C extends Comparable<C>> Collector<T, ?, Optional<T>> maxCollector(Function<T, C> function) {
        return maxBy(getComparator(function));
    }

    /**
     * 获取最小值收集器
     *
     * @param function 进行比较的方法
     * @return Collector<T, ?, Optional < T>>
     */
    private static <T, C extends Comparable<C>> Collector<T, ?, Optional<T>> minCollector(Function<T, C> function) {
        return minBy(getComparator(function));
    }

    /**
     * 对集合进行排序再分割
     * 默认升序排序
     *
     * @param collection 需要分割的集合
     * @param function   排序元素
     * @param size       分割的size
     * @param <T>        集合的元素类型
     * @param <R>        排序的元素类型
     * @return 分割后的集合
     */
    public static <T, R extends Comparable<R>> List<List<T>> cutList(Collection<T> collection,
                                                                     Function<T, R> function,
                                                                     Integer size) {
        List<T> sortedList = sortedList(collection, OrderType.ASC, function);
        return cutList(sortedList, size);
    }

    /**
     * 对集合进行排序再分割
     *
     * @param collection 需要分割的集合
     * @param function   排序元素
     * @param orderType  排序类型
     * @param size       分割的size
     * @param <T>        集合的元素类型
     * @param <R>        排序的元素类型
     * @return 分割后的集合
     */
    public static <T, R extends Comparable<R>> List<List<T>> cutList(Collection<T> collection,
                                                                     Function<T, R> function,
                                                                     OrderType orderType,
                                                                     Integer size) {
        List<T> sortedList = sortedList(collection, orderType, function);
        return cutList(sortedList, size);
    }

    /**
     * 分割 list
     *
     * @param list 需要分割的list
     * @param size 分割的size
     * @param <T>  list的类型
     * @return 分割后的list
     */
    public static <T> List<List<T>> cutList(List<T> list, Integer size) {
        // list为空
        if (isEmpty(list)) {
            return emptyList();
        }
        List<List<T>> result = emptyList();
        int originalSize = list.size();
        // 未达到分割的size
        if (originalSize <= size) {
            result.add(list);
            return result;
        }
        List<T> items = emptyList();
        for (int i = 0; i < originalSize; i++) {
            // 子list的size未达到分割的size
            if (items.size() == size) {
                // 子list的size达到分割的size，将当前子list存入结果list
                result.add(items);
                items = emptyList();
            }
            items.add(list.get(i));
            // 判断是否最后元素，不是最后元素则开启新的子list继续遍历
            if (i == originalSize - 1) {
                result.add(items);
            }
        }
        return result;
    }

}
