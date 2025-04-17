package com.bitprogress.securityroute.service.match;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.service.gain.RouteAnnotationService;
import com.bitprogress.securityroute.service.gain.impl.AnonymousRoutePropertyService;

public abstract class AbstractAnonymousRouteMatchService extends AbstractRouteMatchService<ApiRoute> {

    public AbstractAnonymousRouteMatchService(AnonymousRoutePropertyService anonymousRoutePropertyService,
                                              RouteAnnotationService<ApiRoute> routeAnnotationService) {
        super(anonymousRoutePropertyService, routeAnnotationService);
    }

}
