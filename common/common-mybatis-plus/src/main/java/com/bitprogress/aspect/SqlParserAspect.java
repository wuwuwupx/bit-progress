package com.bitprogress.aspect;

import com.bitprogress.annotation.*;
import com.bitprogress.context.SqlParserContext;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class SqlParserAspect {

    @Around("@annotation(sqlParserMode)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, SqlParserMode sqlParserMode) {
        // 已存在的 sql解析模式信息
        final Boolean originalEnable = SqlParserContext.getEnable();
        final ParserType originalParserType = SqlParserContext.getParserType();
        final SqlType[] originalSqlType = SqlParserContext.getSqlType();
        final TenantType originalTenantType = SqlParserContext.getTenantType();

        boolean hasSqlParser = Objects.nonNull(originalEnable);

        // 当前注解标注的 sql解析模式信息
        Propagation propagation = sqlParserMode.propagation();
        SqlType[] sqlTypes = sqlParserMode.sqlTypes();
        ParserType parserType = sqlParserMode.parserType();
        TenantType tenantType = sqlParserMode.tenantType();

        /*
         * 设置当前的 sql解析模式和 sql类型
         */
        try {
            // 根据传播方式设置 sql解析模式
            switch (propagation) {
                // 默认传播方式
                case REQUIRED: {
                    // 没有解析模式则新建
                    if (!hasSqlParser) {
                        SqlParserContext.setEnable(true);
                        SqlParserContext.setParserType(parserType);
                        SqlParserContext.setSqlType(sqlTypes);
                        SqlParserContext.setTenantType(tenantType);
                    }
                    break;
                }
                // 开启一个新的解析模式
                case REQUIRES_NEW: {
                    SqlParserContext.setEnable(true);
                    SqlParserContext.setParserType(parserType);
                    SqlParserContext.setSqlType(sqlTypes);
                    SqlParserContext.setTenantType(tenantType);
                    break;
                }
                // 关闭 sql解析模式
                case NOT_SUPPORTED: {
                    SqlParserContext.setEnable(false);
                    break;
                }
            }
            /*
             * 执行当前方法
             */
            return point.proceed();
        } finally {
            /*
             * 清除当前 sql解析模式和 sql类型
             */
            SqlParserContext.remove();
            if (hasSqlParser) {
                /*
                 * 重置 sql解析模式和 sql类型
                 */
                SqlParserContext.setEnable(originalEnable);
                SqlParserContext.setParserType(originalParserType);
                SqlParserContext.setSqlType(originalSqlType);
                SqlParserContext.setTenantType(originalTenantType);
            }
        }
    }

}
