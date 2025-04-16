package com.bitprogress.ormmodel.info.parser;

import com.bitprogress.basemodel.Info;
import com.bitprogress.basemodel.util.EnumUtils;
import com.bitprogress.ormmodel.enums.ParserType;
import com.bitprogress.ormmodel.enums.QueryMode;
import com.bitprogress.ormmodel.enums.SqlType;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * sql 解析模式信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseParserInfo extends Info {

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
     * 查询模式
     */
    private QueryMode queryMode;

    /**
     * rpc传播
     */
    private Boolean rpcPropagate;

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

    @JsonGetter(value = "queryMode")
    public Integer getQueryModeJson() {
        return Objects.isNull(queryMode) ? null : queryMode.getValue();
    }

    @JsonSetter(value = "queryMode")
    public void setQueryModeJson(Integer queryMode) {
        this.queryMode = EnumUtils.getByValue(QueryMode.class, queryMode);
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