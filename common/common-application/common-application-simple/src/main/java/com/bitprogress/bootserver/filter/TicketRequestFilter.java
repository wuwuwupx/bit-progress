package com.bitprogress.bootserver.filter;

import com.bitprogress.bootserver.service.route.TicketRouteMatchService;
import com.bitprogress.securityspring.entity.AuthenticationInfo;
import com.bitprogress.securityspring.context.AuthenticationInfoContextService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
public class TicketRequestFilter extends OncePerRequestFilter {

    private final TicketRouteMatchService ticketRouteMatchService;
    private final AuthenticationInfoContextService authenticationInfoContextService;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request     请求信息
     * @param response    响应信息
     * @param filterChain 过滤链
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        AuthenticationInfo contextInfo = authenticationInfoContextService.getContextInfo();
        if (Objects.nonNull(contextInfo)
                && Objects.nonNull(contextInfo.getIsAuthentication())
                && contextInfo.getIsAuthentication()) {
            return;
        }
        if (!ticketRouteMatchService.matchRoute(HttpMethod.valueOf(request.getMethod()), request.getRequestURI())) {
            return;
        }
        AuthenticationInfo authenticationInfo = new AuthenticationInfo();
        authenticationInfo.setIsAuthentication(true);
        authenticationInfoContextService.setContextInfo(authenticationInfo);
        try {
            filterChain.doFilter(request, response);
        } finally {
            authenticationInfoContextService.clearContextInfo();
        }
    }

}