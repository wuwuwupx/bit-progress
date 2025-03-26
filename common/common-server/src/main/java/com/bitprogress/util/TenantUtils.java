package com.bitprogress.util;

import com.bitprogress.ormcontext.entity.TenantInfo;
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
    public static TenantInfo getTenantInfo(UserInfo userInfo) {
        TenantInfo tenantInfo = new TenantInfo();
        tenantInfo.setTenantId(userInfo.getTenantId());
        tenantInfo.setOperateTenantIds(userInfo.getOperateTenantIds());
        // 默认使用当前租户
        tenantInfo.setTenantType(TenantType.CURRENT);
        return tenantInfo;
    }

}
