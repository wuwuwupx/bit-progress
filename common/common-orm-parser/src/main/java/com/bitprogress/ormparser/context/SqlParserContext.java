package com.bitprogress.ormparser.context;

import com.bitprogress.ormparser.annotation.TenantType;
import com.bitprogress.ormparser.entity.SqlParserMsg;

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
     * 设置当前执行 sql 的租户类型
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

}
