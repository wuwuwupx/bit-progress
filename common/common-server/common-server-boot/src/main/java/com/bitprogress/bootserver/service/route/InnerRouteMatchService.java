package com.bitprogress.bootserver.service.route;

import com.bitprogress.securityroute.service.match.AbstractInnerRouteMatchService;
import com.bitprogress.securityroute.service.gain.impl.InnerRoutePropertyService;

public class InnerRouteMatchService extends AbstractInnerRouteMatchService {

    public InnerRouteMatchService(InnerRoutePropertyService innerRoutePropertyService,
                                  InnerRouteAnnotationService innerRouteAnnotationService) {
        super(innerRoutePropertyService, innerRouteAnnotationService);
    }

}
