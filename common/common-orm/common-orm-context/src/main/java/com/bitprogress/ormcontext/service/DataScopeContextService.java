package com.bitprogress.ormcontext.service;

public interface DataScopeContextService<T> {

    /**
     * 获取数据范围类型
     */
    T getDataScopeInfo();

    /**
     * 设置数据范围类型
     *
     * @param dataScopeInfo 数据范围信息
     */
    void setDataScopeInfo(T dataScopeInfo);

    /**
     * 清除数据范围类型
     */
    void clearDataScopeInfo();

    /**
     * 获取当前数据范围类型
     */
    Boolean getCurrentDataScopeEnable();

    /**
     * 设置当前数据范围开启状态
     *
     * @param dataScopeEnable 当前数据范围开启状态
     */
    void setCurrentDataScopeEnable(Boolean dataScopeEnable);

    /**
     * 清除当前数据范围开启状态
     */
    void clearCurrentDataScopeEnable();

}
