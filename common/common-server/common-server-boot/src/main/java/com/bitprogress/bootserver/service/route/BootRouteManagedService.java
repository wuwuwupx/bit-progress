package com.bitprogress.bootserver.service.route;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.securityroute.service.RouteManagedService;
import com.bitprogress.bootserver.context.route.impl.AnonymousRouteContextService;
import com.bitprogress.bootserver.context.route.impl.InnerRouteContextService;
import com.bitprogress.bootserver.context.route.impl.PermissionRouteContextService;
import com.bitprogress.bootserver.context.route.impl.TicketRouteContextService;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class BootRouteManagedService extends RouteManagedService {

    private final AnonymousRouteContextService anonymousRouteContextService;
    private final InnerRouteContextService innerRouteContextService;
    private final TicketRouteContextService ticketRouteContextService;
    private final PermissionRouteContextService permissionRouteContextService;


    /**
     * 发布路由
     *
     * @param anonymousRoutes  匿名路由
     * @param innerRoutes      内部路由
     * @param ticketRoutes     ticket路由
     * @param permissionRoutes 权限路由
     */
    @Override
    protected void publishRoute(Set<ApiRoute> anonymousRoutes,
                                Set<ApiRoute> innerRoutes,
                                Set<ApiRoute> ticketRoutes,
                                Set<PermissionRoute> permissionRoutes) {
        if (Objects.nonNull(anonymousRoutes)) {
            anonymousRouteContextService.setContextInfo(anonymousRoutes);
        }
        if (Objects.nonNull(innerRoutes)) {
            innerRouteContextService.setContextInfo(innerRoutes);
        }
        if (Objects.nonNull(ticketRoutes)) {
            ticketRouteContextService.setContextInfo(innerRoutes);
        }
        if (Objects.nonNull(permissionRoutes)) {
            permissionRouteContextService.setContextInfo(permissionRoutes);
        }
    }

    /**
     * Invoked by the containing {@code BeanFactory} on destruction of a bean.
     *
     * @throws Exception in case of shutdown errors. Exceptions will get logged
     *                   but not rethrown to allow other beans to release their resources as well.
     */
    @Override
    public void destroy() throws Exception {
        anonymousRouteContextService.clearContextInfo();
        innerRouteContextService.clearContextInfo();
        ticketRouteContextService.clearContextInfo();
        permissionRouteContextService.clearContextInfo();
    }

}
