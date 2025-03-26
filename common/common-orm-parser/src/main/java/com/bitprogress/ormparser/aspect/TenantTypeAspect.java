package com.bitprogress.ormparser.aspect;

import com.bitprogress.ormparser.annotation.AllTenant;
import com.bitprogress.ormparser.annotation.CurrentTenant;
import com.bitprogress.ormparser.annotation.MarkTenantType;
import com.bitprogress.ormparser.annotation.OperateTenant;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormcontext.utils.TenantContextUtils;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class TenantTypeAspect {

    @Around(value = "@annotation(currentTenant) || @annotation(operateTenant) || @annotation(allTenant) || @annotation(markTenantType)", argNames = "point,currentTenant,operateTenant,allTenant,markTenantType")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point,
                         CurrentTenant currentTenant,
                         OperateTenant operateTenant,
                         AllTenant allTenant,
                         MarkTenantType markTenantType) {
        TenantType tenantType = Objects.nonNull(currentTenant)
                ? TenantType.CURRENT
                : Objects.nonNull(operateTenant) ? TenantType.OPERATE
                : Objects.nonNull(allTenant) ? TenantType.ALL
                : Objects.nonNull(markTenantType) ? markTenantType.tenantType() : TenantType.CURRENT;
        TenantType existTenantType = TenantContextUtils.getTenantType();
        try {
            TenantContextUtils.setTenantTypeOrThrow(tenantType, "未初始化租户信息，非法操作");
            return point.proceed();
        } finally {
            TenantContextUtils.setTenantType(existTenantType);
        }
    }

}
