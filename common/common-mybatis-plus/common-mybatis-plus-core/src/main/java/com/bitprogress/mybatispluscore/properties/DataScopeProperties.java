package com.bitprogress.mybatispluscore.properties;

import com.bitprogress.ormmodel.enums.DataScopeMode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mybatis plus 数据权限插件配置
 */
@Data
@ConfigurationProperties("mybatis-plus.data-scope")
public class DataScopeProperties {

    /**
     * 启用数据权限插件
     * 默认不启用
     */
    private Boolean enabled = false;

    /**
     * 数据权限模式
     */
    private DataScopeMode mode = DataScopeMode.SINGLE;

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

    /**
     * 白名单
     */
    private List<String> ignoreInsertTables = new ArrayList<>();

    /**
     * 启用名单
     * 为空表示所有的表都启用，不为空则启动配置的表
     * 当启用名单不为空时，优先级高于 白名单
     */
    private List<String> enableInsertTables = new ArrayList<>();

    /**
     * 数据权限字段
     * key 表名
     * value 数据权限字段
     */
    private Map<String, String> dataScopeColumn = new HashMap<>();

    /**
     * 匹配当前用户数据权限的字段
     * key 表名
     * value 数据权限字段
     */
    private Map<String, String> selfWhereColumn = new HashMap<>();

}
