package com.bitprogress.ormcontext.query;

import com.bitprogress.ormmodel.enums.QueryMode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.management.Query;
import java.util.Set;

/**
 * 数据范围查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataScopeQuery extends Query {

    /**
     * 是否不需要查询
     */
    private Boolean isNotNeedQuery;

    /**
     * 是否查询所有
     */
    private Boolean isQueryAll;

    /**
     * 是否范围查询，即 {@link #getDataScopes()} 是否应用
     */
    private Boolean isQueryLimit;

    /**
     * 范围查询模式
     */
    private QueryMode limitQueryMode;

    /**
     * 数据范围列表
     */
    private Set<String> dataScopes;

    /**
     * 是否查询自身数据
     */
    private Boolean isQuerySelf;

    /**
     * 自身数据查询类型
     */
    private QueryMode selfQueryMode;

    /**
     * 自身数据
     */
    private String selfData;

}
