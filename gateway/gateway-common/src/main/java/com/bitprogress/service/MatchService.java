package com.bitprogress.service;

/**
 * @author wuwuwupx
 * 路由匹配服务
 */
public interface MatchService {

    /**
     * 查询是否为不校验路由
     *
     * @param  method 校验路径method
     * @param  url 检验路径
     * @return boolean
     */
    boolean ignoreAuthentication(String method, String url);

}
