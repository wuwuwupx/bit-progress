package com.bitprogress.securityroute.service;

import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.securityroute.entity.UserAuthorisationInfo;
import com.bitprogress.util.ArrayUtils;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.StringUtils;
import org.springframework.util.PatternMatchUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * 权限匹配
 */
public interface AuthorisationService {

    /**
     * 获取用户权限信息
     *
     * @return 用户权限信息
     */
    UserAuthorisationInfo getUserAuthorisation();

    /**
     * 权限匹配
     *
     * @param permissionRoute 权限路由
     * @return 是否匹配
     */
    default boolean authoritiesUserPermission(PermissionRoute permissionRoute) {
        UserAuthorisationInfo userInfo = getUserAuthorisation();
        if (Objects.isNull(userInfo)) {
            return false;
        }

        Set<String> userPermissions = userInfo.getPermissions();
        Set<String> userRoleKeys = userInfo.getRoleKeys();

        String permission = permissionRoute.getPermission();
        String lacksPermission = permissionRoute.getLacksPermission();
        String[] anyPermission = permissionRoute.getAnyPermission();
        String[] allPermission = permissionRoute.getAllPermission();
        String roleKey = permissionRoute.getRoleKey();
        String lacksRoleKey = permissionRoute.getLacksRoleKey();
        String[] anyRoleKeys = permissionRoute.getAnyRoleKeys();
        String[] allRoleKeys = permissionRoute.getAllRoleKeys();

        return (StringUtils.isEmpty(permission) || hasPermission(userPermissions, permission))
                && (StringUtils.isEmpty(lacksPermission) || lacksPermission(userPermissions, lacksPermission))
                && (ArrayUtils.isEmpty(anyPermission) || hasAnyPermission(userPermissions, anyPermission))
                && (ArrayUtils.isEmpty(allPermission) || hasAllPermission(userPermissions, allPermission))
                && (StringUtils.isEmpty(roleKey) || hasRoleKey(userRoleKeys, roleKey))
                && (StringUtils.isEmpty(lacksRoleKey) || lacksRoleKey(userRoleKeys, lacksRoleKey))
                && (ArrayUtils.isEmpty(anyRoleKeys) || hasAnyRoleKeys(userRoleKeys, anyRoleKeys))
                && (ArrayUtils.isEmpty(allRoleKeys) || hasAllPermission(userRoleKeys, allRoleKeys));
    }

    /**
     * 判断是否包含权限
     *
     * @param userPermissions 权限列表
     * @param permission      权限字符串
     * @return 用户是否具备某权限
     */
    default boolean hasPermissions(Collection<String> userPermissions, String permission) {
        return CollectionUtils.isNotEmpty(userPermissions)
                && (CollectionUtils.contains(userPermissions, getAllPermission())
                || CollectionUtils.anyMatch(userPermissions, userPermission -> StringUtils.hasText(userPermission)
                && PatternMatchUtils.simpleMatch(userPermission, permission)));
    }

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    default boolean hasPermission(Set<String> userPermissions, String permission) {
        return hasPermissions(userPermissions, permission);
    }

    /**
     * 验证用户是否不具备某权限，与 hasPermission逻辑相反
     *
     * @param permission 权限字符串
     * @return 用户是否不具备某权限
     */
    default boolean lacksPermission(Set<String> userPermissions, String permission) {
        return CollectionUtils.isEmpty(userPermissions)
                || (CollectionUtils.noneMatch(userPermissions, userPermission -> StringUtils.hasText(userPermission)
                && PatternMatchUtils.simpleMatch(userPermission, permission)));
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 权限列表
     * @return 用户是否具有以下任意一个权限
     */
    default boolean hasAnyPermission(Set<String> userPermissions, String[] permissions) {
        if (CollectionUtils.isEmpty(userPermissions)) {
            return false;
        }
        for (String permission : permissions) {
            if (permission != null && hasPermissions(userPermissions, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 权限列表
     * @return 用户是否具有以下任意一个权限
     */
    default boolean hasAllPermission(Set<String> userPermissions, String[] permissions) {
        if (CollectionUtils.isEmpty(userPermissions)) {
            return false;
        }
        if (CollectionUtils.contains(userPermissions, getAllPermission())) {
            return true;
        }
        for (String permission : permissions) {
            if (!hasPermissions(userPermissions, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param roleKey      角色字符串
     * @param userRoleKeys 用户的角色字符串
     * @return 用户是否具备某角色
     */
    default boolean hasRoleKey(Set<String> userRoleKeys, String roleKey) {
        return CollectionUtils.anyMatch(userRoleKeys, userRoleKey ->
                StringUtils.hasText(userRoleKey) && (userRoleKey.equals(getAdminKey()) || userRoleKey.equals(roleKey)));
    }

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反。
     *
     * @param roleKey      角色名称
     * @param userRoleKeys 用户的角色列表
     * @return 用户是否不具备某角色
     */
    default boolean lacksRoleKey(Set<String> userRoleKeys, String roleKey) {
        return !hasRoleKey(userRoleKeys, roleKey);
    }

    /**
     * 验证用户是否具有以下任意一个角色
     *
     * @param roleKeys     角色列表
     * @param userRoleKeys 用户的角色列表
     * @return 用户是否具有以下任意一个角色
     */
    default boolean hasAnyRoleKeys(Set<String> userRoleKeys, String[] roleKeys) {
        if (CollectionUtils.isEmpty(userRoleKeys)) {
            return false;
        }
        if (CollectionUtils.contains(userRoleKeys, getAdminKey())) {
            return true;
        }
        for (String roleKey : roleKeys) {
            if (hasRoleKey(userRoleKeys, roleKey)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否具有以下所有角色
     *
     * @param roleKeys     角色列表
     * @param userRoleKeys 用户的角色列表
     * @return 用户是否具有以下所有角色
     */
    default boolean hasAllRoleKeys(Set<String> userRoleKeys, String[] roleKeys) {
        if (CollectionUtils.isEmpty(userRoleKeys)) {
            return false;
        }
        if (CollectionUtils.contains(userRoleKeys, getAdminKey())) {
            return true;
        }
        for (String roleKey : roleKeys) {
            if (!hasRoleKey(userRoleKeys, roleKey)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 所有权限标识
     */
    default String getAllPermission() {
        return "*:*:*";
    }

    /**
     * 获取管理员对应的 key
     */
    default String getAdminKey() {
        return "admin";
    }

}
