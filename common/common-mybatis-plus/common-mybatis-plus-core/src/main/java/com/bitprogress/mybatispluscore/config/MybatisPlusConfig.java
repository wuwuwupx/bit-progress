package com.bitprogress.mybatispluscore.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.bitprogress.mybatispluscore.handler.DataScopeLineHandler;
import com.bitprogress.mybatispluscore.handler.SingleTypeDataScopeLineHandler;
import com.bitprogress.mybatispluscore.handler.TenantIdLineHandler;
import com.bitprogress.mybatispluscore.interceptor.TenantSqlInnerInterceptor;
import com.bitprogress.mybatispluscore.properties.DataScopeProperties;
import com.bitprogress.mybatispluscore.properties.TenantProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
@MapperScan("com.bitprogress.**.mapper")
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties({TenantProperties.class, DataScopeProperties.class})
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor(TenantProperties tenantProperties,
                                                        DataScopeProperties dataScopeProperties) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 租户插件
        TenantIdLineHandler tenantSqlHandler = new TenantIdLineHandler(tenantProperties);
        TenantSqlInnerInterceptor sqlInnerInterceptor = new TenantSqlInnerInterceptor(tenantSqlHandler);
        interceptor.addInnerInterceptor(sqlInnerInterceptor);

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
