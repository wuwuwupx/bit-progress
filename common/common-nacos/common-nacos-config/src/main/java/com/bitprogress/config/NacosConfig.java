package com.bitprogress.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * @author wuwuwupx
 */
@AutoConfigureAfter(NacosConfigProperties.class)
public class NacosConfig {

    /**
     * 初始化nacos配置中心服务
     *
     * @return 对应环境的nacos配置中心服务
     * @throws NacosException
     */
    @Bean
    public ConfigService configService(NacosConfigProperties nacosConfigProperties) throws NacosException {
        Properties properties = new Properties();
        String namespace = nacosConfigProperties.getNamespace();
        String serverAddr = nacosConfigProperties.getServerAddr();
        properties.setProperty(PropertyKeyConst.NAMESPACE, namespace);
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
        return NacosFactory.createConfigService(properties);
    }

}
