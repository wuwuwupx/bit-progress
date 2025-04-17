package com.bitprogress.bootserver.service.route;

import com.bitprogress.securityroute.service.gain.impl.PermissionRoutePropertyService;
import com.bitprogress.securityroute.service.match.AbstractPermissionRouteMatchService;

public class PermissionRouteMatchService extends AbstractPermissionRouteMatchService {

    public PermissionRouteMatchService(PermissionRoutePropertyService propertyService,
                                       PermissionRouteAnnotationService annotationService) {
        super(propertyService, annotationService);
    }

}
