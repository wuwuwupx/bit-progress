package com.bitprogress.ormparser.context;

import com.bitprogress.ormcontext.utils.DataScopeContextUtils;
import com.bitprogress.ormcontext.utils.TenantContextUtils;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormparser.entity.SqlParserMsg;

public class SqlParserContext {

    /**
     * 解析模式开启状态
     */
    private static final ThreadLocal<SqlParserMsg> SQL_PARSER_MSG = new ThreadLocal<>();

    /**
     * 当前执行 sql 的租户类型
     * 作用域为一次 sql执行
     * 原先开启状态和租户类型的状态分开维护，为避免关闭解析模式的情况下读取到上级方法的状态，所以额外维护一份当前的状态
     */
    private static final ThreadLocal<TenantType> CURRENT_SQL_TENANT_TYPE = new ThreadLocal<>();

    /**
     * 当前执行 sql 的数据范围类型
     * 作用域为一次 sql执行
     */
    private static final ThreadLocal<DataScopeType> CURRENT_SQL_DATA_SCOPE_TYPE = new ThreadLocal<>();

    /**
     * 获取解析模式信息
     */
    public static SqlParserMsg getSqlParserMsg() {
        return SQL_PARSER_MSG.get();
    }

    /**
     * 设置解析模式信息
     */
    public static void setSqlParserMsg(SqlParserMsg sqlParserMsg) {
        SQL_PARSER_MSG.set(sqlParserMsg);
    }

    /**
     * 清除解析模式信息
     */
    public static void clearSqlParserMsg() {
        SQL_PARSER_MSG.remove();
    }

    /**
     * 设置当前执行 sql 的租户类型
     * 使用 sqlParserMsg 中的租户类型
     */
    public static void setCurrentSqlTenantTypeByParserMode() {
        CURRENT_SQL_TENANT_TYPE.set(SqlParserContext.getSqlParserMsg().getTenantType());
    }

    /**
     * 设置当前执行 sql 的租户类型
     * 使用 tenantContext 中的租户类型
     */
    public static void setCurrentSqlTenantTypeByContext() {
        CURRENT_SQL_TENANT_TYPE.set(TenantContextUtils.getTenantTypeOrDefault());
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
     * 设置当前执行 sql 的数据范围类型
     */
    public static void setCurrentSqlDataScopeTypeByParserMode() {
        CURRENT_SQL_DATA_SCOPE_TYPE.set(SqlParserContext.getSqlParserMsg().getDataScopeType());
    }

    /**
     * 设置当前执行 sql 的数据范围类型
     */
    public static void setCurrentSqlDataScopeTypeByContext() {
        CURRENT_SQL_DATA_SCOPE_TYPE.set(DataScopeContextUtils.getDataScopeTypeOrDefault());
    }

    /**
     * 获取当前执行 sql 的数据范围类型
     */
    public static DataScopeType getCurrentSqlDataScopeType() {
        return CURRENT_SQL_DATA_SCOPE_TYPE.get();
    }

    /**
     * 清除当前执行 sql 的数据范围类型
     */
    public static void clearCurrentSqlDataScopeType() {
        CURRENT_SQL_DATA_SCOPE_TYPE.remove();
    }

}
