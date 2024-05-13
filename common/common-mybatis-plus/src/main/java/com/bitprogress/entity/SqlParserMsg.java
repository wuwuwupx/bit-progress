package com.bitprogress.entity;

import com.bitprogress.annotation.ParserType;
import com.bitprogress.annotation.SqlParserMode;
import com.bitprogress.annotation.SqlType;
import com.bitprogress.annotation.TenantType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sql 解析模式信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SqlParserMsg {

    /**
     * 解析模式开启状态
     */
    private Boolean enable;

    /**
     * 解析类型
     */
    private ParserType parserType;

    /**
     * sql 类型
     */
    private SqlType[] sqlTypes;

    /**
     * 租户类型
     */
    private TenantType tenantType;

    /**
     * 根据注解创建
     *
     * @param sqlParserMode sql解析模式注解
     * @return sql 解析模式信息
     */
    public static SqlParserMsg createBySqlParserMode(SqlParserMode sqlParserMode) {
        SqlParserMsg sqlParserMsg = new SqlParserMsg();
        sqlParserMsg.setEnable(true);
        sqlParserMsg.setParserType(sqlParserMode.parserType());
        sqlParserMsg.setSqlTypes(sqlParserMode.sqlTypes());
        sqlParserMsg.setTenantType(sqlParserMode.tenantType());
        return sqlParserMsg;
    }

    /**
     * 创建禁用状态的sql 解析模式
     */
    public static SqlParserMsg createDisable() {
        SqlParserMsg sqlParserMsg = new SqlParserMsg();
        sqlParserMsg.setEnable(false);
        return sqlParserMsg;
    }

}