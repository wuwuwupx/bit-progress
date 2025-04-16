package com.bitprogress.mybatispluscore.interceptor;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.extension.toolkit.PropertyMapper;
import com.bitprogress.mybatispluscore.handler.TenantHandler;
import lombok.Setter;

import java.util.Properties;

/**
 * 租户拦截器
 */
@Setter
public class TenantSqlInnerInterceptor extends SqlInnerInterceptor {

    private TenantHandler tenantHandler;

    public TenantSqlInnerInterceptor(TenantHandler interceptorHandler) {
        super(interceptorHandler);
        this.tenantHandler = interceptorHandler;
    }

    @Override
    public void setProperties(Properties properties) {
        PropertyMapper.newInstance(properties).whenNotBlank("tenantHandler",
                ClassUtils::newInstance, this::setTenantHandler);
    }

}
