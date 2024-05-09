package com.bitprogress.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis plus 租户插件白名单配置
 */
@Data
@Component
@ConfigurationProperties("mybatis-plus.tenant")
public class TenantProperties {

    /**
     * 启用租户插件
     */
    private Boolean enabled;

    /**
     * 忽略白名单
     */
    private List<String> ignoreTables = new ArrayList<>();

}
