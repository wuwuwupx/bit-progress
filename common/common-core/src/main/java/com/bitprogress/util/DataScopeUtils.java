package com.bitprogress.util;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据范围工具类
 * 采用邮编形式匹配
 * 由于空字符串比较特殊，不做兼容，应该在实际业务中单独处理
 */
public class DataScopeUtils {

    private static final Pattern DEFAULT_SPLIT_PATTERN = Pattern.compile("([A-Z]\\d+)");

    /**
     * 检查传入的数据范围是否在 基准的数据范围全链路上
     * A1B2 A1 -> true
     * A1 A1B2 -> true
     * A1B2C3 A2 -> false
     * A1 A2B2C3 -> false
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScope          需要校验的数据范围
     * @return true：在基准数据范围全链路上，false：不在基准数据范围全链路上
     */
    public static boolean onFullChain(String referenceDataScope, String dataScope) {
        return referenceDataScope.startsWith(dataScope) || dataScope.startsWith(referenceDataScope);
    }

    /**
     * 检查传入的数据范围是否在 基准的数据范围全链路上，只要匹配上任一基准数据范围即可
     *
     * @param referenceDataScopes 基准数据范围
     * @param dataScope           需要校验的数据范围
     * @return true：在基准数据范围全链路上，false：不在基准数据范围全链路上
     */
    public static boolean onFullChain(Set<String> referenceDataScopes, String dataScope) {
        return CollectionUtils
                .anyMatch(referenceDataScopes, referenceDataScope -> onFullChain(referenceDataScope, dataScope));
    }

    /**
     * 检查传入的数据范围是否在 基准的数据范围的上游链路，即 基准数据范围 是以 需要校验的数据范围 开始的
     * A1B2 A1 -> true
     * A1 A1B2 -> false
     * A1B2C3 A2 -> false
     * A1 A2B2C3 -> false
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScope          需要校验的数据范围
     * @return true：在基准数据范围的上游链路，false：不在基准数据范围的上游链路
     */
    public static boolean onUpstreamChain(String referenceDataScope, String dataScope) {
        return referenceDataScope.startsWith(dataScope);
    }

    /**
     * 检查传入的数据范围是否在 基准的数据范围的上游链路，只要匹配上任一基准数据范围即可
     *
     * @param referenceDataScopes 基准数据范围
     * @param dataScope           需要校验的数据范围
     * @return true：在基准数据范围的上游链路，false：不在基准数据范围的上游链路
     */
    public static boolean onUpstreamChain(Set<String> referenceDataScopes, String dataScope) {
        return CollectionUtils
                .anyMatch(referenceDataScopes, referenceDataScope -> onUpstreamChain(referenceDataScope, dataScope));
    }

    /**
     * 检查传入的数据范围是否在 基准的数据范围的下游链路，即 需要校验的数据范围 是以 基准数据范围 开始的
     * A1 A1B2 -> true
     * A1B2 A1 -> false
     * A1B2C3 A2 -> false
     * A1 A2B2C3 -> false
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScope          需要校验的数据范围
     * @return true：在基准数据范围的下游链路，false：不在基准数据范围的下游链路
     */
    public static boolean onDownstreamChain(String referenceDataScope, String dataScope) {
        return dataScope.startsWith(referenceDataScope);
    }

    /**
     * 检查传入的数据范围是否在 基准的数据范围的下游链路，只要匹配上任一基准数据范围即可
     *
     * @param referenceDataScopes 基准数据范围
     * @param dataScope           需要校验的数据范围
     * @return true：在基准数据范围的下游链路，false：不在基准数据范围的下游链路
     */
    public static boolean onDownstreamChain(Set<String> referenceDataScopes, String dataScope) {
        return CollectionUtils
                .anyMatch(referenceDataScopes, referenceDataScope -> onDownstreamChain(referenceDataScope, dataScope));
    }

