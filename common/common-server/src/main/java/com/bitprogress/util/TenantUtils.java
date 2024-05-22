package com.bitprogress.util;

import com.bitprogress.ormcontext.entity.TenantInfo;
import com.bitprogress.request.constant.VerifyConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantUtils {

    private static final Logger log = LoggerFactory.getLogger(TenantUtils.class);

    public static TenantInfo getTenantInfo(HttpServletRequest request) {
        TenantInfo tenantInfo = new TenantInfo();
        String tenantIdStr = request.getHeader(VerifyConstant.TENANT_ID);
        if (StringUtils.isNotEmpty(tenantIdStr)) {
            try {
                tenantInfo.setTenantId(Long.parseLong(tenantIdStr));
            } catch (NumberFormatException e) {
                log.error("tenantId convert error", e);
            }
        }
        String operateTenantIdStr = request.getHeader(VerifyConstant.OPERATE_TENANT_ID);
        if (StringUtils.isNotEmpty(operateTenantIdStr)) {
            try {
                tenantInfo.setOperateTenantId(Long.parseLong(operateTenantIdStr));
            } catch (NumberFormatException e) {
                log.error("operateTenantId convert error", e);
            }
        }
        return tenantInfo;
    }

}
