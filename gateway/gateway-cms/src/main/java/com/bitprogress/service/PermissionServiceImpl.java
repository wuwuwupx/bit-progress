package com.bitprogress.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.bitprogress.auth.base.AuthException;
import com.bitprogress.auth.base.AuthMsg;
import com.bitprogress.auth.base.AuthResult;
import com.bitprogress.constant.VerifyConstant;
import com.bitprogress.route.GatewayRouteMsg;
import com.bitprogress.route.RouteRouse;
import com.bitprogress.util.ArrayUtils;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author wpx
 * 权限匹配服务
 */
@Service
public class PermissionServiceImpl implements PermissionService {

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
     * @param authResult 鉴权结果
     * @param method     方法
     * @param url        接口url
     */
    @Override
    public void authorizePermission(AuthResult<AuthMsg> authResult, String method, String url) {
        RouteRouse routeRouse = GatewayRouteMsg.getRouteRouseList(method.toUpperCase() + StringUtils.SPACE + url);
        // 没有接口相关的权限限制
        if (Objects.isNull(routeRouse)) {
            return;
        }

        // 获取用户的角色信息
        AuthMsg authMsg = authResult.getAuthMsg();
        Map<String, String> params = authMsg.getParams();
        String userPermission = params.get(VerifyConstant.PERMISSIONS);
        String userRoleKey = params.get(VerifyConstant.ROLE_KEY);

        boolean permissionResult;

        // 获取接口权限相关信息
        String permission = routeRouse.getPermission();
        String lacksPermission = routeRouse.getLacksPermission();
        String[] anyPermission = routeRouse.getAnyPermission();
        String roleKey = routeRouse.getRoleKey();
        String lacksRoleKey = routeRouse.getLacksRoleKey();
        String[] anyRoleKeys = routeRouse.getAnyRoleKeys();

        // 根据优先级匹配用户的角色权限
        if (StringUtils.nonEmpty(permission)) {
            permissionResult = hasPermission(permission, userPermission);
        } else if (StringUtils.nonEmpty(lacksPermission)) {
            permissionResult = lacksPermission(lacksPermission, userPermission);
        } else if (ArrayUtils.isNotEmpty(anyPermission)) {
            permissionResult = hasAnyPermission(anyPermission, userPermission);
        } else if (StringUtils.nonEmpty(roleKey)) {
            permissionResult = hasRoleKey(roleKey, userRoleKey);
        } else if (StringUtils.nonEmpty(lacksRoleKey)) {
            permissionResult = lacksRoleKey(lacksRoleKey, userRoleKey);
        } else if (ArrayUtils.isNotEmpty(anyRoleKeys)) {
            permissionResult = hasAnyRoleKeys(anyRoleKeys, userRoleKey);
        } else {
            // 没有需要匹配的角色权限，直接通过
            permissionResult = true;
        }
        authResult.setResult(permissionResult);
        if (!permissionResult) {
            authResult.setAuthException(AuthException.AUTH_PERMISSION_FAILED);
        }
    }

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermission(String permission, String userPermission) {
        if (StringUtils.isEmpty(userPermission)) {
            return false;
        }
        Set<String> permissions = JsonUtils.deserializeSet(userPermission, new TypeReference<Set<String>>() {
        });
        if (CollectionUtils.isEmpty(permissions)) {
            return false;
        }
        return hasPermissions(permissions, permission);
    }

    /**
     * 验证用户是否不具备某权限，与 hasPermission逻辑相反
     *
     * @param permission 权限字符串
     * @return 用户是否不具备某权限
     */
    public boolean lacksPermission(String permission, String userPermission) {
        return !hasPermission(permission, userPermission);
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermission(String[] permissions, String userPermission) {
        if (StringUtils.isEmpty(userPermission)) {
            return false;
        }
        Set<String> userPermissions = JsonUtils.deserializeSet(userPermission, new TypeReference<Set<String>>() {
        });
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
     * 判断用户是否拥有某个角色
     *
     * @param roleKey     角色字符串
     * @param userRoleKey 用户的角色字符串
     * @return 用户是否具备某角色
     */
    public boolean hasRoleKey(String roleKey, String userRoleKey) {
        return SUPER_ADMIN.equals(userRoleKey) || userRoleKey.equals(roleKey);
    }

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反。
     *
     * @param roleKey     角色名称
     * @param userRoleKey 用户的角色列表
     * @return 用户是否不具备某角色
     */
    public boolean lacksRoleKey(String roleKey, String userRoleKey) {
        return !hasRoleKey(roleKey, userRoleKey);
    }

    /**
     * 验证用户是否具有以下任意一个角色
     *
     * @param roleKeys    角色列表
     * @param userRoleKey 用户的角色列表
     * @return 用户是否具有以下任意一个角色
     */
    public boolean hasAnyRoleKeys(String[] roleKeys, String userRoleKey) {
        for (String roleKey : roleKeys) {
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
    private boolean hasPermissions(Set<String> authorities, String permission) {
        return authorities.stream().filter(StringUtils::hasText)
                .anyMatch(x -> ALL_PERMISSION.contains(x) || PatternMatchUtils.simpleMatch(x, permission));
    }

}
