package com.bitprogress.ormmodel.query;

import com.bitprogress.ormmodel.enums.QueryMode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.Set;

/**
 * 数据范围查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataScopeQuery extends ConditionQuery {

    /**
     * 基础数据权限，即租户或者系统的数据权限
     */
    private String baseDataScope;

    /**
     * 查询模式
     */
    private QueryMode queryMode;

    /**
     * 是否范围查询，即 {@link #getRangeDataScopes()} 是否应用
     */
    private Boolean hasRangeQuery;

    /**
     * 范围查询数据范围列表
     */
    private Set<String> rangeDataScopes;

    /**
     * 是否精准查询数据范围，即 {@link #getExactDataScopes()} 是否应用
     */
    private Boolean hasExactQuery;

    /**
     * 精准查询数据范围列表
     */
    private Set<String> exactDataScopes;

    /**
     * 是否查询拥有数据
     */
    private Boolean hasOwnedQuery;

    /**
     * 用于匹配拥有数据的值
     */
    private String ownedData;

    /**
     * 是否查询自身数据
     */
    private Boolean hasSelfQuery;

    /**
     * 自身数据
     */
    private String selfData;

    public boolean hasRangeQuery() {
        return Objects.nonNull(hasRangeQuery) && hasRangeQuery;
    }

    public boolean hasExactQuery() {
        return Objects.nonNull(hasExactQuery) && hasExactQuery;
    }

    public boolean hasOwnedQuery() {
        return Objects.nonNull(hasOwnedQuery) && hasOwnedQuery;
    }

    public boolean hasSelfQuery() {
        return Objects.nonNull(hasSelfQuery) && hasSelfQuery;
    }

}
