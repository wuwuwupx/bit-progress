package com.bitprogress.config;

import com.bitprogress.interceptor.FeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign请求拦截器配置
 */
@Configuration
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor feignRequestInterceptor(ServiceInstanceChooser serviceInstanceChooser) {
        return new FeignRequestInterceptor(serviceInstanceChooser);
    }

}
