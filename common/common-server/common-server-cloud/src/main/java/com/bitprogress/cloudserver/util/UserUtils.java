package com.bitprogress.cloudserver.util;

import com.bitprogress.request.constant.VerifyConstant;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * gateway对用户解析后将用户信息追加到header中
 * web端的用户只有 userId
 */
public class UserUtils {

    private static final Logger log = LoggerFactory.getLogger(UserUtils.class);

    /**
     * 从请求头解析用户信息
     *
     * @param request 请求request
     * @return 用户信息
     */
    public static UserInfo analysisUserInfo(HttpServletRequest request) {
        String userInfoStr = request.getHeader(VerifyConstant.USER_INFO);
        if (StringUtils.isNotEmpty(userInfoStr)) {
            try {
                return JsonUtils.deserializeObject(userInfoStr, UserInfo.class);
            } catch (Exception e) {
                log.error("userInfo convert error", e);
            }
        }
        return new UserInfo();
    }

}
