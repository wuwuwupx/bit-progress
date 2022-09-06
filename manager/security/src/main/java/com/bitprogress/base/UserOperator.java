package com.bitprogress.base;

/**
 * @author wuwuwupx
 */
public class UserOperator {

    private UserOperator() {
    }

    /**
     * 用户信息
     */
    private static final ThreadLocal<UserInfo> USER_INFO = new ThreadLocal<>();

    /**
     * 设置用户信息
     *
     * @param userInfo 用户信息
     */
    public static void setUserInfo(UserInfo userInfo) {
        USER_INFO.set(userInfo);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public static UserInfo getUserInfo() {
        return USER_INFO.get();
    }

    /**
     * 清除用户信息
     */
    public static void clearUserInfo() {
        USER_INFO.remove();
    }

}
