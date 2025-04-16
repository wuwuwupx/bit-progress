package com.bitprogress.ormmodel.query;

import com.bitprogress.ormmodel.enums.SqlOperatorType;
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
     * 所属数据范围列表
     */
    private Set<String> managedDataScopes;

    /**
     * 所属数据范围列表
     */
    private Set<String> belongDataScopes;

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
     * 所属范围操作模式
     */
    private SqlOperatorType exactSqlOperatorType;

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
    private Long ownedData;

    /**
     * 是否查询自身数据
     */
    private Boolean hasSelfQuery;

    /**
     * 自身数据
     */
    private Long selfData;

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
