package com.bitprogress.securityroute.config;

import com.bitprogress.securityroute.annotation.AnonymousApi;
import com.bitprogress.securityroute.annotation.InnerApi;
import com.bitprogress.securityroute.annotation.Permission;
import com.bitprogress.securityroute.context.RouteContext;
import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.util.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 特殊接口初始化
 */
public abstract class SpecialApiInitialization implements InitializingBean, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpecialApiInitialization.applicationContext = applicationContext;
    }

    /**
     * Invoked by the containing {@code BeanFactory} after it has set all bean properties
     * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
     * <p>This method allows the bean instance to perform validation of its overall
     * configuration and final initialization when all bean properties have been set.
     *
     * @throws Exception in the event of misconfiguration (such as failure to set an
     *                   essential property) or if initialization fails for any other reason
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        handlerMethods.forEach((info, handlerMethod) -> {
            Set<ApiRoute> anonymousRoutes = getApiRoutes(info, handlerMethod, AnonymousApi.class);
            Set<ApiRoute> innerRoutes = getApiRoutes(info, handlerMethod, InnerApi.class);
            Set<PermissionRoute> permissionRoutes = getPermissionRoutes(info, handlerMethod);
            publishRoute(permissionRoutes, anonymousRoutes, innerRoutes);
        });
    }

    /**
     * 获取路由
     *
     * @return 路由集合
     */
    private <R extends Annotation> Set<ApiRoute> getApiRoutes(RequestMappingInfo info,
                                                              HandlerMethod handlerMethod,
                                                              Class<R> annotationType) {
        R beanAnnotation = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), annotationType);
        R methodAnnotation = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), annotationType);
        if (Objects.isNull(beanAnnotation) && Objects.nonNull(methodAnnotation)) {
            return null;
        }
        String methodName = handlerMethod.getMethod().getName();
        RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
        Set<RequestMethod> methods = methodsCondition.getMethods();
        return CollectionUtils.isEmpty(methods)
                ? null
                : CollectionUtils.toSet(methods, method -> new ApiRoute(method.asHttpMethod(), methodName));
    }

    /**
     * 获取权限路由
     *
     * @return 路由集合
     */
    private Set<PermissionRoute> getPermissionRoutes(RequestMappingInfo info,
                                                     HandlerMethod handlerMethod) {
        Permission permission = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Permission.class);
        if (Objects.isNull(permission)) {
            return null;
        }
        String methodName = handlerMethod.getMethod().getName();
        RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
        Set<RequestMethod> methods = methodsCondition.getMethods();
        return CollectionUtils.isEmpty(methods)
                ? null
                : CollectionUtils.toSet(methods, method -> PermissionRoute.getByPermission(permission, method, methodName));
    }

    /**
     * 发布路由
     *
     * @param permissionRoutes 权限路由
     * @param anonymousRoutes  匿名路由
     * @param innerRoutes      内部路由
     */
    protected void publishRoute(Set<PermissionRoute> permissionRoutes,
                                Set<ApiRoute> anonymousRoutes,
                                Set<ApiRoute> innerRoutes) {
        if (Objects.nonNull(permissionRoutes)) {
            RouteContext.addPermissionRoutes(permissionRoutes);
        }
        if (Objects.nonNull(anonymousRoutes)) {
            RouteContext.addAnonymousRoutes(anonymousRoutes);
        }
        if (Objects.nonNull(innerRoutes)) {
            RouteContext.addInnerRoutes(innerRoutes);
        }
    }

}
