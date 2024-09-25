package com.bitprogress.usercontext.context;

import com.bitprogress.basemodel.IException;
import com.bitprogress.exception.CommonException;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.usercontext.enums.UserType;
import com.bitprogress.util.CollectionUtils;

import java.util.Map;
import java.util.Set;

public class UserContextUtils {

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
        UserContext.setField(userInfo -> userInfo.setUserId(userId));
    }

    /**
     * 清除用户ID
     */
    public static void removeUserId() {
        UserContext.removeField(userInfo -> userInfo.setUserId(null));
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
        return UserContext.getFieldOrDefault(UserInfo::getUserId, defaultUserId);
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
        return UserContext.getFieldOrThrow(UserInfo::getUserId, () -> CommonException.error(message));
    }

    /**
     * 获取用户ID
     */
    public static Long getUserIdOrThrow(IException exception) {
        return UserContext.getFieldOrThrow(UserInfo::getUserId, () -> CommonException.error(exception));
    }

    /**
     * 获取用户ID
     */
    public static Long getUserIdOrThrow(CommonException exception) {
        return UserContext.getFieldOrThrow(UserInfo::getUserId, () -> exception);
    }

    /**
     * 获取用户类型
     */
    public static UserType getUserType() {
        return UserContext.getFieldOrDefault(UserInfo::getUserType, null);
    }

    /**
     * 设置用户类型
     *
     * @param userType 用户类型
     */
    public static void setUserType(UserType userType) {
        UserContext.setField(userInfo -> userInfo.setUserType(userType));
    }

    /**
     * 清除用户类型
     */
    public static void removeUserType() {
        UserContext.removeField(userInfo -> userInfo.setUserType(null));
    }

    /**
     * 获取用户类型
     */
    public static UserType getUserTypeOrDefault() {
        return UserContext.getFieldOrDefault(UserInfo::getUserType, UserType.NONE);
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
        return UserContext.getFieldOrThrow(UserInfo::getUserType, () -> CommonException.error(message));
    }

    /**
     * 获取用户类型
     */
    public static UserType getUserTypeOrThrow(IException exception) {
        return UserContext.getFieldOrThrow(UserInfo::getUserType, () -> CommonException.error(exception));
    }

    /**
     * 获取用户类型
     */
    public static UserType getUserTypeOrThrow(CommonException exception) {
        return UserContext.getFieldOrThrow(UserInfo::getUserType, () -> exception);
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
        UserContext.setField(userInfo -> userInfo.setRoleIds(roleIds));
    }

    /**
     * 清除角色ID
     */
    public static void removeRoleIds() {
        UserContext.removeField(userInfo -> userInfo.setRoleIds(null));
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
        return UserContext.getFieldOrDefault(UserInfo::getRoleIds, defaultRoleIds);
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
        return UserContext.getFieldOrThrow(UserInfo::getRoleIds, () -> CommonException.error(message));
    }

    /**
     * 获取角色ID
     */
    public static Set<Long> getRoleIdsOrThrow(IException exception) {
        return UserContext.getFieldOrThrow(UserInfo::getRoleIds, () -> CommonException.error(exception));
    }

    /**
     * 获取角色ID
     */
    public static Set<Long> getRoleIdsOrThrow(CommonException exception) {
        return UserContext.getFieldOrThrow(UserInfo::getRoleIds, () -> exception);
    }

    /**
     * 获取租户ID
     */
    public static Long getTenantId() {
        return getTenantIdOrDefault(null);
    }

    /**
     * 设置租户ID
     *
     * @param tenantId 租户ID
     */
    public static void setTenantId(Long tenantId) {
        UserContext.setField(userInfo -> userInfo.setTenantId(tenantId));
    }

    /**
     * 清除租户ID
     */
    public static void removeTenantId() {
        UserContext.removeField(userInfo -> userInfo.setTenantId(null));
    }

    /**
     * 获取租户ID
     */
    public static Long getTenantIdOrDefault() {
        return getTenantIdOrDefault(null);
    }

    /**
     * 获取租户ID
     */
    public static Long getTenantIdOrDefault(Long defaultTenantId) {
        return UserContext.getFieldOrDefault(UserInfo::getTenantId, defaultTenantId);
    }

    /**
     * 获取租户ID
     */
    public static Long getTenantIdOrThrow() {
        return getTenantIdOrThrow("未读取到租户ID");
    }

    /**
     * 获取租户ID
     */
    public static Long getTenantIdOrThrow(String message) {
        return UserContext.getFieldOrThrow(UserInfo::getTenantId, () -> CommonException.error(message));
    }

    /**
     * 获取租户ID
     */
    public static Long getTenantIdOrThrow(IException exception) {
        return UserContext.getFieldOrThrow(UserInfo::getTenantId, () -> CommonException.error(exception));
    }

    /**
     * 获取租户ID
     */
    public static Long getTenantIdOrThrow(CommonException exception) {
        return UserContext.getFieldOrThrow(UserInfo::getTenantId, () -> exception);
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
        UserContext.setField(userInfo -> userInfo.setOperateAllTenant(operateAllTenant));
    }

    /**
     * 清除是否可操作所有租户
     */
    public static void removeOperateAllTenant() {
        UserContext.removeField(userInfo -> userInfo.setOperateAllTenant(null));
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
        return UserContext.getFieldOrDefault(UserInfo::getOperateAllTenant, operateAllTenant);
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
        UserContext.setField(userInfo -> userInfo.setOperateTenantIds(operateTenantIds));
    }

    /**
     * 清除可操作租户ID
     */
    public static void removeOperateTenantIds() {
        UserContext.removeField(userInfo -> userInfo.setOperateTenantIds(null));
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
        return UserContext.getFieldOrDefault(UserInfo::getOperateTenantIds, OperateTenantIds);
    }

    /**
     * 获取可操作租户ID
     */
    public static Set<Long> getOperateTenantIdsOrThrow() {
        return getOperateTenantIdsOrThrow("未读可操作租户ID");
    }

    /**
     * 获取可操作租户ID
     */
    public static Set<Long> getOperateTenantIdsOrThrow(String message) {
        return UserContext.getFieldOrThrow(UserInfo::getOperateTenantIds, () -> CommonException.error(message));
    }

    /**
     * 获取可操作租户ID
     */
    public static Set<Long> getOperateTenantIdsOrThrow(IException exception) {
        return UserContext.getFieldOrThrow(UserInfo::getOperateTenantIds, () -> CommonException.error(exception));
    }

    /**
     * 获取可操作租户ID
     */
    public static Set<Long> getOperateTenantIdsOrThrow(CommonException exception) {
        return UserContext.getFieldOrThrow(UserInfo::getOperateTenantIds, () -> exception);
    }

    /**
     * 获取附加信息
     */
    public static Map<String, String> getAttendMessage() {
        return UserContext.getFieldOrDefault(UserInfo::getAttendMessage, CollectionUtils.emptyMap());
    }

    /**
     * 设置附加信息
     *
     * @param attendMessage 附加信息
     */
    public static void setAttendMessage(Map<String, String> attendMessage) {
        UserContext.setField(userInfo -> userInfo.setAttendMessage(attendMessage));
    }

    /**
     * 清除附加信息
     */
    public static void removeAttendMessage() {
        UserContext.removeField(userInfo -> userInfo.setAttendMessage(null));
    }

    /**
     * 追加附加信息
     *
     * @param key 需要获取的附加信息的keu
     */
    public static void addAttendMessage(String key, String value) {
        Map<String, String> attendMessage = getAttendMessage();
        attendMessage.put(key, value);
        UserContext.setField(userInfo -> userInfo.setAttendMessage(attendMessage));
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
        UserContext.removeField(userInfo -> {
            Map<String, String> attendMessage = userInfo.getAttendMessage();
            if (CollectionUtils.isEmpty(attendMessage)) {
                return;
            }
            attendMessage.remove(key);
        });
    }

}
