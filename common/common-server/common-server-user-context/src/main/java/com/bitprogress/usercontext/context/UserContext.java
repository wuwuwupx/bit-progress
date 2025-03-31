package com.bitprogress.usercontext.context;

import com.bitprogress.basemodel.IException;
import com.bitprogress.exception.CommonException;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.util.JsonUtils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 用户信息上下文
 */
public class UserContext {

    /**
     * 用户信息
     */
    private static final ThreadLocal<UserInfo> USER_INFO = new ThreadLocal<>();

    /**
     * 获取用户信息
     */
    public static UserInfo getUserInfo() {
        return USER_INFO.get();
    }

    /**
     * 设置用户信息
     */
    public static void setUserInfo(UserInfo userInfo) {
        USER_INFO.set(userInfo);
    }

    /**
     * 清除用户信息
     */
    public static void clearUserInfo() {
        USER_INFO.remove();
    }

    /**
     * 获取用户信息
     * 为空返回新的对象
     */
    public static UserInfo getUserInfoOrNew() {
        return Optional
                .ofNullable(USER_INFO.get())
                .orElse(new UserInfo());
    }

    /**
     * 获取用户信息
     * 为空抛出异常
     */
    public static UserInfo getUserInfoOrThrow() {
        return getUserInfoOrThrow("未读取到用户信息");
    }

    /**
     * 获取用户信息
     * 为空抛出异常
     */
    public static UserInfo getUserInfoOrThrow(String message) {
        return getUserInfoOrThrow(() -> CommonException.error(message));
    }

    /**
     * 获取用户信息
     * 为空抛出异常
     */
    public static UserInfo getUserInfoOrThrow(IException exception) {
        return getUserInfoOrThrow(() -> CommonException.error(exception));
    }

    /**
     * 获取用户信息
     * 为空抛出异常
     */
    public static UserInfo getUserInfoOrThrow(CommonException exception) {
        return getUserInfoOrThrow(() -> exception);
    }

    /**
     * 获取用户信息
     * 为空抛出异常
     */
    public static UserInfo getUserInfoOrThrow(Supplier<? extends CommonException> supplier) {
        return Optional
                .ofNullable(USER_INFO.get())
                .orElseThrow(supplier);
    }

    /**
     * 获取用户信息
     */
    public static <T> T getFieldOrDefault(Function<UserInfo, T> fieldFunction, T defaultValue) {
        return Optional
                .ofNullable(USER_INFO.get())
                .map(fieldFunction)
                .orElse(defaultValue);
    }

    /**
     * 获取用户信息
     */
    public static <T> T getFieldOrThrow(Function<UserInfo, T> fieldFunction,
                                        Supplier<? extends CommonException> supplier) {
        UserInfo userInfo = getUserInfoOrThrow();
        return Optional
                .ofNullable(userInfo)
                .map(fieldFunction)
                .orElseThrow(supplier);
    }

    /**
     * 设置用户信息
     */
    public static void setField(Consumer<UserInfo> consumer) {
        UserInfo userInfo = getUserInfoOrNew();
        consumer.accept(userInfo);
        USER_INFO.set(userInfo);
    }

    /**
     * 清除用户信息
     */
    public static void removeField(Consumer<UserInfo> consumer) {
        Optional.ofNullable(USER_INFO.get())
                .ifPresent(consumer);
    }

    /**
     * 序列化用户信息
     */
    public static String getUserInfoJson() {
        return JsonUtils.serializeObject(getUserInfo(), "");
    }

    /**
     * 反序列化用户信息
     */
    public static void setUserInfoJson(String userInfoJson) {
        USER_INFO.set(JsonUtils.deserializeObject(userInfoJson, UserInfo.class));
    }

}
