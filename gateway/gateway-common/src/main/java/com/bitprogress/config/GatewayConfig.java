package com.bitprogress.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

/**
 * @author wpx
 */
@LoadBalancerClients(defaultConfiguration = GatewaySupplierConfiguration.class)
public class GatewayConfig {
}
