package com.bitprogress.discover;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClientConfiguration;
import com.bitprogress.property.ServerVersionProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用重写的获取Nacos服务的类
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnNacosDiscoveryEnabled
@EnableConfigurationProperties(ServerVersionProperties.class)
@EnableAutoConfiguration(exclude = {NacosDiscoveryAutoConfiguration.class, NacosDiscoveryClientConfiguration.class})
public class NacosCustomDiscoveryAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public NacosDiscoveryProperties nacosProperties() {
		return new NacosDiscoveryProperties();
	}

	@Bean
	@ConditionalOnMissingBean
	public NacosServiceDiscovery nacosServiceDiscovery(ServerVersionProperties serverVersionProperties,
													   NacosDiscoveryProperties discoveryProperties,
													   NacosServiceManager nacosServiceManager) {
		return new NacosServiceDiscovery(serverVersionProperties, discoveryProperties, nacosServiceManager);
	}

}
