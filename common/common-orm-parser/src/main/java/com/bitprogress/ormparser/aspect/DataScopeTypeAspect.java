package com.bitprogress.ormparser.aspect;

import com.bitprogress.ormcontext.utils.DataScopeContextUtils;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.ormparser.annotation.MarkDataScopeType;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class DataScopeTypeAspect {

    @Around(value = "@annotation(markDataScopeType)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, MarkDataScopeType markDataScopeType) {
        DataScopeType dataScopeType = Objects.nonNull(markDataScopeType)
                ? markDataScopeType.dataScopeType()
                : DataScopeType.SELF;
        DataScopeType existTenantType = DataScopeContextUtils.getDataScopeType();
        DataScopeContextUtils.setDataScopeTypeOrThrow(dataScopeType, "未初始化数据权限信息，非法操作");
        try {
            return point.proceed();
        } finally {
            DataScopeContextUtils.setDataScopeType(existTenantType);
        }
    }

}
