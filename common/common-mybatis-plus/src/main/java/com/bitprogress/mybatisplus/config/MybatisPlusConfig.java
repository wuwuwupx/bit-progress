package com.bitprogress.mybatisplus.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.bitprogress.mybatisplus.handler.TenantSqlHandler;
import com.bitprogress.mybatisplus.plugins.TenantSqlInnerInterceptor;
import com.bitprogress.mybatisplus.properties.TenantProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;

@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
@MapperScan("com.bitprogress.**.mapper")
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties(TenantProperties.class)
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor(TenantProperties tenantProperties) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 租户插件
        if (Objects.nonNull(tenantProperties.getEnabled()) && tenantProperties.getEnabled()) {
            TenantSqlHandler tenantSqlHandler = new TenantSqlHandler(tenantProperties);
            TenantSqlInnerInterceptor tenantSqlInnerInterceptor = new TenantSqlInnerInterceptor(tenantSqlHandler);
            interceptor.addInnerInterceptor(tenantSqlInnerInterceptor);
        }

        // 乐观锁插件
        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);

        // 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    /**
     * 逻辑删除插件
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
    }

}
