package com.bitprogress.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author wuwuwupx
 */
@SuppressWarnings("Convert2Lambda")
@Configuration
public class CommonConfig {

    /**
     * 跨域配置
     * Gateway已经配置跨域，服务上不能重复配置
     * @Bean
     */
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        //配置CorsFilter优先级
        bean.setOrder(0);
        return bean;
    }

}
