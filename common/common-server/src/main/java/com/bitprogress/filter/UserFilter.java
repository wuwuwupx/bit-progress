package com.bitprogress.filter;

import com.bitprogress.basecontext.context.DispatcherContext;
import com.bitprogress.ormcontext.context.TenantContext;
import com.bitprogress.ormcontext.entity.TenantInfo;
import com.bitprogress.request.enums.RequestType;
import com.bitprogress.usercontext.context.UserContext;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.util.DispatcherUtils;
import com.bitprogress.util.TenantUtils;
import com.bitprogress.util.UserUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import java.io.IOException;

/**
 * 用户信息过滤器
 */
@WebFilter
public class UserFilter implements Filter {

    /**
     * The <code>doFilter</code> method of the Filter is called by the container each time a request/response pair is passed
     * through the chain due to a client request for a resource at the end of the chain. The FilterChain passed in to this
     * method allows the Filter to pass on the request and response to the next entity in the chain.
     *
     * <p>
     * A typical implementation of this method would follow the following pattern:
     * <ol>
     * <li>Examine the request
     * <li>Optionally wrap the request object with a custom implementation to filter content or headers for input filtering
     * <li>Optionally wrap the response object with a custom implementation to filter content or headers for output
     * filtering
     * <li>
     * <ul>
     * <li><strong>Either</strong> invoke the next entity in the chain using the FilterChain object
     * (<code>chain.doFilter()</code>),
     * <li><strong>or</strong> not pass on the request/response pair to the next entity in the filter chain to block the
     * request processing
     * </ul>
     * <li>Directly set headers on the response after invocation of the next entity in the filter chain.
     * </ol>
     *
     * @param request  the <code>ServletRequest</code> object contains the client's request
     * @param response the <code>ServletResponse</code> object contains the filter's response
     * @param chain    the <code>FilterChain</code> for invoking the next filter or the resource
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with the filter's normal operation
     * @see UnavailableException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        try {
            RequestType requestType = DispatcherUtils.getDispatcherType(httpRequest);
            if (RequestType.USER_REQUEST.equals(requestType)) {
                DispatcherContext.markUserRequest();
                UserInfo userInfo = UserUtils.getUserInfo(httpRequest);
                UserContext.setUserInfo(userInfo);
                TenantInfo tenantInfo = TenantUtils.getTenantInfo(httpRequest);
                TenantContext.setTenantInfo(tenantInfo);
            } else {
                DispatcherContext.markAnonymousRequest();
            }
            chain.doFilter(request, response);
        } finally {
            DispatcherContext.clearDispatcherType();
            UserContext.clearUserInfo();
            TenantContext.clearTenantInfo();
        }
    }

}
