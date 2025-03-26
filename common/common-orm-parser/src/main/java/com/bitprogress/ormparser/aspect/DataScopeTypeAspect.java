package com.bitprogress.ormparser.aspect;

import com.bitprogress.ormcontext.utils.DataScopeContextUtils;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.ormparser.annotation.AllDataScope;
import com.bitprogress.ormparser.annotation.CurrentDataScope;
import com.bitprogress.ormparser.annotation.LimitedDataScope;
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

    @Around(value = "@annotation(currentDataScope) || @annotation(limitedDataScope) || @annotation(allDataScope) || @annotation(markDataScopeType)", argNames = "point,currentDataScope,limitedDataScope,allDataScope,markDataScopeType")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point,
                         CurrentDataScope currentDataScope,
                         LimitedDataScope limitedDataScope,
                         AllDataScope allDataScope,
                         MarkDataScopeType markDataScopeType) {
        DataScopeType dataScopeType = Objects.nonNull(currentDataScope)
                ? DataScopeType.SELF
                : Objects.nonNull(limitedDataScope) ? DataScopeType.LIMITED
                : Objects.nonNull(allDataScope) ? DataScopeType.ALL
                : Objects.nonNull(markDataScopeType) ? markDataScopeType.dataScopeType() : DataScopeType.SELF;
        DataScopeType existTenantType = DataScopeContextUtils.getDataScopeType();
        try {
            DataScopeContextUtils.setDataScopeTypeOrThrow(dataScopeType, "未初始化数据权限信息，非法操作");
            return point.proceed();
        } finally {
            DataScopeContextUtils.setDataScopeType(existTenantType);
        }
    }

}
