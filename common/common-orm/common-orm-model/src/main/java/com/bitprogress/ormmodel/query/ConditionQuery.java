package com.bitprogress.ormmodel.query;

import com.bitprogress.basemodel.QO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ConditionQuery extends QO {

    /**
     * 是否不需要查询
     */
    private Boolean isNotNeedQuery;

    /**
     * 是否查询所有
     */
    private Boolean isQueryAll;

    /**
     * 是否不需要查询
     */
    public boolean isNotNeedQuery() {
        return Objects.nonNull(getIsNotNeedQuery()) && getIsNotNeedQuery();
    }

    /**
     * 是否查询所有
     */
    public boolean isQueryAll() {
        return Objects.nonNull(getIsQueryAll()) && getIsQueryAll();
    }

}
