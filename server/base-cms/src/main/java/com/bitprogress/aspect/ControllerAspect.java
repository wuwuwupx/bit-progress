package com.bitprogress.aspect;

import com.bitprogress.util.JsonUtils;
import com.bitprogress.mapper.ManagerLogMapper;
import com.bitprogress.model.managerlog.envm.MethodEnum;
import com.bitprogress.util.NumberUtils;
import com.bitprogress.entity.ManagerLog;
import com.bitprogress.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author wuwuwupx
 * 切入所有的对外开放的controller，记录下POST、PUT、DELETE操作的操作信息
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Autowired
    private ManagerLogMapper managerLogMapper;

    /**
     * 对所有controller包及子包下的方法切入
     */
    @Pointcut(value = "execution(public * com.bitprogress.controller..*.*(..))")
    public void controllerPointcut() {
    }

    /**
     * 记录 POST、PUT、DELETE 方法的操作信息（managerId、API、参数）
     *
     * @param joinPoint
     */
    @Before(value = "controllerPointcut()")
    public void controllerBefore(JoinPoint joinPoint) {
        try {
            Long userId = UserUtils.getUserId();
            if (userId == NumberUtils.INT_MINUS_ONE) {
                return;
            }
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String method = request.getMethod();
            HttpMethod httpMethod = HttpMethod.resolve(method);
            switch (Objects.requireNonNull(httpMethod)) {
                case POST:
                case PUT:
                case DELETE: {
                    LocalDateTime now = LocalDateTime.now();
                    ManagerLog managerLog = new ManagerLog();
                    MethodEnum methodEnum = MethodEnum.valueOf(httpMethod.name());
                    managerLog.setManagerId(userId).setArgs(JsonUtils.serializeObject(joinPoint.getArgs())).setMethod(methodEnum)
                            .setUri(request.getRequestURI()).setCreateTime(now).setUpdateTime(now).setDeleted(false);
                    managerLogMapper.insert(managerLog);
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("controllerBeforeCut error", e);
        }
    }

}
