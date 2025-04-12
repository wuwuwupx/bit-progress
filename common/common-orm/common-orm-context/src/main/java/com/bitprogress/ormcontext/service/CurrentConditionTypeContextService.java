package com.bitprogress.ormcontext.service;

import com.bitprogress.ormmodel.enums.SqlType;

/**
 * 当前条件类型
 *
 * @param <T> 当前条件类型
 */
public interface CurrentConditionTypeContextService<T> {

    /**
     * 获取当前条件类型
     *
     * @return 当前条件类型
     */
    T getCurrentConditionType();

    /**
     * 设置当前条件类型
     *
     * @param currentConditionType 当前条件类型
     */
    void setCurrentConditionType(T currentConditionType);

    /**
     * 清除当前条件类型
     */
    void clearCurrentConditionType();

    /**
     * 设置当前条件类型
     *
     * @param sqlType sql类型
     */
    Boolean setCurrentConditionTypeBySqlType(SqlType sqlType);

}
