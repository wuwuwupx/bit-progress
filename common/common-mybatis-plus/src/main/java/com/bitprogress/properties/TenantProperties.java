package com.bitprogress.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
     * 默认不启用
     */
    private Boolean enabled = false;

    /**
     * 白名单
     */
    private List<String> ignoreTables = new ArrayList<>();

    /**
     * 启用名单
     * 为空表示所有的表都启用，不为空则启动配置的表
     * 当启用名单不为空时，优先级高于 白名单
     */
    private List<String> enableTables = new ArrayList<>();

}
