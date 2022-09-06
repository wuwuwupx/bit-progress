package com.bitprogress.util;

import com.bitprogress.base.UserInfo;
import com.bitprogress.base.UserOperator;
import com.bitprogress.constant.VerifyConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wpx
 * gateway对用户解析后将用户信息追加到header中
 * web端的用户只有 userId
 */
public class UserUtils {

    /**
     * 获取用户信息
     *
     * @param request 请求request
     * @return 用户信息
     */
    public static UserInfo getUserInfo(HttpServletRequest request) {
        UserInfo userInfo = new UserInfo();
        String userId = request.getHeader(VerifyConstant.USER_ID);
        userInfo.setUserId(Long.parseLong(userId));
        String roleKey = request.getHeader(VerifyConstant.ROLE_KEY);
        Map<String, String> params = new HashMap<>();
        params.put(VerifyConstant.ROLE_KEY, roleKey);
        userInfo.setParams(params);
        return userInfo;
    }

    /**
     * 获取从gateway转发时追加的用户ID
     *
     * @return  用户ID
     */
    public static Long getUserId() {
        return UserOperator.getUserInfo().getUserId();
    }

    /**
     * 获取从gateway转发时追加的用户roles
     *
     * @return  用户role
     */
    public static String getRoleKey() {
        Map<String, String> params = UserOperator.getUserInfo().getParams();
        return params.get(VerifyConstant.ROLE_KEY);
    }

}
