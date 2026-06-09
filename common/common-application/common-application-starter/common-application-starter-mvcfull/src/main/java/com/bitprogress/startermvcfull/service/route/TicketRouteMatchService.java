package com.bitprogress.startermvcfull.service.route;

import com.bitprogress.securityroute.service.gain.impl.TicketRoutePropertyService;
import com.bitprogress.securityroute.service.match.AbstractTicketRouteMatchService;

public class TicketRouteMatchService extends AbstractTicketRouteMatchService {

    public TicketRouteMatchService(TicketRoutePropertyService ticketRoutePropertyService,
                                   TicketRouteAnnotationService ticketRouteAnnotationService) {
        super(ticketRoutePropertyService, ticketRouteAnnotationService);
    }

}
