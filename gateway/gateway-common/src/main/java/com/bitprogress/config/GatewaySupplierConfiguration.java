package com.bitprogress.config;

import com.bitprogress.loadbalancer.GatewayServiceInstanceListSupplier;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author wpx
 * 全局服务配置
 */
public class GatewaySupplierConfiguration {

    @Bean
    ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context) {
        ReactiveDiscoveryClient reactiveDiscoveryClient = context.getBean(ReactiveDiscoveryClient.class);
        return new GatewayServiceInstanceListSupplier(reactiveDiscoveryClient, context.getEnvironment());
    }

}
