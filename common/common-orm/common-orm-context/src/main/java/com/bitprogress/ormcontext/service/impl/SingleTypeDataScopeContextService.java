package com.bitprogress.ormcontext.service.impl;

import com.bitprogress.ormcontext.info.SingleTypeDataScopeInfo;
import com.bitprogress.ormcontext.service.DataScopeContextService;
import com.bitprogress.ormmodel.enums.DataScopeType;

public class SingleTypeDataScopeContextService implements DataScopeContextService<SingleTypeDataScopeInfo> {

    private static final ThreadLocal<SingleTypeDataScopeInfo> DATA_SCOPE_INFO = new ThreadLocal<>();

    /**
     * 当前执行 sql 是否开启数据范围
     * 作用域为一次 sql执行
     */
    private static final ThreadLocal<Boolean> CURRENT_SQL_DATA_SCOPE_ENABLE = new ThreadLocal<>();

    /**
     * 当前执行 sql 的数据范围类型
     * 作用域为一次 sql执行
     */
    private static final ThreadLocal<DataScopeType> CURRENT_SQL_DATA_SCOPE_Type = new ThreadLocal<>();

    /**
     * 获取数据范围类型
     */
    @Override
    public SingleTypeDataScopeInfo getDataScopeInfo() {
        return DATA_SCOPE_INFO.get();
    }

    /**
     * 设置数据范围类型
     *
     * @param dataScopeInfo 数据范围信息
     */
    @Override
    public void setDataScopeInfo(SingleTypeDataScopeInfo dataScopeInfo) {
        DATA_SCOPE_INFO.set(dataScopeInfo);
    }

    /**
     * 清除数据范围类型
     */
    @Override
    public void clearDataScopeInfo() {
        DATA_SCOPE_INFO.remove();
    }

    /**
     * 获取当前数据范围类型
     */
    @Override
    public Boolean getCurrentDataScopeEnable() {
        return CURRENT_SQL_DATA_SCOPE_ENABLE.get();
    }

    /**
     * 设置当前数据范围开启状态
     *
     * @param dataScopeEnable 当前数据范围开启状态
     */
    @Override
    public void setCurrentDataScopeEnable(Boolean dataScopeEnable) {
        CURRENT_SQL_DATA_SCOPE_ENABLE.set(dataScopeEnable);
    }

    /**
     * 清除当前数据范围开启状态
     */
    @Override
    public void clearCurrentDataScopeEnable() {
        CURRENT_SQL_DATA_SCOPE_ENABLE.remove();
    }

    /**
     * 获取当前数据范围类型
     */
    public DataScopeType getCurrentDataScopeType() {
        return CURRENT_SQL_DATA_SCOPE_Type.get();
    }

    /**
     * 设置当前数据范围类型
     *
     * @param dataScopeType 当前数据范围类型
     */
    public void setCurrentDataScopeType(DataScopeType dataScopeType) {
        CURRENT_SQL_DATA_SCOPE_Type.set(dataScopeType);
    }

    /**
     * 清除当前数据范围类型
     */
    public void clearCurrentDataScopeType() {
        CURRENT_SQL_DATA_SCOPE_Type.remove();
    }

}
