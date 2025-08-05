package com.bitprogress.bootserver.service.route;

import com.bitprogress.securityroute.service.match.AbstractAnonymousRouteMatchService;
import com.bitprogress.securityroute.service.gain.impl.AnonymousRoutePropertyService;

public class AnonymousRouteMatchService extends AbstractAnonymousRouteMatchService {

    public AnonymousRouteMatchService(AnonymousRoutePropertyService anonymousRoutePropertyService,
                                      AnonymousRouteAnnotationService anonymousRouteAnnotationService) {
        super(anonymousRoutePropertyService, anonymousRouteAnnotationService);
    }

}
