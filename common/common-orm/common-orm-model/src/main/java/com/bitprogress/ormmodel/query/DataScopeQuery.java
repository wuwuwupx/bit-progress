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
     * 是否范围查询，即 {@link #getDataScopes()} 是否应用
     */
    private Boolean isQueryLimit;

    /**
     * 范围查询模式
     */
    private SqlOperatorType limitSqlOperatorType;

    /**
     * 数据范围列表
     */
    private Set<String> dataScopes;

    /**
     * 是否查询所属数据范围，即 {@link #getBelongDataScopes()} 是否应用
     */
    private Boolean isQueryBelong;

    /**
     * 所属范围查询模式
     */
    private SqlOperatorType belongSqlOperatorType;

    /**
     * 所属数据范围列表
     */
    private Set<String> belongDataScopes;

    /**
     * 是否查询拥有数据
     */
    private Boolean isQueryOwned;

    /**
     * 用于匹配拥有数据的值
     */
    private Long ownedData;

    /**
     * 是否查询自身数据
     */
    private Boolean isQuerySelf;

    /**
     * 自身数据
     */
    private Long selfData;

    public boolean isQueryLimit() {
        return Objects.nonNull(isQueryLimit) && isQueryLimit;
    }

    public boolean isQueryBelong() {
        return Objects.nonNull(isQueryBelong) && isQueryBelong;
    }

    public boolean isQueryOwned() {
        return Objects.nonNull(isQueryOwned) && isQueryOwned;
    }

    public boolean isQuerySelf() {
        return Objects.nonNull(isQuerySelf) && isQuerySelf;
    }

}
