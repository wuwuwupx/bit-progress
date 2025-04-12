package com.bitprogress.mybatispluscore.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.bitprogress.mybatispluscore.handler.DataScopeHandler;
import com.bitprogress.mybatispluscore.handler.DefaultTenantHandler;
import com.bitprogress.mybatispluscore.handler.SingleTypeDataScopeHandler;
import com.bitprogress.mybatispluscore.handler.TenantHandler;
import com.bitprogress.mybatispluscore.interceptor.TenantSqlInnerInterceptor;
import com.bitprogress.mybatispluscore.properties.DataScopeProperties;
import com.bitprogress.mybatispluscore.properties.TenantProperties;
import com.bitprogress.ormcontext.service.DataScopeContextService;
import com.bitprogress.ormcontext.service.TenantContextService;
import com.bitprogress.ormcontext.service.impl.SingleTypeDataScopeContextService;
import com.bitprogress.ormcontext.service.impl.TenantContextServiceImpl;
import com.bitprogress.ormparser.Service.DataScopeOrmDataService;
import com.bitprogress.ormparser.Service.TenantOrmDataService;
import com.bitprogress.ormparser.Service.impl.SingleTypeDataScopeOrmDataService;
import com.bitprogress.ormparser.Service.impl.TenantOrmDataServiceImpl;
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
    public MybatisPlusInterceptor paginationInterceptor(TenantHandler tenantSqlHandler) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 租户插件
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

    @Bean
    public TenantHandler tenantSqlHandler(TenantProperties tenantProperties, TenantOrmDataService ormDataService) {
        return new DefaultTenantHandler(tenantProperties, ormDataService);
    }

    @Bean
    public TenantOrmDataService tenantOrmDataService(TenantContextService tenantContextService) {
        return new TenantOrmDataServiceImpl(tenantContextService);
    }

    @Bean
    public TenantContextService tenantContextService() {
        return new TenantContextServiceImpl();
    }

    @Bean
    public DataScopeHandler dataScopeHandler(DataScopeProperties dataScopeProperties,
                                             DataScopeOrmDataService ormDataService) {
        return new SingleTypeDataScopeHandler(dataScopeProperties, ormDataService);
    }

    @Bean
    public DataScopeOrmDataService dataScopeOrmDataService(SingleTypeDataScopeContextService dataScopeContextService) {
        return new SingleTypeDataScopeOrmDataService(dataScopeContextService);
    }

    @Bean
    public SingleTypeDataScopeContextService dataScopeContextService() {
        return new SingleTypeDataScopeContextService();
    }

    /**
     * 逻辑删除插件
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
    }

}
