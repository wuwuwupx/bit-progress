package com.bitprogress.securityroute.service.match;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.service.gain.RouteAnnotationService;
import com.bitprogress.securityroute.service.gain.impl.InnerRoutePropertyService;
import com.bitprogress.securityroute.service.gain.impl.TicketRoutePropertyService;

public abstract class AbstractTicketRouteMatchService extends AbstractRouteMatchService<ApiRoute> {

    public AbstractTicketRouteMatchService(TicketRoutePropertyService ticketRoutePropertyService,
                                           RouteAnnotationService<ApiRoute> routeAnnotationService) {
        super(ticketRoutePropertyService, routeAnnotationService);
    }

}
