package com.bitprogress.ormmodel.info.parser;

import com.bitprogress.basemodel.util.EnumUtils;
import com.bitprogress.ormmodel.annotation.TenantParserMode;
import com.bitprogress.ormmodel.enums.TenantType;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * sql 解析模式信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantParserInfo extends BaseParserInfo {

    /**
     * 租户类型
     */
    private TenantType tenantType;

    /**
     * 根据注解创建
     *
     * @param tenantParserMode sql解析模式注解
     * @return sql 解析模式信息
     */
    public static TenantParserInfo createBySqlParserMode(TenantParserMode tenantParserMode) {
        TenantParserInfo tenantParserInfo = new TenantParserInfo();
        tenantParserInfo.setEnable(true);
        tenantParserInfo.setParserType(tenantParserMode.parserType());
        tenantParserInfo.setSqlTypes(tenantParserMode.sqlTypes());
        tenantParserInfo.setTenantType(tenantParserMode.tenantType());
        tenantParserInfo.setRpcPropagate(tenantParserMode.rpcPropagate());
        return tenantParserInfo;
    }

    /**
     * 创建禁用状态的sql 解析模式
     */
    public static TenantParserInfo createDisable() {
        TenantParserInfo tenantParserInfo = new TenantParserInfo();
        tenantParserInfo.setEnable(false);
        return tenantParserInfo;
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

}