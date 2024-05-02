package com.bitprogress.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wpx
 * Created on 2021/1/26 16:56
 * 
 */
public class GatewayRouteMsg {

    /**
     * 路由ID列表
     */
    private static final List<String> ROUTE_LIST = new ArrayList<>();

    /**
     * 路由白名单
     */
    private static final List<String> WHITE_ROUTE = new ArrayList<>();

    /**
     * 路由资源
     */
    private static final Map<String, RouteRouse> ROUTE_ROUSE_MAP = new HashMap<>();

    /**
     * 路由API_TOKEN
     */
    private static final Map<String, String> ROUTE_API_TOKEN_MAP = new HashMap<>();

    /**
     * 获取路由ID列表
     *
     * @return   List<String>
     */
    public static List<String> getRouteList() {
        return ROUTE_LIST;
    }

    /**
     * 获取路由白名单
     *
     * @return   List<String>
     */
    public static List<String> getWhiteRoute() {
        return WHITE_ROUTE;
    }

    /**
     * 获取接口的资源列表
     *
     * @param    key
     * @return   List<String>
     */
    public static RouteRouse getRouteRouseList(String key) {
        return ROUTE_ROUSE_MAP.get(key);
    }

    /**
     * 获取路由的token
     *
     * @param    key
     * @return   String
     */
    public static String getRouteApiToken(String key) {
        return ROUTE_API_TOKEN_MAP.get(key);
    }

    /**
     * 添加路由ID
     *
     * @param    route
     */
    public static void addRoute(String route) {
        ROUTE_LIST.add(route);
    }

    /**
     * 添加路由白名单
     *
     * @param    whiteRoutes
     */
    public static void addWhiteRoute(List<String> whiteRoutes) {
        WHITE_ROUTE.addAll(whiteRoutes);
    }

    /**
     * 添加路由资源
     *
     * @param    roleMap
     */
    public static void addRouteRouse(Map<String, RouteRouse> roleMap) {
        ROUTE_ROUSE_MAP.putAll(roleMap);
    }

    /**
     * 添加路由API_TOKEN
     *
     * @param    tokenMap
     */
    public static void addRouteApiTokenMap(Map<String, String> tokenMap) {
        ROUTE_API_TOKEN_MAP.putAll(tokenMap);
    }

    /**
     * 清空路由ID
     */
    public static void clearRouteList() {
        ROUTE_LIST.clear();
    }

    /**
     * 清空路由白名单
     */
    public static void clearWhiteRoute() {
        WHITE_ROUTE.clear();
    }

    /**
     * 清空路由资源
     */
    public static void clearRouteRouseMap() {
        ROUTE_ROUSE_MAP.clear();
    }

    /**
     * 清空路由API_TOKEN
     */
    public static void clearRouteApiTokenMap() {
        ROUTE_API_TOKEN_MAP.clear();
    }

}
