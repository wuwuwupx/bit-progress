package com.bitprogress.ormparser.util;

import com.bitprogress.ormmodel.enums.ParserType;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormparser.context.SqlParserContext;
import com.bitprogress.ormparser.entity.SqlParserMsg;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.StringUtils;

import java.util.Objects;

public class SqlParserUtils {

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
        SqlParserMsg sqlParserMsg = SqlParserContext.getSqlParserMsg();
        return Objects.nonNull(sqlParserMsg)
                && sqlParserMsg.getEnable()
                && matchSqlType(sqlParserMsg.getSqlTypes(), sqlType);
    }

    /**
     * 检查是否忽略语句处理
     * 需要在 {@link SqlParserUtils#onSqlParser(SqlType)} == true 的前提下使用
     * 当 {@link SqlParserMsg#getParserType()} == {@link ParserType#INCLUDE}，return false
     *
     * @return true：忽略解析
     */
    public static Boolean ignoreProcess() {
        return ParserType.INCLUDE != SqlParserContext.getSqlParserMsg().getParserType();
    }

    /**
     * 是否启用租户
     *
     * @return true：启用租户
     */
    public static Boolean tenantEnabled() {
        return SqlParserContext.getSqlParserMsg().getTenantEnabled();
    }

    /**
     * 是否启用数据范围
     *
     * @return true：启用数据范围
     */
    public static Boolean dataScopeEnabled() {
        return SqlParserContext.getSqlParserMsg().getDataScopeEnabled();
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

    public static Boolean isRpcPropagate() {
        SqlParserMsg sqlParserMsg = SqlParserContext.getSqlParserMsg();
        return Objects.nonNull(sqlParserMsg) && sqlParserMsg.getEnable() && sqlParserMsg.getRpcPropagate();
    }

    public static String getSqlParserMsgJson() {
        return isRpcPropagate() ? JsonUtils.serializeObject(SqlParserContext.getSqlParserMsg()) : "";
    }

    public static void setSqlParserMsgJson(String sqlParserMsg) {
        if (StringUtils.isNotEmpty(sqlParserMsg)) {
            SqlParserContext.setSqlParserMsg(JsonUtils.deserializeObject(sqlParserMsg, SqlParserMsg.class));
        }
    }

    /**
     * 获取租户类型
     *
     * @return 租户类型
     */
    public static TenantType getTenantType() {
        return SqlParserContext.getSqlParserMsg().getTenantType();
    }

}