    /**
     * 获取 处于 基准数据范围 全链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1,A1B2,A1B2C3]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 全链路上的 数据范围列表
     */
    public static Set<String> getFullChain(String referenceDataScope, Set<String> dataScopes) {
        return CollectionUtils.filterSet(dataScopes, dataScope -> onFullChain(referenceDataScope, dataScope));
    }

    /**
     * 获取 压缩的 处于 基准数据范围 全链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 全链路上的 压缩后的数据范围列表
     */
    public static Set<String> getCompressFullChain(String referenceDataScope, Set<String> dataScopes) {
        return compressDataScopes(dataScope -> onFullChain(referenceDataScope, dataScope), dataScopes);
    }

    /**
     * 获取 处于 基准数据范围 上游链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1,A1B2]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 上游链路上的 数据范围列表
     */
    public static Set<String> getUpstreamChain(String referenceDataScope, Set<String> dataScopes) {
        return CollectionUtils.filterSet(dataScopes, dataScope -> onUpstreamChain(referenceDataScope, dataScope));
    }

    /**
     * 获取 压缩的 处于 基准数据范围 上游链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 上游链路上的 压缩后的数据范围列表
     */
    public static Set<String> getCompressUpstreamChain(String referenceDataScope, Set<String> dataScopes) {
        return compressDataScopes(dataScope -> onUpstreamChain(referenceDataScope, dataScope), dataScopes);
    }

    /**
     * 获取 处于 基准数据范围 下游链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1B2,A1B2C3]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 下游链路上的 数据范围列表
     */
    public static Set<String> getDownstreamChain(String referenceDataScope, Set<String> dataScopes) {
        return CollectionUtils.filterSet(dataScopes, dataScope -> onDownstreamChain(referenceDataScope, dataScope));
    }

    /**
     * 获取 压缩的 处于 基准数据范围 下游链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1B2]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 下游链路上的 压缩后的数据范围列表
     */
    public static Set<String> getCompressDownstreamChain(String referenceDataScope, Set<String> dataScopes) {
        return compressDataScopes(dataScope -> onDownstreamChain(referenceDataScope, dataScope), dataScopes);
    }

    /**
     * 压缩数据范围，处于同一链路上的 数据范围进行合并只保留最上游的数据权限，其他数据范围进行过滤
     * [A1B2,A2B3,A3B2,A3B1] [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3,A3B2C3] -> [A1,A2,A3B2,A3B1]
     *
     * @param dataScopesArray 多个数据范围
     * @return 压缩后的数据范围
     */
    @SafeVarargs
    public static Set<String> compressDataScopes(Set<String>... dataScopesArray) {
        return compressDataScopes(t -> true, dataScopesArray);
    }

    /**
     * 压缩数据范围，满足 predicate 且处于同一链路上的 数据范围进行合并只保留最上游的数据权限，其他数据范围进行过滤
     * predicate -> onFullChain(A1,dataScope)
     * [A1B2,A2B3,A3B2,A3B1] [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3,A3B2C3] -> [A1]
     * predicate -> return true
     * [A1B2,A2B3,A3B2,A3B1] [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3,A3B2C3] -> [A1,A2,A3B2,A3B1]
     *
     * @param predicate       筛选函数
     * @param dataScopesArray 多个数据范围
     * @return 压缩后的数据范围
     */
    @SafeVarargs
    public static Set<String> compressDataScopes(Predicate<String> predicate, Set<String>... dataScopesArray) {
        Set<String> compressedDataScopes = CollectionUtils.emptySet();
        if (ArrayUtils.isEmpty(dataScopesArray)) {
            return compressedDataScopes;
        }
        for (Set<String> dataScopes : dataScopesArray) {
            dataScopes.forEach(dataScope -> {
                if (predicate.test(dataScope)) {
                    Iterator<String> iterator = compressedDataScopes.iterator();
                    while (iterator.hasNext()) {
                        String referenceDataScope = iterator.next();
                        // 先检查是否在同一链路上
                        if (onFullChain(referenceDataScope, dataScope)) {
                            // 检查当前数据范围是否 不在基准数据范围的下游链路 不直接匹配上游是因为相等的情况也不需要操作
                            if (!onDownstreamChain(referenceDataScope, dataScope)) {
                                iterator.remove();
                                // 跳出while循环，添加当前数据范围
                                break;
                            }
                            // 在链路上但没有处于上游，不需要添加当前数据范围，直接跳出foreach
                            return;
                        }
                    }
                    compressedDataScopes.add(dataScope);
                }
            });
        }
        return compressedDataScopes;
    }

