package com.bitprogress.ormparser.entity;

import com.bitprogress.basemodel.util.EnumUtils;
import com.bitprogress.ormparser.annotation.ParserType;
import com.bitprogress.ormparser.annotation.SqlParserMode;
import com.bitprogress.ormparser.annotation.SqlType;
import com.bitprogress.ormparser.annotation.TenantType;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

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
     * rpc传播
     */
    private Boolean rpcPropagate;

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
        sqlParserMsg.setRpcPropagate(sqlParserMode.rpcPropagate());
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

    @JsonGetter(value = "enable")
    public Integer getEnableJson() {
        return Objects.isNull(enable) ? null : enable ? 1 : 0;
    }

    @JsonSetter(value = "enable")
    public void setEnableJson(Integer enable) {
        this.enable = Objects.isNull(enable) ? null : enable == 1;
    }

    @JsonGetter(value = "parserType")
    public Integer getParserTypeJson() {
        return Objects.isNull(parserType) ? null : parserType.getValue();
    }

    @JsonSetter(value = "parserType")
    public void setParserTypeJson(Integer parserType) {
        this.parserType = EnumUtils.getByValue(ParserType.class, parserType);
    }

    @JsonGetter(value = "sqlTypes")
    public Integer[] getSqlTypesJson() {
        return Objects.isNull(sqlTypes)
                ? null
                : Arrays
                .stream(sqlTypes)
                .map(SqlType::getValue)
                .toArray(Integer[]::new);
    }

    @JsonSetter(value = "sqlTypes")
    public void setSqlTypesJson(Integer[] sqlTypes) {
        if (Objects.isNull(sqlTypes)) {
            return;
        }
        this.sqlTypes = Arrays
                .stream(sqlTypes)
                .map(sqlType -> EnumUtils.getByValue(SqlType.class, sqlType))
                .toArray(SqlType[]::new);
    }

    @JsonGetter(value = "tenantType")
    public Integer getTenantTypeJson() {
        return Optional
                .ofNullable(tenantType)
                .map(TenantType::getValue)
                .orElse(null);
    }

    @JsonSetter(value = "tenantType")
    public void setTenantTypeJson(Integer tenantType) {
        this.tenantType = EnumUtils.getByValue(TenantType.class, tenantType);
    }

    @JsonGetter(value = "rpcPropagate")
    public Integer getRpcPropagateJson() {
        return Objects.isNull(rpcPropagate) ? null : rpcPropagate ? 1 : 0;
    }

    @JsonSetter
    public void setRpcPropagateJson(Integer rpcPropagate) {
        this.rpcPropagate = Objects.isNull(rpcPropagate) ? null : rpcPropagate == 1;
    }

}