package com.bitprogress.usercontext.entity;

import com.bitprogress.usercontext.enums.DataScopeType;
import com.bitprogress.usercontext.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

/**
 * 用户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型
     */
    private UserType userType;

    /**
     * 角色ID
     */
    private Set<Long> roleIds;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 是否可操作所有租户
     * 为防止越权，默认不可操作
     */
    private Boolean operateAllTenant;

    /**
     * 可操作租户ID
     */
    private Set<Long> operateTenantIds;

    /**
     * 数据范围类型
     */
    private DataScopeType dataScopeType;

    /**
     * 自身数据范围
     */
    private String dataScope;

    /**
     * 可查询数据范围列表
     */
    private Set<String> dataScopes;

    /**
     * 附带信息
     */
    private Map<String, String> attendMessage;

}
