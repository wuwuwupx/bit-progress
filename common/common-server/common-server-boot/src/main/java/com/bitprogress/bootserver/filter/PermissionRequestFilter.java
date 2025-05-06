package com.bitprogress.bootserver.filter;

import com.bitprogress.bootserver.service.route.PermissionRouteMatchService;
import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.securityspring.service.AuthorisationService;
import com.bitprogress.securityspring.token.TicketAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PermissionRequestFilter extends OncePerRequestFilter {

    private final PermissionRouteMatchService permissionRouteMatchService;
    private final AuthorisationService authorisationService;

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
        Optional<PermissionRoute> closestApiRoute = permissionRouteMatchService
                .getClosestApiRoute(HttpMethod.valueOf(request.getMethod()), request.getRequestURI());
        if (closestApiRoute.isEmpty()) {
            return;
        }
        boolean permission = authorisationService.authoritiesUserPermission(closestApiRoute.get());
        if (!permission) {
            TicketAuthenticationToken authenticationToken = new TicketAuthenticationToken();
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

}