    /**
     * 擦除数据权限，基准数据权限为空时默认不清除数据
     * 如 A1 : [A1,A2,A1B2,A3],[A2B2,A2,A3B4C5] -> [A2,A3] [A2B2,A2,A3B4C5]
     *
     * @param referenceDataScope 基准数据权限
     * @param dataScopes         需要检索的数据权限列表
     */
    @SafeVarargs
    public static void eraseDataScopes(String referenceDataScope, Set<String>... dataScopes) {
        eraseDataScopes(referenceDataScope, false, dataScopes);
    }

    /**
     * 擦除数据权限
     * 如 A1 : [A1,A2,A1B2,A3],[A2B2,A2,A3B4C5] -> [A2,A3] [A2B2,A2,A3B4C5]
     * 假设基准字符串为空，应该
     * <p>
     * 如果 emptyReferenceClear 为 true，则基准数据权限为空时，会清除所有数据权限；
     * 为 false 的时候，则基准数据权限为空时则退出，因为无法继续匹配，类似于除零异常
     *
     * @param referenceDataScope  基准数据权限
     * @param emptyReferenceClear 基准数据权限是否为空时，是否清除所有数据权限
     * @param dataScopesArray     需要检索的数据权限列表
     */
    @SafeVarargs
    public static void eraseDataScopes(String referenceDataScope,
                                       Boolean emptyReferenceClear,
                                       Set<String>... dataScopesArray) {
        if (ArrayUtils.isEmpty(dataScopesArray)) {
            return;
        }
        if (StringUtils.isEmpty(referenceDataScope)) {
            if (emptyReferenceClear) {
                for (Set<String> dataScopes : dataScopesArray) {
                    dataScopes.clear();
                }
            }
            return;
        }
        for (Set<String> dataScopes : dataScopesArray) {
            if (CollectionUtils.isEmpty(dataScopes)) {
                continue;
            }
            // 把处于下游的数据权限擦除
            dataScopes.removeIf(dataScope -> onDownstreamChain(referenceDataScope, dataScope));
        }
    }

    /**
     * 擦除数据权限，基准数据权限为空时默认不清除数据
     * 如 [A1,A2] : [A1,A2,A1B2,A3],[A2B2,A2,A3B4C5] -> [A3] [A3B4C5]
     *
     * @param referenceDataScopes 基准数据权限列表
     * @param dataScopesArray     需要检索的数据权限列表
     */
    @SafeVarargs
    public static void eraseDataScopes(Set<String> referenceDataScopes, Set<String>... dataScopesArray) {
        eraseDataScopes(referenceDataScopes, false, dataScopesArray);
    }

    /**
     * 擦除数据权限
     * 如 [A1,A2] : [A1,A2,A1B2,A3],[A2B2,A2,A3B4C5] -> [A3] [A3B4C5]
     * <p>
     * 如果 emptyReferenceClear 为 true，则基准数据权限为空时，会清除所有数据权限；
     * 为 false 的时候，则基准数据权限为空时则退出，因为无法继续匹配，类似于除零异常
     *
     * @param referenceDataScopes 基准数据权限列表
     * @param emptyReferenceClear 基准数据权限是否为空时，是否清除所有数据权限
     * @param dataScopesArray     需要检索的数据权限列表
     */
    @SafeVarargs
    public static void eraseDataScopes(Set<String> referenceDataScopes,
                                       Boolean emptyReferenceClear,
                                       Set<String>... dataScopesArray) {
        if (ArrayUtils.isEmpty(dataScopesArray)) {
            return;
        }
        if (CollectionUtils.anyMatch(referenceDataScopes, StringUtils::isEmpty)) {
            if (emptyReferenceClear) {
                for (Set<String> dataScopes : dataScopesArray) {
                    dataScopes.clear();
                }
            }
            return;
        }
        for (Set<String> dataScopes : dataScopesArray) {
            if (CollectionUtils.isEmpty(dataScopes)) {
                continue;
            }
            // 把处于下游的数据权限擦除，即 数据权限处于任一基准数据权限的下游就擦除
            dataScopes.removeIf(dataScope -> onDownstreamChain(referenceDataScopes, dataScope));
        }
    }

