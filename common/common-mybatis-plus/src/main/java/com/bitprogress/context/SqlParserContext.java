package com.bitprogress.context;

import com.bitprogress.annotation.ParserType;
import com.bitprogress.annotation.SqlType;
import com.bitprogress.annotation.TenantType;

import java.util.Objects;

public class SqlParserContext {

    /**
     * 解析模式开启状态
     */
    private static final ThreadLocal<Boolean> ENABLE = new ThreadLocal<>();

    /**
     * 解析类型
     */
    private static final ThreadLocal<ParserType> PARSER_TYPE = new ThreadLocal<>();

    /**
     * sql 类型
     */
    private static final ThreadLocal<SqlType[]> SQL_TYPE = new ThreadLocal<>();

    /**
     * 租户类型
     */
    private static final ThreadLocal<TenantType> TENANT_TYPE = new ThreadLocal<>();

    /**
     * 当前执行 sql 的租户类型
     * 作用域为一次 sql执行
     */
    private static final ThreadLocal<TenantType> CURRENT_SQL_TENANT_TYPE = new ThreadLocal<>();

    public static Boolean getEnable() {
        return ENABLE.get();
    }

    public static void setEnable(Boolean enable) {
        ENABLE.set(enable);
    }

    public static ParserType getParserType() {
        return PARSER_TYPE.get();
    }

    public static void setParserType(ParserType parserType) {
        PARSER_TYPE.set(parserType);
    }

    public static SqlType[] getSqlType() {
        return SQL_TYPE.get();
    }

    public static void setSqlType(SqlType[] sqlTypes) {
        SQL_TYPE.set(sqlTypes);
    }

    public static TenantType getTenantType() {
        return TENANT_TYPE.get();
    }

    public static void setTenantType(TenantType parserType) {
        TENANT_TYPE.set(parserType);
    }

    public static void remove() {
        ENABLE.remove();
        PARSER_TYPE.remove();
        SQL_TYPE.remove();
        TENANT_TYPE.remove();
    }

    /**
     * 进入 sql 解析模式
     * {@link SqlParserContext#getEnable()} != null and {@link SqlParserContext#getEnable()} == true
     * {@link SqlParserContext#getSqlType()} match sqlType
     * 进入 sql解析模式后，才会启用 {@link SqlParserContext#getParserType()} 和 {@link SqlParserContext#getTenantType()}
     *
     * @param sqlType sql 类型
     * @return true：开启 sql解析模式，false：未开启sql 解析模式
     */
    public static Boolean onSqlParser(SqlType sqlType) {
        Boolean enable = SqlParserContext.getEnable();
        return Objects.nonNull(enable) && enable && matchSqlType(sqlType);
    }

    /**
     * 检查是否忽略语句处理
     * 需要在 {@link SqlParserContext#onSqlParser(SqlType)} == true 的前提下使用
     * 当 {@link SqlParserContext#getParserType()} == {@link ParserType#INCLUDE}，return false
     *
     * @return true：忽略解析
     */
    public static Boolean ignoreProcess() {
        return ParserType.INCLUDE != SqlParserContext.getParserType();
    }

    /**
     * 设置当前执行 sql 的租户类型
     * {@link SqlParserContext#onSqlParser(SqlType)} == true 且 {@link SqlParserContext#ignoreProcess()} == false 时使用
     */
    public static void setCurrentSqlTenantType() {
        CURRENT_SQL_TENANT_TYPE.set(SqlParserContext.getTenantType());
    }

    /**
     * 获取当前执行 sql 的租户类型
     */
    public static TenantType getCurrentSqlTenantType() {
        return CURRENT_SQL_TENANT_TYPE.get();
    }

    /**
     * 清除当前执行 sql 的租户类型
     */
    public static void clearCurrentSqlTenantType() {
        CURRENT_SQL_TENANT_TYPE.remove();
    }

    /**
     * 匹配是否含有对应的 sql类型
     * 含有 {@link SqlType#NONE} 则表示包含所有的 sql类型
     *
     * @param sqlType sql类型
     * @return true：当前 sql解析模式对当前 sql类型生效，false：不生效
     */
    private static boolean matchSqlType(SqlType sqlType) {
        SqlType[] sqlTypes = getSqlType();
        if (Objects.isNull(sqlType)) {
            return false;
        }
        for (SqlType type : sqlTypes) {
            if (type.equals(SqlType.NONE) || type.equals(sqlType)) {
                return true;
            }
        }
        return false;
    }

}
