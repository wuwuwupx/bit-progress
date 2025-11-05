package com.bitprogress.lock.aspect;

import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.util.Assert;
import com.bitprogress.lock.annotation.LockParam;
import com.bitprogress.lock.annotation.LockParamType;
import com.bitprogress.lock.annotation.ResubmitLock;
import com.bitprogress.lock.service.ContextValueService;
import com.bitprogress.lock.service.ResubmitLockService;
import com.bitprogress.util.ArrayUtils;
import com.bitprogress.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@AllArgsConstructor
public class ResubmitLockAspect {

    private final ContextValueService contextValueService;

    private final ResubmitLockService resubmitLockService;

    @SneakyThrows
    @Around("@annotation(resubmitLock)")
    public Object around(ProceedingJoinPoint joinPoint, ResubmitLock resubmitLock) {
        LockParam[] lockParams = resubmitLock.lockParams();
        if (ArrayUtils.isEmpty(lockParams)) {
            return joinPoint.proceed();
        }
        String keyPrefix = resubmitLock.keyPrefix();
        long expire = resubmitLock.expire();
        TimeUnit timeUnit = resubmitLock.timeUnit();
        boolean release = resubmitLock.release();
        Object[] args = joinPoint.getArgs();
        String lockKey = getLockKey(keyPrefix, lockParams, args);
        boolean lock = resubmitLockService.lock(lockKey, expire, timeUnit);
        Assert.isTrue(lock, "点击太快啦！请稍后重试");
        try {
            return joinPoint.proceed();
        } finally {
            if (release) {
                resubmitLockService.unlock(lockKey);
            }
        }
    }

    /**
     * 获取锁的key
     *
     * @param keyPrefix  锁前缀
     * @param lockParams 锁参数
     * @param args       方法参数
     * @return 锁的key
     */
    private String getLockKey(String keyPrefix, LockParam[] lockParams, Object[] args) {
        StringBuilder keyBuilder = new StringBuilder(keyPrefix);
        keyBuilder.append(":");
        for (LockParam lockParam : lockParams) {
            String expression = lockParam.expression();
            LockParamType lockParamType = lockParam.paramType();
            String value;
            switch (lockParamType) {
                case CONTEXT -> value = contextValueService.getContextValue(expression);
                case SPRING_EL -> value = getSpringElValue(expression, args);
                default -> value = "";
            }
            keyBuilder.append(value);
        }
        return keyBuilder.toString();
    }

    /**
     * 获取spring el表达式的值
     *
     * @param expression 表达式
     *                   * @param args       参数
     * @return 表达式值
     */
    private String getSpringElValue(String expression, Object[] args) {
        if (StringUtils.isEmpty(expression)) {
            return "";
        }

        try {
            // 创建表达式解析器
            ExpressionParser parser = new SpelExpressionParser();
            // 创建评估上下文
            StandardEvaluationContext context = getStandardEvaluationContext(args);

            // 解析表达式
            Expression exp = parser.parseExpression(expression);
            Object result = exp.getValue(context);

            return result != null ? result.toString() : "";
        } catch (Exception e) {
            log.error("get value for spring el expression error", e);
            throw CommonException.error("get value for spring el expression error");
        }
    }

    /**
     * 获取评估上下文
     *
     * @param args 参数
     * @return 评估上下文
     */
    private static StandardEvaluationContext getStandardEvaluationContext(Object[] args) {
        StandardEvaluationContext context = new StandardEvaluationContext();

        // 设置参数变量
        if (ArrayUtils.isNotEmpty(args)) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                context.setVariable("param" + i, arg);
                String simpleName = arg.getClass().getSimpleName();
                context.setVariable(simpleName, arg);
                context.setVariable(toCamelCase(simpleName), arg);
            }

            // 如果只有一个参数，也作为根对象
            if (ArrayUtils.isSingle(args)) {
                context.setRootObject(args[0]);
            }
        }
        return context;
    }

    /**
     * 将类名转换为驼峰式
     *
     * @param className 类名
     * @return 驼峰式类名
     */
    private static String toCamelCase(String className) {
        if (StringUtils.isEmpty(className)) {
            return className;
        }
        if (className.length() == 1) {
            return className.toLowerCase();
        }
        return Character.toLowerCase(className.charAt(0)) + className.substring(1);
    }

}
