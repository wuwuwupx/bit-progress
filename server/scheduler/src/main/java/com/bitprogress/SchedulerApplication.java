package com.bitprogress;

import com.bitprogress.util.BeanManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author wuwuwupx
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SchedulerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SchedulerApplication.class);
        BeanManager.setApplicationContext(context);
    }

}
