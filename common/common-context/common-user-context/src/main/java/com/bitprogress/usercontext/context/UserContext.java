package com.bitprogress.usercontext.context;

import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.IException;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.util.CollectionUtils;

import java.util.Map;
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
    public static void removeUserInfo() {
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
     * 获取用户ID
     */
    public static Long getUserId() {
        return getUserIdOrDefault(null);
    }

    /**
     * 设置用户信息
     *
     * @param userId 用户ID
     */
    public static void setUserId(Long userId) {
        setField(userInfo -> userInfo.setUserId(userId));
    }

    /**
     * 清除用户ID
     */
    public static void removeUserId() {
        removeField(userInfo -> userInfo.setUserId(null));
    }

    /**
     * 获取用户ID
     */
    public static Long getUserIdOrDefault() {
        return getUserIdOrDefault(0L);
    }

    /**
     * 获取用户ID
     */
    public static Long getUserIdOrDefault(Long defaultUserId) {
        return getFieldOrDefault(UserInfo::getUserId, defaultUserId);
    }

    /**
     * 获取用户ID
     */
    public static Long getUserIdOrThrow() {
        return getUserIdOrThrow("未读取到用户ID");
    }

    /**
     * 获取用户ID
     */
    public static Long getUserIdOrThrow(String message) {
        return getFieldOrThrow(UserInfo::getUserId, () -> CommonException.error(message));
    }

    /**
     * 获取用户ID
     */
    public static Long getUserIdOrThrow(IException exception) {
        return getFieldOrThrow(UserInfo::getUserId, () -> CommonException.error(exception));
    }

    /**
     * 获取用户ID
     */
    public static Long getUserIdOrThrow(CommonException exception) {
        return getFieldOrThrow(UserInfo::getUserId, () -> exception);
    }

    /**
     * 获取角色ID
     */
    public static Long getRoleId() {
        return getRoleIdOrDefault(null);
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public static void setRoleId(Long roleId) {
        setField(userInfo -> userInfo.setRoleId(roleId));
    }

    /**
     * 清除角色ID
     */
    public static void removeRoleId() {
        removeField(userInfo -> userInfo.setRoleId(null));
    }

    /**
     * 获取角色ID
     */
    public static Long getRoleIdOrDefault() {
        return getRoleIdOrDefault(0L);
    }

    /**
     * 获取角色ID
     */
    public static Long getRoleIdOrDefault(Long defaultUserId) {
        return getFieldOrDefault(UserInfo::getRoleId, defaultUserId);
    }

    /**
     * 获取角色ID
     */
    public static Long getRoleIdOrThrow() {
        return getRoleIdOrThrow("未读取到角色ID");
    }

    /**
     * 获取角色ID
     */
    public static Long getRoleIdOrThrow(String message) {
        return getFieldOrThrow(UserInfo::getRoleId, () -> CommonException.error(message));
    }

    /**
     * 获取角色ID
     */
    public static Long getRoleIdOrThrow(IException exception) {
        return getFieldOrThrow(UserInfo::getRoleId, () -> CommonException.error(exception));
    }

    /**
     * 获取角色ID
     */
    public static Long getRoleIdOrThrow(CommonException exception) {
        return getFieldOrThrow(UserInfo::getRoleId, () -> exception);
    }

    /**
     * 获取附加信息
     */
    public static Map<String, String> getAttendMessage() {
        return getFieldOrDefault(UserInfo::getAttendMessage, CollectionUtils.emptyMap());
    }

    /**
     * 设置附加信息
     *
     * @param attendMessage 附加信息
     */
    public static void setAttendMessage(Map<String, String> attendMessage) {
        setField(userInfo -> userInfo.setAttendMessage(attendMessage));
    }

    /**
     * 清除附加信息
     */
    public static void removeAttendMessage() {
        removeField(userInfo -> userInfo.setAttendMessage(null));
    }

    /**
     * 追加附加信息
     *
     * @param key 需要获取的附加信息的keu
     */
    public static void addAttendMessage(String key, String value) {
        Map<String, String> attendMessage = getAttendMessage();
        attendMessage.put(key, value);
        setField(userInfo -> userInfo.setAttendMessage(attendMessage));
    }

    /**
     * 根据key获取附加信息
     *
     * @param key 需要获取的附加信息的keu
     * @return 附加信息
     */
    public static String getAttendMessageByKey(String key) {
        return CollectionUtils.getForMap(getAttendMessage(), key);
    }

    /**
     * 根据key清除附加信息
     *
     * @param key 需要清除的附加信息的key
     */
    public static void removeAttendMessageByKey(String key) {
        removeField(userInfo ->{
            Map<String, String> attendMessage = userInfo.getAttendMessage();
            if (CollectionUtils.isEmpty(attendMessage)) {
                return;
            }
            attendMessage.remove(key);
        });
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

}
