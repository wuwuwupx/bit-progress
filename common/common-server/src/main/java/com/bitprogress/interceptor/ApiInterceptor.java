package com.bitprogress.interceptor;

import com.bitprogress.constant.VerifyConstant;
import com.bitprogress.property.ApplicationTokenProperties;
import com.bitprogress.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author wuwuwupx
 * create on 2021/6/21 1:41
 * API拦截器，检验对外提供的接口token
 */
@Service
public class ApiInterceptor implements HandlerInterceptor {

    @Autowired
    private ApplicationTokenProperties applicationTokenProperties;

    /**
     * 对请求进行处理，检验是否由网关转发，并将网关追加到请求头的信息进行缓存
     *
     * @param request
     * @param response
     * @param handler
     * @return 处理是否通过
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String routeApiToken = request.getHeader(VerifyConstant.ROUTE_API_TOKEN);
        String apiToken = applicationTokenProperties.getApi();
        if (StringUtils.equals(routeApiToken, apiToken)) {
            // 保存用户信息

            return true;
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}
