package com.bitprogress.bootserver.filter;

import com.bitprogress.ormcontext.service.TenantContextService;
import com.bitprogress.ormcontext.service.impl.SingleTypeDataScopeContextService;
import com.bitprogress.ormmodel.info.user.SingleTypeDataScopeInfo;
import com.bitprogress.ormmodel.info.user.UserTenantInfo;
import com.bitprogress.request.enums.RequestType;
import com.bitprogress.securityroute.entity.UserAuthorisationInfo;
import com.bitprogress.securityroute.service.context.UserAuthorisationContextService;
import com.bitprogress.servercore.service.UserContextMaintenanceService;
import com.bitprogress.servercore.util.DispatcherUtils;
import com.bitprogress.servercore.util.UserUtils;
import com.bitprogress.systemcontext.service.DispatcherContextService;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.usercontext.service.UserInfoContextService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 用户信息过滤器
 */
@WebFilter
public class UserFilter implements Filter, UserContextMaintenanceService {

    @Autowired
    private TenantContextService tenantContextService;
    @Autowired
    private SingleTypeDataScopeContextService dataScopeContextService;
    @Autowired
    private UserAuthorisationContextService userAuthorisationContextService;

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
            // 实际应该在token解析的时候获取
            RequestType requestType = DispatcherUtils.getRequestType(httpRequest);
            if (RequestType.USER_REQUEST.equals(requestType)) {
                DispatcherContextService.markUserRequest();
                UserInfo userInfo = UserUtils.analysisUserInfo(httpRequest);
                setContextByUserInfo(userInfo);
            } else {
                DispatcherContextService.markAnonymousRequest();
            }
            chain.doFilter(request, response);
        } finally {
            DispatcherContextService.clearDispatcherType();
            clearContext();
        }
    }

    /**
     * 设置用户上下文
     *
     * @param userInfo 用户信息
     */
    @Override
    public void setContextByUserInfo(UserInfo userInfo) {
        UserInfoContextService.setUserInfo(userInfo);
        UserTenantInfo userTenantInfo = UserUtils.getTenantInfo(userInfo);
        tenantContextService.setUserInfo(userTenantInfo);
        SingleTypeDataScopeInfo dataScopeInfo = UserUtils.getDataScopeInfo(userInfo);
        dataScopeContextService.setUserInfo(dataScopeInfo);
        UserAuthorisationInfo userAuthorisationInfo = UserUtils.getUserAuthorisationInfo(userInfo);
        userAuthorisationContextService.setContextInfo(userAuthorisationInfo);
    }

    /**
     * 清除用户上下文
     */
    @Override
    public void clearContext() {
        UserInfoContextService.clearUserInfo();
        tenantContextService.clearUserInfo();
        tenantContextService.clearParserInfo();
        dataScopeContextService.clearUserInfo();
        dataScopeContextService.clearParserInfo();
        userAuthorisationContextService.clearContextInfo();
    }

}
