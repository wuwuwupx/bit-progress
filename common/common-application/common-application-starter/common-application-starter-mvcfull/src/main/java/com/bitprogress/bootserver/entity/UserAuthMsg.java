package com.bitprogress.bootserver.entity;

import com.bitprogress.securityspring.entity.AuthMsg;
import com.bitprogress.usercontext.entity.CombinationDataScope;
import com.bitprogress.usercontext.enums.DataScopeType;
import com.bitprogress.usercontext.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

/**
 * 用户信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthMsg extends AuthMsg {

    /**
     * 用户ID
     */
    private String userId;

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
    private Set<String> roleIds;

    /**
     * 角色key列表
     */
    private Set<String> roleKeys;

    /**
     * 角色权限列表
     */
    private Set<String> permissions;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 是否可操作所有租户
     * 为防止越权，默认不可操作
     */
    private Boolean canOperateAllTenant;

    /**
     * 可操作租户ID
     */
    private Set<String> operateTenantIds;

    /**
     * 数据范围类型
     */
    private DataScopeType dataScopeType;

    /**
     * 数据范围类型
     */
    private Set<DataScopeType> dataScopeTypes;

    /**
     * 数据范围类型
     */
    private Set<CombinationDataScope> combinationDataScopes;

    /**
     * 基础数据权限，即租户或系统的基础数据权限
     */
    private String baseDataScope;

    /**
     * 自身数据范围
     */
    private String dataScope;

    /**
     * 可查询数据范围列表
     */
    private Set<String> dataScopes;

    /**
     * 可查询数据范围列表
     */
    private Set<String> belongDataScopes;

    /**
     * 附带信息
     */
    private Map<String, String> attendMessage;

}
