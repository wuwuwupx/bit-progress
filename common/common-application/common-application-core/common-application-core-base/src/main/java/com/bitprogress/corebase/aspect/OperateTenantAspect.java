package com.bitprogress.corebase.aspect;

import com.bitprogress.corebase.service.RequestInformationService;
import com.bitprogress.exception.util.Assert;
import com.bitprogress.modelbase.annotation.OperateTenantApi;
import com.bitprogress.ormcontext.service.TenantContextService;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormmodel.info.user.UserTenantInfo;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.usercontext.service.UserInfoContextService;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.StringUtils;
import lombok.SneakyThrows;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

/**
 * 设置租户的类型
 */
@Component
@Aspect
public class OperateTenantAspect {

    private static final Logger log = LoggerFactory.getLogger(OperateTenantAspect.class);

    @Autowired
    private TenantContextService tenantContextService;
    @Autowired
    private RequestInformationService requestInformationService;

    @SneakyThrows
    @Before(value = "@annotation(operateTenantApi) && execution(* *(..)) && within(@org.springframework.stereotype.Controller *)")
    public void before(OperateTenantApi operateTenantApi) {
        String operateTenantId = requestInformationService.getTenantId();
        boolean required = operateTenantApi.required();
        Assert.isTrue(!required || StringUtils.isNotEmpty(operateTenantId), "未获取到操作租户ID，非法操作");
        UserInfo userInfo = UserInfoContextService.getUserInfo();
        Assert.notNull(userInfo, "获取用户信息失败");
        if (Objects.isNull(userInfo.getCanOperateAllTenant()) || !userInfo.getCanOperateAllTenant()) {
            Set<String> operateTenantIds = userInfo.getOperateTenantIds();
            Assert.isTrue(CollectionUtils.contains(operateTenantIds, operateTenantId), "无权限操作此租户");
        }
        /*
         * 为避免越权，需要在初始化租户信息后才能设置
         * 不维护上下文
         * 同时设置租户操作类型
         */
        UserTenantInfo userTenantInfo = tenantContextService.getUserInfo();
        Assert.notNull(userTenantInfo, "未初始化租户信息，非法操作");
        userTenantInfo.setOperateTenantId(operateTenantId);
        userTenantInfo.setTenantType(TenantType.OPERATE);
        tenantContextService.setUserInfo(userTenantInfo);
    }

}
