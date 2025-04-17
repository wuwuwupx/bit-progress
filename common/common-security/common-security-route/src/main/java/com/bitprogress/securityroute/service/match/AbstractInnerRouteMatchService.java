package com.bitprogress.securityroute.service.match;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.service.gain.RouteAnnotationService;
import com.bitprogress.securityroute.service.gain.impl.InnerRoutePropertyService;

public abstract class AbstractInnerRouteMatchService extends AbstractRouteMatchService<ApiRoute> {

    public AbstractInnerRouteMatchService(InnerRoutePropertyService innerRoutePropertyService,
                                          RouteAnnotationService<ApiRoute> routeAnnotationService) {
        super(innerRoutePropertyService, routeAnnotationService);
    }

}
