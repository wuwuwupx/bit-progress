package com.bitprogress.config;

import com.bitprogress.interceptor.FeignRequestInterceptor;
import com.bitprogress.property.ServerTokenProperties;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign请求拦截器配置
 */
@Configuration
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor feignRequestInterceptor(ServerTokenProperties serverTokenProperties) {
        return new FeignRequestInterceptor(serverTokenProperties);
    }

}
