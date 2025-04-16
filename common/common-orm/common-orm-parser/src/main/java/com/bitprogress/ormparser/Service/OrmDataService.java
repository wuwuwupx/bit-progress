package com.bitprogress.ormparser.Service;

import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.query.ConditionQuery;

public interface OrmDataService<T extends ConditionQuery> {

    /**
     * 缓存前一sql上下文
     */
    void cachePreSqlContext();

    /**
     * 设置当前sql上下文
     *
     * @param sqlType sql类型
     * @return 是否设置成功
     */
    boolean setCurrentSqlContextBySqlType(SqlType sqlType);

    /**
     * 清除sql上下文
     */
    void clearCurrentSqlContext();

    /**
     * 恢复前一sql上下文
     */
    void restorePreSqlContext();

    /**
     * 获取查询条件信息
     * 针对于数据查询而言
     *
     * @return 查询条件信息
     */
    T getConditionQuery();

}
