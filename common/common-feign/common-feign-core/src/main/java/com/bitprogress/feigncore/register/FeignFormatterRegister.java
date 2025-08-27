package com.bitprogress.feigncore.register;

import com.bitprogress.formatter.LocalDateFormatter;
import com.bitprogress.formatter.LocalDateTimeFormatter;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

/**
 * feign的序列化和反序列化格式化注册
 */
@Configuration
public class FeignFormatterRegister implements FeignFormatterRegistrar {

    /**
     * 格式化注册
     *
     * @param registry 注册器
     */
    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateTimeFormatter());
        registry.addFormatter(new LocalDateFormatter());
    }

}