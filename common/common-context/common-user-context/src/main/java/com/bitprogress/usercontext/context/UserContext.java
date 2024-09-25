package com.bitprogress.usercontext.context;

import com.bitprogress.basemodel.IException;
import com.bitprogress.exception.CommonException;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.usercontext.enums.UserType;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.JsonUtils;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
     * 设置用户信息
     *
     * @param userId        用户ID
     * @param userType      用户类型
     * @param roleIds       角色ID
     * @param attendMessage 附加信息
     */
    public static void setUserInfo(Long userId, UserType userType, Set<Long> roleIds, Map<String, String> attendMessage) {
        setField(userInfo -> {
            userInfo.setUserId(userId);
            userInfo.setUserType(userType);
            userInfo.setRoleIds(roleIds);
            userInfo.setAttendMessage(attendMessage);
        });
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
     * 获取用户类型
     */
    public static UserType getUserType() {
        return getFieldOrDefault(UserInfo::getUserType, null);
    }

    /**
     * 设置用户类型
     *
     * @param userType 用户类型
     */
    public static void setUserType(UserType userType) {
        setField(userInfo -> userInfo.setUserType(userType));
    }

    /**
     * 清除用户类型
     */
    public static void removeUserType() {
        removeField(userInfo -> userInfo.setUserType(null));
    }

    /**
     * 获取用户类型
     */
    public static UserType getUserTypeOrDefault() {
        return getFieldOrDefault(UserInfo::getUserType, UserType.NONE);
    }

    /**
     * 获取用户类型
     */
    public static UserType getUserTypeOrThrow() {
        return getUserTypeOrThrow("未读取到用户类型");
    }

    /**
     * 获取用户类型
     */
    public static UserType getUserTypeOrThrow(String message) {
        return getFieldOrThrow(UserInfo::getUserType, () -> CommonException.error(message));
    }

    /**
     * 获取用户类型
     */
    public static UserType getUserTypeOrThrow(IException exception) {
        return getFieldOrThrow(UserInfo::getUserType, () -> CommonException.error(exception));
    }

    /**
     * 获取用户类型
     */
    public static UserType getUserTypeOrThrow(CommonException exception) {
        return getFieldOrThrow(UserInfo::getUserType, () -> exception);
    }

    /**
     * 获取角色ID
     */
    public static Set<Long> getRoleId() {
        return getRoleIdsOrDefault(null);
    }

    /**
     * 设置角色ID
     *
     * @param roleIds 角色ID
     */
    public static void setRoleIds(Set<Long> roleIds) {
        setField(userInfo -> userInfo.setRoleIds(roleIds));
    }

    /**
     * 清除角色ID
     */
    public static void removeRoleIds() {
        removeField(userInfo -> userInfo.setRoleIds(null));
    }

    /**
     * 获取角色ID
     */
    public static Set<Long> getRoleIdsOrDefault() {
        return getRoleIdsOrDefault(CollectionUtils.emptySet());
    }

    /**
     * 获取角色ID
     */
    public static Set<Long> getRoleIdsOrDefault(Set<Long> defaultRoleIds) {
        return getFieldOrDefault(UserInfo::getRoleIds, defaultRoleIds);
    }

    /**
     * 获取角色ID
     */
    public static Set<Long> getRoleIdsOrThrow() {
        return getRoleIdsOrThrow("未读取到角色ID");
    }

    /**
     * 获取角色ID
     */
    public static Set<Long> getRoleIdsOrThrow(String message) {
        return getFieldOrThrow(UserInfo::getRoleIds, () -> CommonException.error(message));
    }

    /**
     * 获取角色ID
     */
    public static Set<Long> getRoleIdsOrThrow(IException exception) {
        return getFieldOrThrow(UserInfo::getRoleIds, () -> CommonException.error(exception));
    }

    /**
     * 获取角色ID
     */
    public static Set<Long> getRoleIdsOrThrow(CommonException exception) {
        return getFieldOrThrow(UserInfo::getRoleIds, () -> exception);
    }

    /**
     * 是否可操作所有租户
     */
    public static Boolean getOperateAllTenant() {
        return getOperateAllTenantOrDefault();
    }

    /**
     * 设置是否可操作所有租户
     *
     * @param operateAllTenant 是否可操作所有租户
     */
    public static void setOperateAllTenant(Boolean operateAllTenant) {
        setField(userInfo -> userInfo.setOperateAllTenant(operateAllTenant));
    }

    /**
     * 清除是否可操作所有租户
     */
    public static void removeOperateAllTenant() {
        removeField(userInfo -> userInfo.setOperateAllTenant(null));
    }

    /**
     * 是否可操作所有租户
     */
    public static Boolean getOperateAllTenantOrDefault() {
        return getOperateAllTenantOrDefault(false);
    }

    /**
     * 获取是否可操作所有租户
     */
    public static Boolean getOperateAllTenantOrDefault(Boolean operateAllTenant) {
        return getFieldOrDefault(UserInfo::getOperateAllTenant, operateAllTenant);
    }

    /**
     * 获取是否可操作所有租户
     */
    public static Boolean getOperateAllTenantOrThrow() {
        return getOperateAllTenantOrThrow("未读取到角色ID");
    }

    /**
     * 获取是否可操作所有租户
     */
    public static Boolean getOperateAllTenantOrThrow(String message) {
        return getFieldOrThrow(UserInfo::getOperateAllTenant, () -> CommonException.error(message));
    }

    /**
     * 获取是否可操作所有租户
     */
    public static Boolean getOperateAllTenantOrThrow(IException exception) {
        return getFieldOrThrow(UserInfo::getOperateAllTenant, () -> CommonException.error(exception));
    }

    /**
     * 获取是否可操作所有租户
     */
    public static Boolean getOperateAllTenantOrThrow(CommonException exception) {
        return getFieldOrThrow(UserInfo::getOperateAllTenant, () -> exception);
    }

    /**
     * 获取可操作租户ID
     */
    public static Set<Long> getOperateTenantIds() {
        return getOperateTenantIdsOrDefault();
    }

    /**
     * 设置可操作租户ID
     *
     * @param operateTenantIds 是否可操作所有租户
     */
    public static void setOperateTenantIds(Set<Long> operateTenantIds) {
        setField(userInfo -> userInfo.setOperateTenantIds(operateTenantIds));
    }

    /**
     * 清除可操作租户ID
     */
    public static void removeOperateTenantIds() {
        removeField(userInfo -> userInfo.setOperateTenantIds(null));
    }

    /**
     * 是否可操作租户ID
     */
    public static Set<Long> getOperateTenantIdsOrDefault() {
        return getOperateTenantIdsOrDefault(CollectionUtils.emptySet());
    }

    /**
     * 获取可操作租户ID
     */
    public static Set<Long> getOperateTenantIdsOrDefault(Set<Long> OperateTenantIds) {
        return getFieldOrDefault(UserInfo::getOperateTenantIds, OperateTenantIds);
    }

    /**
     * 获取可操作租户ID
     */
    public static Set<Long> getOperateTenantIdsOrThrow() {
        return getOperateTenantIdsOrThrow("未读取到角色ID");
    }

    /**
     * 获取可操作租户ID
     */
    public static Set<Long> getOperateTenantIdsOrThrow(String message) {
        return getFieldOrThrow(UserInfo::getOperateTenantIds, () -> CommonException.error(message));
    }

    /**
     * 获取可操作租户ID
     */
    public static Set<Long> getOperateTenantIdsOrThrow(IException exception) {
        return getFieldOrThrow(UserInfo::getOperateTenantIds, () -> CommonException.error(exception));
    }

    /**
     * 获取可操作租户ID
     */
    public static Set<Long> getOperateTenantIdsOrThrow(CommonException exception) {
        return getFieldOrThrow(UserInfo::getOperateTenantIds, () -> exception);
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
        removeField(userInfo -> {
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

    public static String getUserInfoJson() {
        return JsonUtils.serializeObject(getUserInfo());
    }

    public static void setUserInfoJson(String userInfoJson) {
        USER_INFO.set(JsonUtils.deserializeObject(userInfoJson, UserInfo.class));
    }

}
