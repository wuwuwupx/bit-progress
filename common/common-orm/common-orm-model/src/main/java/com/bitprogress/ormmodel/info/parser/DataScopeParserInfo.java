package com.bitprogress.ormmodel.info.parser;

import com.bitprogress.basemodel.util.EnumUtils;
import com.bitprogress.ormmodel.annotation.DataScopeParserMode;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * sql 解析模式信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataScopeParserInfo extends BaseParserInfo {

    /**
     * 数据范围类型
     */
    private DataScopeType dataScopeType;

    /**
     * 根据注解创建
     *
     * @param parserMode sql解析模式注解
     * @return sql 解析模式信息
     */
    public static DataScopeParserInfo createByDataScopeParserMode(DataScopeParserMode parserMode) {
        DataScopeParserInfo sqlParserInfo = new DataScopeParserInfo();
        sqlParserInfo.setEnable(true);
        sqlParserInfo.setParserType(parserMode.parserType());
        sqlParserInfo.setSqlTypes(parserMode.sqlTypes());
        sqlParserInfo.setDataScopeType(parserMode.dataScopeType());
        sqlParserInfo.setQueryMode(parserMode.queryMode());
        sqlParserInfo.setRpcPropagate(parserMode.rpcPropagate());
        return sqlParserInfo;
    }

    /**
     * 创建禁用状态的sql 解析模式
     */
    public static DataScopeParserInfo createDisable() {
        DataScopeParserInfo sqlParserInfo = new DataScopeParserInfo();
        sqlParserInfo.setEnable(false);
        return sqlParserInfo;
    }

    @JsonGetter(value = "dataScopeType")
    public Integer getDataScopeTypeJson() {
        return Objects.isNull(dataScopeType) ? null : dataScopeType.getValue();
    }

    @JsonSetter(value = "dataScopeType")
    public void setDataScopeTypeJson(Integer dataScopeType) {
        this.dataScopeType = EnumUtils.getByValue(DataScopeType.class, dataScopeType);
    }

}