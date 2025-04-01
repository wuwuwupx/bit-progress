package com.bitprogress.securityroute.entity;

import com.bitprogress.securityroute.annotation.Permission;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;

/**
 * Permission Route
 */
@Data
public class PermissionRoute extends ApiRoute {

    /**
     * 验证用户是否具备某权限
     */
    private String permission;

    /**
     * 验证用户是否不具备某权限，与 hasPermission逻辑相反
     */
    private String lacksPermission;

    /**
     * 验证用户是否具有以下任意一个权限
     */
    private String[] anyPermission;

    /**
     * 验证用户是否具有以下所有权限
     */
    private String[] allPermission;

    /**
     * 判断用户是否拥有某个角色
     */
    private Long roleId;

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反
     */
    private Long lacksRoleId;

    /**
     * 验证用户是否具有以下任意一个角色
     */
    private long[] anyRoleIds;

    /**
     * 验证用户是否具有以下所有角色
     */
    private long[] allRoleIds;

    public static PermissionRoute getByPermission(Permission permission, RequestMethod method, String methodName) {
        PermissionRoute permissionRoute = new PermissionRoute();
        permissionRoute.setMethod(method.asHttpMethod());
        permissionRoute.setUrl(methodName);
        permissionRoute.setPermission(permission.permission());
        permissionRoute.setLacksPermission(permission.lacksPermission());
        permissionRoute.setAnyPermission(permission.anyPermission());
        permissionRoute.setAllPermission(permission.allPermission());
        permissionRoute.setRoleId(permission.roleId());
        permissionRoute.setLacksRoleId(permission.lacksRoleId());
        permissionRoute.setAnyRoleIds(permission.anyRoleIds());
        permissionRoute.setAllRoleIds(permission.allRoleIds());
        return permissionRoute;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
