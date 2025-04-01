package com.bitprogress.route;

import com.bitprogress.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wpx
 * Created on 2021/1/27 14:48
 * 资源对象，由方法、url和权限列表组成
 * 路由资源
 */
@Setter
@Getter
public class RouteRouse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 资源方法
     */
    private String method;

    /**
     * 资源url
     */
    private String url;

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
     * 生成资源key
     *
     * @return   String
     */
    public String routeRouseKey() {
        String method = this.method.toUpperCase();
        return method + StringUtils.SPACE + this.url;
    }

}