    /**
     * 分割数据范围
     * 1003 1003A1B2C3 -> [1003, 1003A1、1003A1B2C3]
     * 1004 1003A1B2C3 -> []
     * 1004 1004A1B1 -> [1004、1004A1、1004A1B1]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScope          数据范围
     */
    public static Set<String> splitDataScope(String referenceDataScope, String dataScope) {
        return splitDataScope(referenceDataScope, dataScope, DEFAULT_SPLIT_PATTERN);
    }

    /**
     * 分割数据范围
     * 1003 1003A1B2C3 -> [1003, 1003A1、1003A1B2C3]
     * 1004 1003A1B2C3 -> []
     * 1004 1004A1B1 -> [1004、1004A1、1004A1B1]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScope          数据范围
     */
    public static Set<String> splitDataScope(String referenceDataScope, String dataScope, Pattern pattern) {
        Set<String> dataScopes = CollectionUtils.emptySet();
        if (StringUtils.isEmpty(referenceDataScope) || StringUtils.isEmpty(dataScope)) {
            return dataScopes;
        }
        splitDataScope(referenceDataScope, dataScope, pattern, dataScopes::add);
        return dataScopes;
    }

    /**
     * 分割数据范围
     * 1003 1003A1B2C3 -> [1003, 1003A1、1003A1B2C3]
     * 1004 1003A1B2C3 -> []
     * 1004 1004A1B1 -> [1004、1004A1、1004A1B1]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         数据范围
     */
    public static Set<String> splitDataScope(String referenceDataScope, Set<String> dataScopes) {
        return splitDataScope(referenceDataScope, dataScopes, DEFAULT_SPLIT_PATTERN);
    }

    /**
     * 分割数据范围
     * 1003 1003A1B2C3 -> [1003, 1003A1、1003A1B2C3]
     * 1004 1003A1B2C3 -> []
     * 1004 1004A1B1 -> [1004、1004A1、1004A1B1]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         数据范围
     */
    public static Set<String> splitDataScope(String referenceDataScope, Set<String> dataScopes, Pattern pattern) {
        Set<String> splitDataScopes = CollectionUtils.emptySet();
        if (StringUtils.isEmpty(referenceDataScope) || CollectionUtils.isEmpty(dataScopes)) {
            return splitDataScopes;
        }
        dataScopes.forEach(dataScope -> splitDataScope(referenceDataScope, dataScope, pattern, splitDataScopes::add));
        return splitDataScopes;
    }

    /**
     * 分割数据范围
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScope          数据范围
     * @param consumer           数据范围处理函数
     */
    private static void splitDataScope(String referenceDataScope,
                                       String dataScope,
                                       Pattern pattern,
                                       Consumer<String> consumer) {
        if (StringUtils.isEmpty(referenceDataScope) || StringUtils.isEmpty(dataScope)) {
            return;
        }
        if (onDownstreamChain(referenceDataScope, dataScope)) {
            consumer.accept(referenceDataScope);
            Matcher matcher = pattern.matcher(dataScope.substring(referenceDataScope.length()));
            StringBuilder builder = new StringBuilder(referenceDataScope);
            while (matcher.find()) {
                builder.append(matcher.group(1));
                consumer.accept(builder.toString());
            }
        }
    }

}
