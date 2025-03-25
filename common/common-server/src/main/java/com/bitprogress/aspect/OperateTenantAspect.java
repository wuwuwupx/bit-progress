package com.bitprogress.aspect;

import com.bitprogress.exception.CommonException;
import com.bitprogress.ormcontext.utils.TenantContextUtils;
import com.bitprogress.servermodel.annotation.OperateTenantApi;
import com.bitprogress.servermodel.constant.TenantConstant;
import com.bitprogress.usercontext.utils.UserContextUtils;
import com.bitprogress.util.Assert;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Set;

/**
 * 设置租户的类型
 */
@Component
@Aspect
public class OperateTenantAspect {

    private static final Logger log = LoggerFactory.getLogger(OperateTenantAspect.class);

    @SneakyThrows
    @Before(value = "@annotation(operateTenantApi) && execution(* *(..)) && within(@org.springframework.stereotype.Controller *)")
    public void before(JoinPoint joinPoint, OperateTenantApi operateTenantApi) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(attributes, "获取请求信息失败");
        HttpServletRequest request = attributes.getRequest();
        String operateTenantIdStr = request.getHeader(TenantConstant.OPERATE_TENANT_KEY);
        boolean required = operateTenantApi.required();
        if (StringUtils.isNotEmpty(operateTenantIdStr)) {
            long operateTenantId;
            try {
                operateTenantId = Long.parseLong(operateTenantIdStr);
            } catch (Exception e) {
                log.error("operateTenantId convert error", e);
                throw CommonException.error("获取操作租户ID失败");
            }
            if (!UserContextUtils.getOperateAllTenant()) {
                Set<Long> operateTenantIds = UserContextUtils.getOperateTenantIds();
                Assert.isTrue(CollectionUtils.contains(operateTenantIds, operateTenantId), "无权限操作此租户");
            }
            /*
             * 为避免越权，需要在初始化租户信息后才能设置
             * 不维护上下文
             */
            TenantContextUtils.setOperateTenantIdOrThrow(operateTenantId, "未初始化租户信息，非法操作");
        } else {
            Assert.isTrue(!required, "未获取到操作租户ID，非法操作");
        }
    }

}
