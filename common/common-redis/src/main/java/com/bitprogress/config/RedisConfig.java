package com.bitprogress.config;

import com.bitprogress.service.StringRedisService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 配置默认的Redis
 */
@Configuration
public class RedisConfig {

    @Bean
    public StringRedisService stringRedisService(StringRedisTemplate stringRedisTemplate) {
        return new StringRedisService(stringRedisTemplate);
    }

}
