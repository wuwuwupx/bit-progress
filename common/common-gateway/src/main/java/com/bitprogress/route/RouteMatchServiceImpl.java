package com.bitprogress.route;

import com.bitprogress.service.MatchService;
import com.bitprogress.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * @author wuwuwupx
 * 路由匹配服务
 */
@Service
public class RouteMatchServiceImpl implements MatchService {

    /**
     * 需要匹配的路径前缀
     */
    private static final String MATCH_URL_PRE = "/api/";

    private AntPathMatcher matcher = new AntPathMatcher();

    /**
     * 查询是否为不校验路由
     *
     * @param method 校验路径method
     * @param url    检验路径
     * @return boolean
     */
    @Override
    public boolean ignoreAuthentication(String method, String url) {
        //如果为非/api/开头，一律通过
        if (StringUtils.startWithIgnoreCase(url, MATCH_URL_PRE)) {
            // 获取路由白名单
            List<String> whiteRoutes = GatewayRouteMsg.getWhiteRoute();

            if (whiteRoutes.isEmpty()) {
                return false;
            }

            //判断与pattern是否匹配
            for (String ignoreUrlPattern : whiteRoutes) {
                if (pathMatch(ignoreUrlPattern, method, url)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }

    }

    /**
     * 路由规则匹配（ant格式）
     *
     * @param pattern 白名单路径
     * @param method  请求方法
     * @param url     请求路径
     */
    private boolean pathMatch(String pattern, String method, String url) {
        AntPathMatcher pathMatcher = this.matcher;

        //使用空格分隔，e.g. GET /api/user，如果没有METHOD，直接取URI部分
        String pUri, pMethod;

        String[] patternArray = pattern.split(" ");

        if (patternArray.length < 2) {
            pMethod = null;
            pUri = patternArray[0];
        } else {
            pMethod = patternArray[0].toUpperCase();
            pUri = patternArray[1];
        }

        //如果pMethod为空，uri匹配，则匹配成功；若不为空，需要完全匹配
        return (null != pMethod && (pMethod.equals(method) && pathMatcher.match(pUri, url)))
                || (null == pMethod && pathMatcher.match(pUri, url));
    }

}
