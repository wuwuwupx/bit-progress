package com.bitprogress.securityspring.filter;

import com.bitprogress.securityroute.service.match.AbstractAnonymousRouteMatchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

@AllArgsConstructor
public abstract class AbstractAnonymousRequestFilter extends AbstractWhiteRequestFilter {

    private final AbstractAnonymousRouteMatchService anonymousRouteMatchService;

    /**
     * @param request
     * @return
     */
    @Override
    protected boolean filter(HttpServletRequest request) {
        String url = request.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        return anonymousRouteMatchService.matchRoute(httpMethod, url);
    }

}
