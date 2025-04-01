package com.bitprogress.securityroute.context;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.entity.PermissionRoute;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class RouteContext {

    /**
     * 权限路由
     */
    private static final Set<PermissionRoute> PERMISSION_ROUTES = new HashSet<>();

    /**
     * 匿名路由
     */
    private static final Set<ApiRoute> ANONYMOUS_ROUTES = new HashSet<>();

    /**
     * 内部路由
     */
    private static final Set<ApiRoute> INNER_ROUTES = new HashSet<>();

    /**
     * 添加权限路由
     *
     * @param permissionRoutes 权限路由
     */
    public static void addPermissionRoutes(Set<PermissionRoute> permissionRoutes) {
        PERMISSION_ROUTES.addAll(permissionRoutes);
    }

    /**
     * 设置权限路由
     *
     * @param permissionRoutes 权限路由
     */
    public static void setPermissionRoutes(List<PermissionRoute> permissionRoutes) {
        PERMISSION_ROUTES.clear();
        PERMISSION_ROUTES.addAll(permissionRoutes);
    }

    /**
     * 添加匿名路由
     *
     * @param anonymousRoutes 匿名路由
     */
    public static void addAnonymousRoutes(Set<ApiRoute> anonymousRoutes) {
        ANONYMOUS_ROUTES.addAll(anonymousRoutes);
    }

    /**
     * 设置匿名路由
     *
     * @param anonymousRoutes 匿名路由
     */
    public static void setAnonymousRoutes(List<ApiRoute> anonymousRoutes) {
        ANONYMOUS_ROUTES.clear();
        ANONYMOUS_ROUTES.addAll(anonymousRoutes);
    }

    /**
     * 添加内部路由
     *
     * @param innerRoutes 内部路由
     */
    public static void addInnerRoutes(Set<ApiRoute> innerRoutes) {
        INNER_ROUTES.addAll(innerRoutes);
    }

    /**
     * 设置内部路由
     *
     * @param innerRoutes 内部路由
     */
    public static void setInnerRoutes(List<ApiRoute> innerRoutes) {
        INNER_ROUTES.clear();
        INNER_ROUTES.addAll(innerRoutes);
    }

    /**
     * 清空路由
     */
    public static void clear() {
        PERMISSION_ROUTES.clear();
        ANONYMOUS_ROUTES.clear();
        INNER_ROUTES.clear();
    }

}
