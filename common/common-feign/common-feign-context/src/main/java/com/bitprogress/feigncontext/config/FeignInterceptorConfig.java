package com.bitprogress.feigncontext.config;

import com.bitprogress.feigncontext.interceptor.FeignRequestInterceptor;
import com.bitprogress.ormcontext.service.DataScopeContextService;
import com.bitprogress.ormcontext.service.TenantContextService;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;
import org.springframework.context.annotation.Bean;

/**
 * feign请求拦截器配置
 */
@AutoConfiguration
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor feignRequestInterceptor(ServiceInstanceChooser serviceInstanceChooser,
                                                      TenantContextService tenantContextService,
                                                      DataScopeContextService<?, ?> dataScopeContextService) {
        return new FeignRequestInterceptor(serviceInstanceChooser, tenantContextService, dataScopeContextService);
    }

}
