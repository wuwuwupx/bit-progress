package com.bitprogress.property;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 *  redis多数据源配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = RedisDataSourceProperties.PREFIX)
public class RedisDataSourceProperties {

    public static final String PREFIX = "spring.redis";

    private Map<String, RedisMessageProperties> dataSource;

}
