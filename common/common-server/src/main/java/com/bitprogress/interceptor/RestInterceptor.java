package com.bitprogress.interceptor;

import com.bitprogress.exception.CommonException;
import com.bitprogress.property.ApplicationTokenProperties;
import com.bitprogress.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;


import static com.bitprogress.constant.VerifyConstant.FEIGN_COMMON_TOKEN;
import static com.bitprogress.constant.VerifyConstant.ROUTE_REST_TOKEN;

/**
 * @author wuwuwupx
 * create on 2021/6/21 1:41
 * REST拦截器，检验对内部提供的接口token
 */
@Service
public class RestInterceptor implements HandlerInterceptor {

    @Autowired
    private ApplicationTokenProperties applicationTokenProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String routeRestToken = request.getHeader(ROUTE_REST_TOKEN);
        String restToken = applicationTokenProperties.getRest();
        if (StringUtils.equals(FEIGN_COMMON_TOKEN, routeRestToken) || StringUtils.equals(routeRestToken, restToken)) {
            return true;
        }
        throw new CommonException("");
    }

}
