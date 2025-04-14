package com.bitprogress.securityroute.entity;

import com.bitprogress.securityroute.annotation.Permission;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

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
    private String roleKey;

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反
     */
    private String lacksRoleKey;

    /**
     * 验证用户是否具有以下任意一个角色
     */
    private String[] anyRoleKeys;

    /**
     * 验证用户是否具有以下所有角色
     */
    private String[] allRoleKeys;

    public static PermissionRoute getByPermission(Permission permission, RequestMethod method, String methodName) {
        PermissionRoute permissionRoute = new PermissionRoute();
        permissionRoute.setMethod(method.asHttpMethod());
        permissionRoute.setUrl(methodName);
        permissionRoute.setPermission(permission.permission());
        permissionRoute.setLacksPermission(permission.lacksPermission());
        permissionRoute.setAnyPermission(permission.anyPermission());
        permissionRoute.setAllPermission(permission.allPermission());
        permissionRoute.setRoleKey(permission.roleKey());
        permissionRoute.setLacksRoleKey(permission.lacksRoleKey());
        permissionRoute.setAnyRoleKeys(permission.anyRoleKeys());
        permissionRoute.setAllRoleKeys(permission.allRoleKeys());
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
