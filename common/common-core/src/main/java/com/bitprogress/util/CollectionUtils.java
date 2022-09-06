package com.bitprogress.util;

import com.bitprogress.constant.StringConstants;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author wpx
 * Created on 2020/11/18 16:17
 * 集合工具类
 * 流的操作可能导致性能下降
 */
public class CollectionUtils {

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
        return emptyMap(16);
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
        return emptyMap(expectedCapacity, 0.75F);
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

    public static <T> List<T> emptyList() {
        return new ArrayList<>();
    }

    public static <T> Set<T> emptySet() {
        return new HashSet<>();
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
    public static boolean nonEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断map是否不为空
     *
     * @param map 检查的集合
     * @return boolean true：不为空，false：为空
     */
    public static boolean nonEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 集合是否包含元素
     *
     * @param collection 检查的集合
     * @param obj        目标元素
     * @return true：集合包含目标元素，false：集合不包含目标元素
     */
    public static boolean contains(Collection<?> collection, Object obj) {
        return nonEmpty(collection) && collection.contains(obj);
    }

    /**
     * 集合是否包含数组中的任一元素
     *
     * @param collection 检查的集合
     * @param objs       目标数组
     * @return true：集合包含目标数组中的任一元素
     */
    public static boolean containsAny(Collection<?> collection, Object... objs) {
        if (nonEmpty(collection)) {
            for (Object obj : collection) {
                if (ArrayUtils.contains(objs, obj)) {
                    return true;
                }
            }
        }
        return false;
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
        return Arrays.stream(array).map(function).collect(toList());
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
     * 获取集合转换后的 stream
     *
     * @param collection 集合
     * @param function   转换函数
     * @param <T>        原集合元素类型
     * @param <R>        转换后流元素类型
     * @return 转换类型后的流
     */
    public static <T, R> Stream<R> map(Collection<T> collection, Function<T, R> function) {
        return collection.stream().map(function);
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
        return collection.stream().peek(consumer);
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
        return collection.stream().filter(predicate);
    }

    /**
     * 统计集合中符合匹配条件的元素个数
     *
     * @param collection 集合
     * @param predicate  匹配函数
     * @param <T>        集合元素类型
     * @return 符合匹配条件的元素个数
     */
    public static <T> long countByPredicate(Collection<T> collection, Predicate<T> predicate) {
        return filter(collection, predicate).count();
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
     * @param delimiter  连接符
     * @return 连接后的字符串
     */
    public static String join(Collection<String> collection, String delimiter) {
        if (isEmpty(collection)) {
            return StringConstants.EMPTY;
        }
        return String.join(delimiter, collection);
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
            return StringConstants.EMPTY;
        }
        return map(collection, function).collect(joining(delimiter));
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
            return StringConstants.EMPTY;
        }
        return map(collection, function).filter(StringUtils::nonEmpty).collect(joining(delimiter));
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
    public static <T, R> Map<R, String> groupJoin(Collection<T> collection, Function<T, R> groupFunction,
                                                  Function<T, String> mappingFunction, String delimiter) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return collection.stream().collect(groupingBy(groupFunction, collectingAndThen(mapping(mappingFunction, toList()),
                strings -> String.join(delimiter, strings))));
    }

    /**
     * 对collection分组
     *
     * @param collection    需要分组的集合
     * @param groupFunction 分组的function
     * @return Map<R, . List < C>>
     */
    public static <T, R> Map<R, List<T>> groupCollection(Collection<T> collection, Function<T, R> groupFunction) {
        return groupCollection(collection, groupFunction, t -> t);
    }

    /**
     * 对集合分组后的元素取某个字段
     *
     * @param collection      需要分组的集合
     * @param groupFunction   分组的function
     * @param mappingFunction 分组后的集合取某个字段的function
     * @return Map<R, . List < C>>
     */
    public static <T, R, C> Map<R, List<C>> groupCollection(Collection<T> collection, Function<T, R> groupFunction,
                                                            Function<T, C> mappingFunction) {
        if (isEmpty(collection)) {
            return emptyMap();
        }
        return collection.stream().collect(groupingBy(groupFunction, mapping(mappingFunction, toList())));
    }

    /**
     * 对集合进行分组
     * 默认降序排序
     *
     * @param collection    需要分组的集合
     * @param groupFunction 分组元素
     * @param <T>           集合的元素类型
     * @param <R>           分组的元素类型
     * @return 分组后的集合
     */
    public static <T, R extends Comparable<R>> List<List<T>> groupCollectionValue(Collection<T> collection,
                                                                                  Function<T, R> groupFunction) {
        return groupCollectionValue(collection, groupFunction, t -> t);
    }

    /**
     * 对集合进行分组
     * 默认降序排序
     *
     * @param collection      需要分组的集合
     * @param groupFunction   分组元素
     * @param mappingFunction 取值元素
     * @param <T>             集合的元素类型
     * @param <R>             分组的元素类型
     * @param <C>             取值的元素类型
     * @return 分组后的集合
     */
    public static <T, R extends Comparable<R>, C> List<List<C>> groupCollectionValue(Collection<T> collection,
                                                                                     Function<T, R> groupFunction,
                                                                                     Function<T, C> mappingFunction) {
        return groupCollectionValue(collection, groupFunction, mappingFunction, OrderType.DESC);
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
    public static <T, R extends Comparable<R>, C> List<List<C>> groupCollectionValue(Collection<T> collection,
                                                                                     Function<T, R> groupFunction,
                                                                                     Function<T, C> mappingFunction,
                                                                                     OrderType orderType) {
        if (isEmpty(collection)) {
            return emptyList();
        }
        Map<R, List<C>> map = collection.stream().collect(groupingBy(groupFunction, mapping(mappingFunction, toList())));
        return map.keySet().stream().sorted(getComparator(k -> k, orderType)).map(map::get).collect(toList());
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
        return peek(collection, consumer).collect(toList());
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
        return peek(collection, consumer).collect(toSet());
    }

    /**
     * 将集合内的元素类型转换，并将集合的类型转换为list
     *
     * @param collection 传入的集合
     * @param function   <T, C> T传入类型 C 返回类型
     * @return List<C> 转换后的list
     */
    public static <T, C> List<C> conversionList(Collection<T> collection, Function<T, C> function) {
        if (isEmpty(collection)) {
            return emptyList();
        }
        return map(collection, function).collect(toList());
    }

    /**
     * 将集合内的元素类型转换，并将集合的类型转换为set
     *
     * @param map      传入的list
     * @param function 转换函数
     * @return 转换后的list
     */
    public static <T, R, C> List<C> conversionList(Map<T, R> map, Function<Map.Entry<T, R>, C> function) {
        if (isEmpty(map)) {
            return emptyList();
        }
        return map.entrySet().stream().map(function).collect(toList());
    }

    /**
     * 将集合内的元素类型转换，并将集合的类型转换为set
     *
     * @param collection 传入的list
     * @param function   <T, C> T传入类型 C 返回类型
     * @return Set<C> 转换后的set
     */
    public static <T, C> Set<C> conversionSet(Collection<T> collection, Function<T, C> function) {
        if (isEmpty(collection)) {
            return emptySet();
        }
        return map(collection, function).collect(toSet());
    }

    /**
     * 将集合内的元素类型转换，并将集合的类型转换为set
     *
     * @param map      传入的list
     * @param function 转换函数
     * @return Set<C> 转换后的set
     */
    public static <T, R, C> Set<C> conversionSet(Map<T, R> map, Function<Map.Entry<T, R>, C> function) {
        if (isEmpty(map)) {
            return emptySet();
        }
        return map.entrySet().stream().map(function).collect(toSet());
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
        return filter(collection, predicate).collect(toList());
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
        return filter(collection, predicate).collect(toSet());
    }

    /**
     * 对集合进行求和
     *
     * @param collection 求和的集合
     * @return 集合的和
     */
    public static BigDecimal sumBigDecimal(Collection<BigDecimal> collection) {
        return sumBigDecimal(collection, t -> t);
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
        if (nonEmpty(collection)) {
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
        return sumLong(collection, t -> t);
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
        if (nonEmpty(collection)) {
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
        return sumDouble(collection, t -> t);
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
        if (nonEmpty(collection)) {
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
        return sumInteger(collection, t -> t);
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
        if (nonEmpty(collection)) {
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
                collectingAndThen(toList(), CollectionUtils::sumInteger))));
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
                collectingAndThen(toList(), CollectionUtils::sumLong))));
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
                collectingAndThen(toList(), CollectionUtils::sumDouble))));
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
                collectingAndThen(toList(), CollectionUtils::sumBigDecimal))));
    }

    /**
     * 对集合进行排序
     * 默认降序排序
     *
     * @param collection 需要排序的集合
     * @param <T>        集合的元素类型
     * @return 排序后的list
     */
    public static <T extends Comparable<T>> List<T> sortedCollection(Collection<T> collection) {
        return sortedCollection(collection, OrderType.DESC);
    }

    /**
     * 对集合进行排序
     *
     * @param collection 需要排序的集合
     * @param orderType  排序函数
     * @param <T>        集合的元素类型
     * @return 排序后的list
     */
    public static <T extends Comparable<T>> List<T> sortedCollection(Collection<T> collection, OrderType orderType) {
        return sortedCollection(collection, orderType, t -> t);
    }

    /**
     * 对集合进行排序
     * 默认降序排序
     *
     * @param collection 需要排序的集合
     * @param function   排序函数
     * @param <T>        集合的元素类型
     * @param <C>        排序元素类型
     * @return 排序后的list
     */
    public static <T, C extends Comparable<C>> List<T> sortedCollection(Collection<T> collection,
                                                                        Function<T, C> function) {
        return sortedCollection(collection, OrderType.DESC, function);
    }

    /**
     * 对集合进行排序
     * 默认降序排序
     *
     * @param collection 需要排序的集合
     * @param orderType  排序类型
     * @param <T>        集合的元素类型
     * @param <C>        集合排序类型
     * @return 排序后的list
     */
    public static <T, C extends Comparable<C>> List<T> sortedCollection(Collection<T> collection,
                                                                        OrderType orderType,
                                                                        Function<T, C> function) {
        return isEmpty(collection) ? emptyList()
                : collection.stream().sorted(getComparator(function, orderType)).collect(toList());
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
    public static <T, R extends Comparable<R>> Map<R, T> collectionToMap(Collection<T> collection,
                                                                         Function<T, R> keyFunction) {
        return collectionToMap(collection, keyFunction, t -> t);
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
    public static <T, R extends Comparable<R>, U> Map<R, U> collectionToMap(Collection<T> collection,
                                                                            Function<T, R> keyFunction,
                                                                            Function<T, U> valueFunction) {
        return collectionToMap(collection, keyFunction, keyFunction, valueFunction);
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
    public static <T, R, U extends Comparable<U>, C> Map<R, C> collectionToMap(Collection<T> collection,
                                                                               Function<T, R> keyFunction,
                                                                               Function<T, U> comparableFunction,
                                                                               Function<T, C> valueFunction) {
        return collectionToMap(collection, keyFunction, comparableFunction, valueFunction, ComparableType.MAX);
    }

    /**
     * 将collection 转换为 map
     * 转换后map的value类型默认为T，即传入的list类型
     * key重复的情况下，使用keyFunction获取比较器
     *
     * @param collection    需要转换的collection
     * @param keyFunction   获取map的key的function <T, R> T传入的类型，R转换后map的key类型
     * @param valueFunction 获取map的value的function <T, C> T传入类型，C转换后map的value类型
     * @param type          key重复情况下，比较器的类型，MAX最大值或MIN最小值
     * @return Map<R, T> 转换后的map
     */
    public static <T, R extends Comparable<R>, U> Map<R, U> collectionToMap(Collection<T> collection,
                                                                            Function<T, R> keyFunction,
                                                                            Function<T, U> valueFunction,
                                                                            ComparableType type) {
        return collectionToMap(collection, keyFunction, keyFunction, valueFunction, type);
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
    public static <T, C, R, U extends Comparable<U>> Map<R, C> collectionToMap(Collection<T> collection,
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
        return isEmpty(collection) || (conversionSet(collection, function).size() == collection.size());
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
        return collection.stream().collect(toMap(keyFunction, valueFunction));
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
     * 默认降序排序
     *
     * @param list     传入的集合
     * @param function 排序元素
     * @return Optional<T>
     */
    public static <T, C extends Comparable<C>> Optional<T> limitOne(List<T> list, Function<T, C> function) {
        return limitOne(list, function, 0, OrderType.DESC);
    }

    /**
     * 获取 通过排序方法{function}排序后  跳过{skip}个元素后  的第一个元素
     * 默认降序排序
     *
     * @param list     传入的集合
     * @param function 排序元素
     * @param skip     跳过元素个数
     * @return Optional<T>
     */
    public static <T, C extends Comparable<C>> Optional<T> limitOne(List<T> list,
                                                                    Function<T, C> function,
                                                                    long skip) {
        return limitOne(list, function, skip, OrderType.DESC);
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
        return collection.stream().skip(skip).limit(limit).collect(toList());
    }

    /**
     * 获取 指定 排序方法{function}排序后的前 {limit} 个元素
     * 默认不跳过
     * 默认降序排序
     *
     * @param list     需要截取的list
     * @param function 排序元素
     * @param limit    截取元素个数
     * @return List<T>
     */
    public static <T, C extends Comparable<C>> List<T> limitList(List<T> list, Function<T, C> function, long limit) {
        return limitList(list, function, 0, limit, OrderType.DESC);
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
        return list.stream().sorted(getComparator(function, type)).skip(skip).limit(limit).collect(toList());
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
        return list.stream().skip(skip).collect(toList());
    }

    /**
     * 截取 指定元素排序后 跳过 {skip} 个元素后的所有元素
     * 默认降序排序
     *
     * @param list     需要截取的list
     * @param function 排序元素
     * @param skip     跳过元素个数
     * @return List<T>
     */
    public static <T, C extends Comparable<C>> List<T> skipList(List<T> list, Function<T, C> function, long skip) {
        return skipList(list, function, skip, OrderType.DESC);
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
        return list.stream().sorted(getComparator(function, type)).skip(skip).collect(toList());
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
        return type == ComparableType.MIN ? minCollector(function) : maxCollector(function);
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
        return type == OrderType.ASC ? comparator : comparator.reversed();
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
     * 默认降序排序
     *
     * @param collection 需要分割的集合
     * @param function   排序元素
     * @param size       分割的size
     * @param <T>        集合的元素类型
     * @param <R>        排序的元素类型
     * @return 分割后的集合
     */
    public static <T, R extends Comparable<R>> List<List<T>> cutCollection(Collection<T> collection,
                                                                           Function<T, R> function,
                                                                           Integer size) {
        List<T> sortedList = sortedCollection(collection, OrderType.DESC, function);
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
    public static <T, R extends Comparable<R>> List<List<T>> cutCollection(Collection<T> collection,
                                                                           Function<T, R> function,
                                                                           OrderType orderType,
                                                                           Integer size) {
        List<T> sortedList = sortedCollection(collection, orderType, function);
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
        if (isEmpty(list)) {
            return emptyList();
        }
        List<List<T>> result = emptyList();
        List<T> items = emptyList();
        for (int i = 0; i < list.size(); i++) {
            // 子list的size未达到分割的size
            if (items.size() == size) {
                // 子list的size达到分割的size，将当前子list存入结果list
                result.add(items);
                items = emptyList();
            }
            items.add(list.get(i));
            // 判断是否最后元素，不是最后元素则开启新的子list继续遍历
            if (i == list.size() - 1) {
                result.add(items);
            }
        }
        return result;
    }

}
