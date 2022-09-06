package com.bitprogress;

import com.bitprogress.util.BeanManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wuwuwupx
 * create on 2021/6/22 23:44
 * WebBaseApplication is
 */
@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement(proxyTargetClass = true)
@EnableDiscoveryClient
@EnableFeignClients
public class BaseWebApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BaseWebApplication.class, args);
        BeanManager.setApplicationContext(context);
    }

}
