package com.bitprogress.ormcontext.service;

import com.bitprogress.ormmodel.enums.SqlType;

/**
 * 当前条件类型
 */
public interface SqlContextService {

    /**
     * 缓存前一sql上下文
     */
    void cachePreSqlContext();

    /**
     * 恢复前一sql上下文
     */
    void restorePreSqlContext();

    /**
     * 设置当前条件类型
     *
     * @param sqlType sql类型
     */
    Boolean setCurrentSqlContextBySqlType(SqlType sqlType);

    /**
     * 清除sql上下文
     */
    void clearCurrentSqlContext();

}
