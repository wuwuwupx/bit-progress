package com.bitprogress.ormcontext.service;

import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormmodel.info.parser.UserTenantInfo;
import com.bitprogress.ormmodel.info.parser.TenantParserInfo;

import java.util.Objects;

public interface TenantContextService extends ParserContextService<TenantParserInfo>,
        UserContextService<UserTenantInfo>, CurrentConditionTypeContextService<TenantType> {

    /**
     * 注解的数据范围解析信息
     */
    ThreadLocal<TenantParserInfo> TENANT_PARSER_INFO = new ThreadLocal<>();

    /**
     * 数据范围信息
     * 作用域为当前线程
     */
    ThreadLocal<UserTenantInfo> TENANT_INFO = new ThreadLocal<>();

    /**
     * 当前执行 sql 的租户类型
     * 作用域为一次 sql执行
     */
    ThreadLocal<TenantType> CURRENT_SQL_TENANT_Type = new ThreadLocal<>();

    /**
     * 获取数据范围解析信息
     */
    @Override
    default TenantParserInfo getParserInfo() {
        return TENANT_PARSER_INFO.get();
    }

    /**
     * 设置数据范围解析信息
     *
     * @param parserInfo 数据范围解析信息
     */
    @Override
    default void setParserInfo(TenantParserInfo parserInfo) {
        TENANT_PARSER_INFO.set(parserInfo);
    }

    /**
     * 清除数据范围解析信息
     */
    @Override
    default void clearParserInfo() {
        TENANT_PARSER_INFO.remove();
    }

    /**
     * 获取解析信息类型
     *
     * @return 解析信息类型
     */
    @Override
    default Class<TenantParserInfo> getParserInfoClass() {
        return TenantParserInfo.class;
    }

    /**
     * 获取租户数据
     */
    @Override
    default UserTenantInfo getUserInfo() {
        return  TENANT_INFO.get();
    }

    /**
     * 设置租户数据
     *
     * @param userTenantInfo 租户数据
     */
    @Override
    default void setUserInfo(UserTenantInfo userTenantInfo) {
        TENANT_INFO.set(userTenantInfo);
    }

    /**
     * 清除租户数据
     */
    @Override
    default void clearUserInfo() {
        TENANT_INFO.remove();
    }
    /**
     * 获取用户信息类型
     *
     * @return 用户信息类型
     */
    @Override
    default Class<UserTenantInfo> getUserInfoClass() {
        return UserTenantInfo.class;
    }

    /**
     * 获取当前租户类型
     *
     * @return 当前租户类型
     */
    @Override
    default TenantType getCurrentConditionType() {
        return CURRENT_SQL_TENANT_Type.get();
    }

    /**
     * 设置当前租户类型
     *
     * @param tenantType 租户类型
     */
    @Override
    default void setCurrentConditionType(TenantType tenantType) {
        CURRENT_SQL_TENANT_Type.set(tenantType);
    }

    /**
     * 清除当前数据范围类型
     */
    @Override
    default void clearCurrentConditionType() {
        CURRENT_SQL_TENANT_Type.remove();
    }

    /**
     * 设置当前数据范围类型
     *
     * @param sqlType sql类型
     */
    @Override
    default Boolean setCurrentConditionTypeBySqlType(SqlType sqlType) {
        if (onParser(sqlType)) {
            if (ignoreProcess()) {
                return false;
            }
            setCurrentConditionType(getParserInfo().getTenantType());
        } else {
            UserTenantInfo userTenantInfo = getUserInfo();
            if (Objects.nonNull(userTenantInfo)) {
                setCurrentConditionType(userTenantInfo.getTenantType());
                return true;
            }
        }
        return false;
    }

}
