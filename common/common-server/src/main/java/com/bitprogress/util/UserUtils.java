package com.bitprogress.util;

import com.bitprogress.basemodel.util.EnumUtils;
import com.bitprogress.request.constant.VerifyConstant;
import com.bitprogress.usercontext.context.UserContext;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.usercontext.enums.UserType;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * gateway对用户解析后将用户信息追加到header中
 * web端的用户只有 userId
 */
public class UserUtils {

    private static final Logger log = LoggerFactory.getLogger(UserUtils.class);

    /**
     * 获取用户信息
     *
     * @param request 请求request
     * @return 用户信息
     */
    public static UserInfo getUserInfo(HttpServletRequest request) {
        UserInfo userInfo = new UserInfo();

        String userId = request.getHeader(VerifyConstant.USER_ID);
        if (StringUtils.isNotEmpty(userId)) {
            try {
                userInfo.setUserId(Long.parseLong(userId));
            } catch (Exception e) {
                log.error("userId convert error", e);
            }
        }

        String userType = request.getHeader(VerifyConstant.USER_TYPE);
        if (StringUtils.isNotEmpty(userType)) {
            try {
                userInfo.setUserType(EnumUtils.getByValue(UserType.class, Integer.parseInt(userType)));
            } catch (Exception e) {
                log.error("userType convert error", e);
            }
        }

        String roleId = request.getHeader(VerifyConstant.ROLE_ID);
        if (StringUtils.isNotEmpty(roleId)) {
            try {
                Set<Long> roleIds = JsonUtils.deserializeSet(roleId, new TypeReference<Set<Long>>() {
                });
                userInfo.setRoleIds(roleIds);
            } catch (Exception e) {
                log.error("roleId convert error", e);
            }
        }

        String attendMessageStr = request.getHeader(VerifyConstant.ATTEND_MESSAGE);
        if (StringUtils.isNotEmpty(attendMessageStr)) {
            try {
                Map<String, String> attendMessage = JsonUtils.deserializeMap(attendMessageStr,
                        new TypeReference<Map<String, String>>() {
                        });
                userInfo.setAttendMessage(attendMessage);
            } catch (Exception e) {
                log.error("attendMessage convert error", e);
            }
        }
        return userInfo;
    }

    /**
     * 获取从gateway转发时追加的用户ID
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        return UserContext.getUserId();
    }

    /**
     * 获取从gateway转发时追加的用户roles
     *
     * @return 用户role
     */
    public static Set<Long> getRoleIds() {
        return UserContext.getRoleId();
    }

}
