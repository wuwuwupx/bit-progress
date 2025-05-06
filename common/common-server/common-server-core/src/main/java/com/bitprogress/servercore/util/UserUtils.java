package com.bitprogress.servercore.util;

import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormmodel.info.user.SingleTypeDataScopeInfo;
import com.bitprogress.ormmodel.info.user.UserTenantInfo;
import com.bitprogress.request.constant.VerifyConstant;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

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

    /**
     * 从用户信息中获取租户ID
     * 不依赖前端传递
     *
     * @param userInfo 用户信息
     * @return 租户信息
     */
    public static UserTenantInfo getTenantInfo(UserInfo userInfo) {
        UserTenantInfo userTenantInfo = new UserTenantInfo();
        userTenantInfo.setTenantId(userInfo.getTenantId());
        userTenantInfo.setOperateTenantIds(userInfo.getOperateTenantIds());
        // 默认使用当前租户
        userTenantInfo.setTenantType(TenantType.CURRENT);
        userTenantInfo.setCanOperateAllTenant(userInfo.getCanOperateAllTenant());
        return userTenantInfo;
    }

    /**
     * 从用户信息中获取数据范围信息
     * 不依赖前端传递
     *
     * @param userInfo 用户信息
     * @return 数据范围信息
     */
    public static SingleTypeDataScopeInfo getDataScopeInfo(UserInfo userInfo) {
        SingleTypeDataScopeInfo dataScopeInfo = new SingleTypeDataScopeInfo();
        dataScopeInfo.setSelfData(userInfo.getUserId());
        dataScopeInfo.setBaseDataScope(userInfo.getBaseDataScope());
        dataScopeInfo.setDataScope(userInfo.getDataScope());
        dataScopeInfo.setManagedDataScopes(userInfo.getDataScopes());
        dataScopeInfo.setBelongDataScopes(userInfo.getBelongDataScopes());
        dataScopeInfo.setOwnedData(userInfo.getUserId());
        if (Objects.nonNull(userInfo.getDataScopeType())) {
            dataScopeInfo.setDataScopeType(DataScopeType.getByValue(userInfo.getDataScopeType().getValue()));
        } else {
            // 为避免越权操作，默认为自身范围
            dataScopeInfo.setDataScopeType(DataScopeType.SELF);
        }
        return dataScopeInfo;
    }

}
