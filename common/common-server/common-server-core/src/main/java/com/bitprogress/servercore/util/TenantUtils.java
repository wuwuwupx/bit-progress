package com.bitprogress.servercore.util;

import com.bitprogress.ormmodel.info.parser.UserTenantInfo;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.usercontext.entity.UserInfo;

public class TenantUtils {

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

}
