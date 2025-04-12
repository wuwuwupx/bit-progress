package com.bitprogress.ormparser.Service;

import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.query.ConditionQuery;

public interface OrmDataService<T extends ConditionQuery> {

    /**
     * 设置当前条件类型
     *
     * @param sqlType sql类型
     * @return 是否设置成功
     */
    Boolean setCurrentConditionType(SqlType sqlType);

    /**
     * 清除当前条件类型
     */
    void clearCurrentConditionType();

    /**
     * 获取查询条件信息
     * 针对于数据查询而言
     *
     * @return 查询条件信息
     */
    T getConditionQuery();

}
