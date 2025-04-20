package com.bitprogress.securityspring.filter;

import com.bitprogress.securityroute.service.match.AbstractInnerRouteMatchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

@AllArgsConstructor
public abstract class InnerAnonymousRequestFilter extends AbstractWhiteRequestFilter {

    private final AbstractInnerRouteMatchService innerRouteMatchService;

    /**
     * @param request
     * @return
     */
    @Override
    protected boolean filter(HttpServletRequest request) {
        String url = request.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        return innerRouteMatchService.matchRoute(httpMethod, url);
    }

}
