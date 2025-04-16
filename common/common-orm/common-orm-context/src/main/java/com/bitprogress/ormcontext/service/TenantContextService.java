package com.bitprogress.ormcontext.service;

import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormmodel.info.user.UserTenantInfo;
import com.bitprogress.ormmodel.info.parser.TenantParserInfo;

import java.util.Objects;
import java.util.Optional;

public interface TenantContextService extends ParserContextService<TenantParserInfo>,
        UserContextService<UserTenantInfo>, SqlContextService {

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
    ThreadLocal<TenantType> PRE_SQL_TENANT_Type = new ThreadLocal<>();

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
     * 获取前一sql租户类型
     *
     * @return 当前租户类型
     */
    default TenantType getPreSqlTenantType() {
        return PRE_SQL_TENANT_Type.get();
    }

    /**
     * 设置前一sql租户类型
     *
     * @param tenantType 租户类型
     */
    default void setPreSqlTenantType(TenantType tenantType) {
        PRE_SQL_TENANT_Type.set(tenantType);
    }

    /**
     * 清除前一sql租户类型
     */
    default void clearPreSqlTenantType() {
        PRE_SQL_TENANT_Type.remove();
    }

    /**
     * 获取当前租户类型
     *
     * @return 当前sql租户类型
     */
    default TenantType getCurrentSqlTenantType() {
        return CURRENT_SQL_TENANT_Type.get();
    }

    /**
     * 设置当前sql租户类型
     *
     * @param tenantType 租户类型
     */
    default void setCurrentSqlTenantType(TenantType tenantType) {
        CURRENT_SQL_TENANT_Type.set(tenantType);
    }

    /**
     * 清除当前sql租户类型
     */
    default void clearCurrentSqlTenantType() {
        CURRENT_SQL_TENANT_Type.remove();
    }

    /**
     * 缓存前一sql上下文
     */
    @Override
    default void cachePreSqlContext() {
        Optional<TenantType> currentSqlTenantType = Optional.ofNullable(getCurrentSqlTenantType());
        currentSqlTenantType.ifPresent(this::setPreSqlTenantType);
        clearCurrentSqlTenantType();
    }

    /**
     * 恢复前一sql上下文
     */
    @Override
    default void restorePreSqlContext() {
        Optional<TenantType> preSqlTenantType = Optional.ofNullable(getPreSqlTenantType());
        preSqlTenantType.ifPresent(this::setCurrentSqlTenantType);
        clearPreSqlTenantType();
    }

    /**
     * 设置当前sql上下文
     *
     * @param sqlType sql类型
     */
    @Override
    default Boolean setCurrentSqlContextBySqlType(SqlType sqlType) {
        if (onParser(sqlType)) {
            if (ignoreProcess()) {
                return false;
            }
            setCurrentSqlTenantType(getParserInfo().getTenantType());
            return true;
        } else {
            UserTenantInfo userTenantInfo = getUserInfo();
            if (Objects.nonNull(userTenantInfo)) {
                setCurrentSqlTenantType(userTenantInfo.getTenantType());
                return true;
            }
            return false;
        }
    }

    /**
     * 清除sql上下文
     */
    @Override
    default void clearCurrentSqlContext() {
        clearCurrentSqlTenantType();
    }

}
