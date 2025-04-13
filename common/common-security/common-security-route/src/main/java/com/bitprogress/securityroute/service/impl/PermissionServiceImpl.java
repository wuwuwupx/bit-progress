package com.bitprogress.securityroute.service.impl;

import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.util.ArrayUtils;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
* 权限匹配服务
 */
@Service
public class PermissionServiceImpl {

    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    /**
     * 检验接口权限
     * 通过 method+url 获取接口的权限信息
     *
     * @param method     方法
     * @param url        接口url
     */
    public void authorizePermission(String method, String url) {

        PermissionRoute routeRouse = new PermissionRoute();

        // 获取用户的角色信息
        Map<String, String> params = CollectionUtils.emptyMap();
        Set<String> userPermission = CollectionUtils.emptySet();
        Set<String>  userRoleKey = CollectionUtils.emptySet();

        boolean permissionResult;

        // 获取接口权限相关信息
        String permission = routeRouse.getPermission();
        String lacksPermission = routeRouse.getLacksPermission();
        String[] anyPermission = routeRouse.getAnyPermission();
        Long roleKey = routeRouse.getRoleId();
        Long lacksRoleKey = routeRouse.getLacksRoleId();
        long[] anyRoleKeys = routeRouse.getAnyRoleIds();
        long[] allRoleKeys = routeRouse.getAllRoleIds();

        // 根据优先级匹配用户的角色权限
        if (StringUtils.isNotEmpty(permission)) {
            permissionResult = hasPermission(permission, userPermission);
        } else if (StringUtils.isNotEmpty(lacksPermission)) {
            permissionResult = lacksPermission(lacksPermission, userPermission);
        } else if (ArrayUtils.isNotEmpty(anyPermission)) {
            permissionResult = hasAnyPermission(anyPermission, userPermission);
        } else if (Objects.nonNull(roleKey)) {
            permissionResult = hasRoleKey(roleKey, userRoleKey);
        } else if (Objects.nonNull(lacksRoleKey)) {
            permissionResult = lacksRoleKey(lacksRoleKey, userRoleKey);
        } else if (ArrayUtils.isNotEmpty(anyRoleKeys)) {
            permissionResult = hasAnyRoleKeys(anyRoleKeys, userRoleKey);
        } else {
            // 没有需要匹配的角色权限，直接通过
            permissionResult = true;
        }
    }

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermission(String permission, Set<String>  userPermission) {
        if (CollectionUtils.isEmpty(userPermission)) {
            return false;
        }
        return hasPermissions(userPermission, permission);
    }

    /**
     * 验证用户是否不具备某权限，与 hasPermi逻辑相反
     *
     * @param permission 权限字符串
     * @return 用户是否不具备某权限
     */
    public boolean lacksPermission(String permission, Set<String>  userPermission) {
        return !hasPermission(permission, userPermission);
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermission(String[] permissions, Set<String>  userPermission) {
        if (CollectionUtils.isEmpty(userPermission)) {
            return false;
        }
        for (String permission : permissions) {
            if (permission != null && hasPermissions(userPermission, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param roleKey     角色字符串
     * @param userRoleKey 用户的角色字符串
     * @return 用户是否具备某角色
     */
    public boolean hasRoleKey(Long roleKey, String userRoleKey) {
        return SUPER_ADMIN.equals(userRoleKey) || userRoleKey.equals(roleKey);
    }

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反。
     *
     * @param roleKey     角色名称
     * @param userRoleKey 用户的角色列表
     * @return 用户是否不具备某角色
     */
    public boolean lacksRoleKey(Long roleKey, String userRoleKey) {
        return !hasRoleKey(roleKey, userRoleKey);
    }

    /**
     * 验证用户是否具有以下任意一个角色
     *
     * @param roleKeys    角色列表
     * @param userRoleKey 用户的角色列表
     * @return 用户是否具有以下任意一个角色
     */
    public boolean hasAnyRoleKeys(long[] roleKeys, String userRoleKey) {
        for (long roleKey : roleKeys) {
            if (hasRoleKey(roleKey, userRoleKey)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否包含权限
     *
     * @param authorities 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Collection<String> authorities, String permission) {
        return authorities.stream().filter(Objects::nonNull)
                .anyMatch(x -> ALL_PERMISSION.contains(x) || PatternMatchUtils.simpleMatch(x, permission));
    }

}
