package com.bitprogress.ormparser.aspect;

import com.bitprogress.ormparser.annotation.Propagation;
import com.bitprogress.ormparser.annotation.SqlParserMode;
import com.bitprogress.ormparser.context.SqlParserContext;
import com.bitprogress.ormparser.entity.SqlParserMsg;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class SqlParserAspect {

    @Around("@annotation(sqlParserMode)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, SqlParserMode sqlParserMode) {
        // 已存在的 sql解析模式信息

        final Optional<SqlParserMsg> preSqlParserMsg = Optional.ofNullable(SqlParserContext.getSqlParserMsg());

        // 当前注解标注的 sql解析模式信息
        Propagation propagation = sqlParserMode.propagation();

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
                        SqlParserContext.setSqlParserMsg(SqlParserMsg.createBySqlParserMode(sqlParserMode));
                    }
                }
                // 开启一个新的解析模式
                case REQUIRES_NEW -> {
                    SqlParserContext.setSqlParserMsg(SqlParserMsg.createBySqlParserMode(sqlParserMode));
                }
                // 关闭 sql解析模式
                case NOT_SUPPORTED -> {
                    SqlParserContext.setSqlParserMsg(SqlParserMsg.createDisable());
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
            SqlParserContext.clearSqlParserMsg();
            /*
             * 重置 sql解析模式和 sql类型
             */
            preSqlParserMsg.ifPresent(SqlParserContext::setSqlParserMsg);
        }
    }

}
