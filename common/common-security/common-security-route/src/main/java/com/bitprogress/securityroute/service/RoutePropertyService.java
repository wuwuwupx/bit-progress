package com.bitprogress.securityroute.service;

import com.bitprogress.securityroute.entity.ApiRoute;

public interface RoutePropertyService<T extends ApiRoute> extends RouteGainService<T> {

    /**
     * 配置是否覆盖其他路由来源
     */
    Boolean isCover();

}
