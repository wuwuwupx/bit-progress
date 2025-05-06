package com.bitprogress.ormmodel.query;

import com.bitprogress.ormmodel.enums.SqlOperatorType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class TenantQuery extends ConditionQuery {

    /**
     * 查询类型
     */
    private SqlOperatorType sqlOperatorType;

    /**
     * 租户id
     */
    private Set<String> tenantIds;

}
