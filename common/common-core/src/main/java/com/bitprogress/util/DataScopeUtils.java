package com.bitprogress.util;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

/**
 * 数据范围工具类
 * 采用邮编形式匹配
 * 由于空字符串比较特殊，不做兼容，应该在实际业务中单独处理
 */
public class DataScopeUtils {

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
    public static boolean onFullLink(String referenceDataScope, String dataScope) {
        return referenceDataScope.startsWith(dataScope) || dataScope.startsWith(referenceDataScope);
    }

    /**
     * 检查传入的数据范围是否在 基准的数据范围全链路上，只要匹配上任一基准数据范围即可
     *
     * @param referenceDataScopes 基准数据范围
     * @param dataScope           需要校验的数据范围
     * @return true：在基准数据范围全链路上，false：不在基准数据范围全链路上
     */
    public static boolean onFullLink(Set<String> referenceDataScopes, String dataScope) {
        return CollectionUtils
                .anyMatch(referenceDataScopes, referenceDataScope -> onFullLink(referenceDataScope, dataScope));
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
    public static boolean onUpperLink(String referenceDataScope, String dataScope) {
        return referenceDataScope.startsWith(dataScope);
    }

    /**
     * 检查传入的数据范围是否在 基准的数据范围的上游链路，只要匹配上任一基准数据范围即可
     *
     * @param referenceDataScopes 基准数据范围
     * @param dataScope           需要校验的数据范围
     * @return true：在基准数据范围的上游链路，false：不在基准数据范围的上游链路
     */
    public static boolean onUpperLink(Set<String> referenceDataScopes, String dataScope) {
        return CollectionUtils
                .anyMatch(referenceDataScopes, referenceDataScope -> onUpperLink(referenceDataScope, dataScope));
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
    public static boolean onLowerLink(String referenceDataScope, String dataScope) {
        return dataScope.startsWith(referenceDataScope);
    }

    /**
     * 检查传入的数据范围是否在 基准的数据范围的下游链路，只要匹配上任一基准数据范围即可
     *
     * @param referenceDataScopes 基准数据范围
     * @param dataScope           需要校验的数据范围
     * @return true：在基准数据范围的下游链路，false：不在基准数据范围的下游链路
     */
    public static boolean onLowerLink(Set<String> referenceDataScopes, String dataScope) {
        return CollectionUtils
                .anyMatch(referenceDataScopes, referenceDataScope -> onLowerLink(referenceDataScope, dataScope));
    }

    /**
     * 获取 处于 基准数据范围 全链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1,A1B2,A1B2C3]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 全链路上的 数据范围列表
     */
    public static Set<String> getFullLink(String referenceDataScope, Set<String> dataScopes) {
        return CollectionUtils.filterSet(dataScopes, dataScope -> onFullLink(referenceDataScope, dataScope));
    }

    /**
     * 获取 压缩的 处于 基准数据范围 全链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 全链路上的 压缩后的数据范围列表
     */
    public static Set<String> getCompressFullLink(String referenceDataScope, Set<String> dataScopes) {
        return compressDataScopes(dataScope -> onFullLink(referenceDataScope, dataScope), dataScopes);
    }

    /**
     * 获取 处于 基准数据范围 上游链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1,A1B2]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 上游链路上的 数据范围列表
     */
    public static Set<String> getUpperLink(String referenceDataScope, Set<String> dataScopes) {
        return CollectionUtils.filterSet(dataScopes, dataScope -> onUpperLink(referenceDataScope, dataScope));
    }

    /**
     * 获取 压缩的 处于 基准数据范围 上游链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 上游链路上的 压缩后的数据范围列表
     */
    public static Set<String> getCompressUpperLink(String referenceDataScope, Set<String> dataScopes) {
        return compressDataScopes(dataScope -> onUpperLink(referenceDataScope, dataScope), dataScopes);
    }

    /**
     * 获取 处于 基准数据范围 下游链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1B2,A1B2C3]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 下游链路上的 数据范围列表
     */
    public static Set<String> getLowerLink(String referenceDataScope, Set<String> dataScopes) {
        return CollectionUtils.filterSet(dataScopes, dataScope -> onLowerLink(referenceDataScope, dataScope));
    }

    /**
     * 获取 压缩的 处于 基准数据范围 下游链路上的 数据范围
     * A1B2 [A1,A2,A1B2,A1B2C3,A2B2,A1B1C3] -> [A1B2]
     *
     * @param referenceDataScope 基准数据范围
     * @param dataScopes         需要匹配的数据范围列表
     * @return 处于 基准数据范围 下游链路上的 压缩后的数据范围列表
     */
    public static Set<String> getCompressLowerLink(String referenceDataScope, Set<String> dataScopes) {
        return compressDataScopes(dataScope -> onLowerLink(referenceDataScope, dataScope), dataScopes);
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
     * predicate -> onFullLink(A1,dataScope)
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
                        if (onFullLink(referenceDataScope, dataScope)) {
                            // 检查当前数据范围是否 不在基准数据范围的下游链路 不直接匹配上游是因为相等的情况也不需要操作
                            if (!onLowerLink(referenceDataScope, dataScope)) {
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

}
