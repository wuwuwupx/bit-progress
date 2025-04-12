package com.bitprogress.ormcontext.service.impl;

import com.bitprogress.ormcontext.service.DataScopeContextService;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.ormmodel.info.user.SingleTypeDataScopeInfo;

import java.util.Objects;

public class SingleTypeDataScopeContextService
        implements DataScopeContextService<SingleTypeDataScopeInfo, DataScopeType> {

    /**
     * 数据范围信息
     * 作用域为当前线程
     */
    private static final ThreadLocal<SingleTypeDataScopeInfo> DATA_SCOPE_INFO = new ThreadLocal<>();

    /**
     * 当前执行 sql 的数据范围类型
     * 作用域为一次 sql执行
     */
    private static final ThreadLocal<DataScopeType> CURRENT_SQL_DATA_SCOPE_Type = new ThreadLocal<>();

    /**
     * 获取数据范围信息
     */
    @Override
    public SingleTypeDataScopeInfo getUserInfo() {
        return DATA_SCOPE_INFO.get();
    }

    /**
     * 设置数据范围信息
     *
     * @param dataScopeInfo 数据范围信息
     */
    @Override
    public void setUserInfo(SingleTypeDataScopeInfo dataScopeInfo) {
        DATA_SCOPE_INFO.set(dataScopeInfo);
    }

    /**
     * 清除数据范围信息
     */
    @Override
    public void clearUserInfo() {
        DATA_SCOPE_INFO.remove();
    }

    /**
     * 获取用户信息类型
     *
     * @return 用户信息类型
     */
    @Override
    public Class<SingleTypeDataScopeInfo> getUserInfoClass() {
        return SingleTypeDataScopeInfo.class;
    }

    /**
     * 获取当前数据范围类型
     */
    @Override
    public DataScopeType getCurrentConditionType() {
        return CURRENT_SQL_DATA_SCOPE_Type.get();
    }

    /**
     * 设置当前数据范围类型
     *
     * @param dataScopeType 当前数据范围类型
     */
    @Override
    public void setCurrentConditionType(DataScopeType dataScopeType) {
        CURRENT_SQL_DATA_SCOPE_Type.set(dataScopeType);
    }

    /**
     * 清除当前数据范围类型
     */
    @Override
    public void clearCurrentConditionType() {
        CURRENT_SQL_DATA_SCOPE_Type.remove();
    }

    /**
     * 从解析信息中获取数据范围类型
     *
     * @return 当前数据范围类型
     */
    @Override
    public DataScopeType getDataScopeTypeByParserInfo() {
        return Objects.nonNull(getParserInfo()) ? getParserInfo().getDataScopeType() : DataScopeType.SELF;
    }

    /**
     * 从用户信息中获取数据范围类型
     *
     * @return 当前数据范围类型
     */
    @Override
    public DataScopeType getDataScopeTypeByUserInfo() {
        return Objects.nonNull(getUserInfo()) ? getUserInfo().getDataScopeType() : DataScopeType.SELF;
    }
}
