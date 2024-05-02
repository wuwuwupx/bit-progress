package com.bitprogress.register;

import com.bitprogress.property.RedisDataSourceProperties;
import com.bitprogress.property.RedisMessageProperties;
import com.bitprogress.service.StringRedisService;
import com.bitprogress.util.CollectionUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wuwuwupx
 * 初始化RedisTemplate并交给Spring管理
 * 装载bean之前会扫描 InstantiationAwareBeanPostProcessor ，以此达到先加载此类的目的
 */
@Component
public class RedisTemplateRegister implements BeanFactoryAware, InstantiationAwareBeanPostProcessor {

    @Autowired
    private RedisDataSourceProperties redisDataSourceProperties;

    /**
     * StringRedisTemplate的beanName后缀
     */
    public static final String TEMPLATE_NAME = "StringRedisTemplate";

    /**
     * RedisBaseService的beanName后缀
     */
    public static final String SERVICE_NAME = "StringRedisService";

    /**
     * 读取redis配置并创建对应的StringRedisTemplate
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        Map<String, RedisMessageProperties> redisDataSource = redisDataSourceProperties.getDataSource();
        if (CollectionUtils.isNotEmpty(redisDataSource)) {
            redisDataSource.forEach((name, dataSource) -> {
                StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
                RedisConnectionFactory redisConnection = getRedisConnection(dataSource);
                stringRedisTemplate.setConnectionFactory(redisConnection);
                stringRedisTemplate.afterPropertiesSet();
                // 向ioc容器中注入StringRedisTemplate
                String templateName = name + TEMPLATE_NAME;
                listableBeanFactory.registerSingleton(templateName, stringRedisTemplate);
                // 向ioc容器中注入RedisBaseService
                StringRedisService stringRedisService = new StringRedisService(stringRedisTemplate);
                String serviceName = name + SERVICE_NAME;
                listableBeanFactory.registerSingleton(serviceName, stringRedisService);
            });
        }
    }

    /**
     * 配置redisConnection
     *
     * @param dataSource
     */
    private RedisConnectionFactory getRedisConnection(RedisMessageProperties dataSource) {
        return getLettuceConnectionFactory(dataSource);
    }

    /**
     * 获取LettuceConnection
     *
     * @param dataSource
     */
    private RedisConnectionFactory getLettuceConnectionFactory(RedisMessageProperties dataSource) {
        RedisStandaloneConfiguration redisConfig = getRedisConfig(dataSource);
        RedisMessageProperties.Pool pool = dataSource.getLettuce().getPool();
        GenericObjectPoolConfig poolConfig = getPoolConfig(pool);
        LettucePoolingClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig).build();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfig, clientConfiguration);
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    /**
     * 获取连接池配置
     *
     * @param pool
     */
    private GenericObjectPoolConfig getPoolConfig(RedisMessageProperties.Pool pool) {
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(pool.getMaxActive());
        poolConfig.setMaxIdle(pool.getMaxIdle());
        poolConfig.setMinIdle(pool.getMinIdle());
        if (pool.getTimeBetweenEvictionRuns() != null) {
            poolConfig.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRuns().toMillis());
        }
        if (pool.getMaxWait() != null) {
            poolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
        }
        return poolConfig;
    }

    /**
     * 获取redis连接配置
     *
     * @param dataSource
     */
    private RedisStandaloneConfiguration getRedisConfig(RedisMessageProperties dataSource) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(dataSource.getHost());
        redisConfig.setPort(dataSource.getPort());
        redisConfig.setUsername(dataSource.getUsername());
        redisConfig.setPassword(RedisPassword.of(dataSource.getPassword()));
        redisConfig.setDatabase(dataSource.getDatabase());
        return redisConfig;
    }

}
