package com.bitprogress.ormparser.aspect;

import com.bitprogress.ormcontext.service.TenantContextService;
import com.bitprogress.ormmodel.annotation.TenantParserMode;
import com.bitprogress.ormmodel.enums.Propagation;
import com.bitprogress.ormmodel.info.parser.TenantParserInfo;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class TenantParserAspect {

    @Autowired
    private TenantContextService tenantContextService;

    @Around("@annotation(tenantParserMode)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, TenantParserMode tenantParserMode) {
        // 已存在的 sql解析模式信息

        final Optional<TenantParserInfo> preSqlParserMsg = Optional.ofNullable(tenantContextService.getParserInfo());

        // 当前注解标注的 sql解析模式信息
        Propagation propagation = tenantParserMode.propagation();

        /*
         * 设置当前的 sql解析模式和 sql类型
         */
        try {
            // 根据传播方式设置 sql解析模式
            switch (propagation) {
                // 默认传播方式
                case REQUIRED -> {
                    // 没有解析模式则新建
                    if (preSqlParserMsg.isEmpty()) {
                        tenantContextService.setParserInfo(TenantParserInfo.createBySqlParserMode(tenantParserMode));
                    }
                }
                // 开启一个新的解析模式
                case REQUIRES_NEW ->
                        tenantContextService.setParserInfo(TenantParserInfo.createBySqlParserMode(tenantParserMode));
                // 关闭 sql解析模式
                case NOT_SUPPORTED -> tenantContextService.setParserInfo(TenantParserInfo.createDisable());
            }
            /*
             * 执行当前方法
             */
            return point.proceed();
        } finally {
            /*
             * 清除当前 sql解析模式和 sql类型
             */
            tenantContextService.clearParserInfo();
            /*
             * 重置 sql解析模式和 sql类型
             */
            preSqlParserMsg.ifPresent(tenantContextService::setParserInfo);
        }
    }

}
