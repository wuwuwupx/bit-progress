package com.bitprogress.bootserver.filter;

import com.bitprogress.bootserver.entity.UserAuthMsg;
import com.bitprogress.bootserver.mapstruct.UserMapStruct;
import com.bitprogress.exception.CommonException;
import com.bitprogress.ormcontext.service.TenantContextService;
import com.bitprogress.ormcontext.service.impl.SingleTypeDataScopeContextService;
import com.bitprogress.ormmodel.info.user.SingleTypeDataScopeInfo;
import com.bitprogress.ormmodel.info.user.UserTenantInfo;
import com.bitprogress.securityspring.context.AuthenticationInfoContextService;
import com.bitprogress.securityspring.context.UserAuthorisationContextService;
import com.bitprogress.securityspring.entity.AuthResult;
import com.bitprogress.securityspring.entity.AuthenticationInfo;
import com.bitprogress.securityspring.entity.UserAuthorisationInfo;
import com.bitprogress.securityspring.service.AuthenticationService;
import com.bitprogress.servercore.util.UserUtils;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.usercontext.service.UserInfoContextService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final UserMapStruct MAP_STRUCT = UserMapStruct.INSTANCE;

    private final AuthenticationInfoContextService authenticationInfoContextService;
    private final AuthenticationService authenticationService;
    private final UserAuthorisationContextService userAuthorisationContextService;
    private final TenantContextService tenantContextService;
    private final SingleTypeDataScopeContextService dataScopeContextService;

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
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        AuthResult<UserAuthMsg> authResult = authenticationService.checkToken(authorization, UserAuthMsg.class);
        if (!authResult.getResult()) {
            throw CommonException.error(authResult.getAuthException());
        }
        UserAuthorisationInfo authorisationInfo = new UserAuthorisationInfo();
        UserAuthMsg authMsg = authResult.getAuthMsg();

        UserInfo userInfo = MAP_STRUCT.toUserInfo(authMsg);
        UserInfoContextService.setUserInfo(userInfo);

        UserTenantInfo userTenantInfo = UserUtils.getTenantInfo(userInfo);
        tenantContextService.setUserInfo(userTenantInfo);
        SingleTypeDataScopeInfo singleTypeDataScopeInfo = UserUtils.getDataScopeInfo(userInfo);
        dataScopeContextService.setUserInfo(singleTypeDataScopeInfo);


        authorisationInfo.setPermissions(authMsg.getPermissions());
        authorisationInfo.setRoleKeys(authMsg.getRoleKeys());
        userAuthorisationContextService.setContextInfo(authorisationInfo);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo();
        authenticationInfo.setIsAuthentication(true);
        authenticationInfoContextService.setContextInfo(authenticationInfo);
        try {
            filterChain.doFilter(request, response);
        } finally {
            userAuthorisationContextService.clearContextInfo();
            authenticationInfoContextService.clearContextInfo();
            UserInfoContextService.clearUserInfo();
            tenantContextService.clearUserInfo();
            dataScopeContextService.clearUserInfo();
        }
    }

}