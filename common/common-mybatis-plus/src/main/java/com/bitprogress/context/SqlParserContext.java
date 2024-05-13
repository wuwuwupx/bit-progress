package com.bitprogress.context;

import com.bitprogress.annotation.ParserType;
import com.bitprogress.annotation.SqlType;
import com.bitprogress.annotation.TenantType;

import java.util.Objects;

public class SqlParserContext {

    /**
     * 解析模式开启状态
     */
    private static final ThreadLocal<SqlParserMsg> SQL_PARSER_MSG = new ThreadLocal<>();

    /**
     * 当前执行 sql 的租户类型
     * 作用域为一次 sql执行
     * 原先开启状态和租户类型的状态分开维护，未避免关闭解析模式的情况下读取到上级方法的状态，所以额外维护一份当前的状态
     */
    private static final ThreadLocal<TenantType> CURRENT_SQL_TENANT_TYPE = new ThreadLocal<>();

    public static SqlParserMsg getSqlParserMsg() {
        return SQL_PARSER_MSG.get();
    }

    public static void setSqlParserMsg(SqlParserMsg sqlParserMsg) {
        SQL_PARSER_MSG.set(sqlParserMsg);
    }

    public static void removeSqlParserMsg() {
        SQL_PARSER_MSG.remove();
    }

    /**
     * 进入 sql 解析模式
     * {@link SqlParserContext#getSqlParserMsg()} != null and {@link SqlParserMsg#getEnable()} == true
     * {@link SqlParserMsg#getSqlTypes()} match sqlType
     * 进入 sql解析模式后，才会启用 {@link SqlParserMsg#getParserType()} 和 {@link SqlParserMsg#getTenantType()}
     *
     * @param sqlType sql 类型
     * @return true：开启 sql解析模式，false：未开启sql 解析模式
     */
    public static Boolean onSqlParser(SqlType sqlType) {
        SqlParserMsg sqlParserMsg = getSqlParserMsg();
        return Objects.nonNull(sqlParserMsg)
                && sqlParserMsg.getEnable()
                && matchSqlType(sqlParserMsg.getSqlTypes(), sqlType);
    }

    /**
     * 检查是否忽略语句处理
     * 需要在 {@link SqlParserContext#onSqlParser(SqlType)} == true 的前提下使用
     * 当 {@link SqlParserMsg#getParserType()} == {@link ParserType#INCLUDE}，return false
     *
     * @return true：忽略解析
     */
    public static Boolean ignoreProcess() {
        return ParserType.INCLUDE != SqlParserContext.getSqlParserMsg().getParserType();
    }

    /**
     * 设置当前执行 sql 的租户类型
     * {@link SqlParserContext#onSqlParser(SqlType)} == true 且 {@link SqlParserContext#ignoreProcess()} == false 时使用
     */
    public static void setCurrentSqlTenantType() {
        CURRENT_SQL_TENANT_TYPE.set(SqlParserContext.getSqlParserMsg().getTenantType());
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
     * @param sqlTypes sql 生效的类型
     * @param sqlType  当前 sql类型
     * @return true：当前 sql解析模式对当前 sql类型生效，false：不生效
     */
    private static boolean matchSqlType(SqlType[] sqlTypes, SqlType sqlType) {
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
