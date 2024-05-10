package com.bitprogress.config;

import com.bitprogress.interceptor.FeignRequestInterceptor;
import com.bitprogress.property.ServerTokenProperties;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuwuwupx
 * create on 2021/6/23 0:22
 * feign请求过滤器配置
 */
@Configuration
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor feignRequestInterceptor(ServerTokenProperties serverTokenProperties) {
        return new FeignRequestInterceptor(serverTokenProperties);
    }

}
