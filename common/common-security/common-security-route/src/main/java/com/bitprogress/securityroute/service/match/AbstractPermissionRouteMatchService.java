package com.bitprogress.securityroute.service.match;

import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.securityroute.service.gain.RouteAnnotationService;
import com.bitprogress.securityroute.service.gain.impl.PermissionRoutePropertyService;

public abstract class AbstractPermissionRouteMatchService extends AbstractRouteMatchService<PermissionRoute> {

    public AbstractPermissionRouteMatchService(PermissionRoutePropertyService permissionRoutePropertyService,
                                               RouteAnnotationService<PermissionRoute> routeAnnotationService) {
        super(permissionRoutePropertyService, routeAnnotationService);
    }

}
