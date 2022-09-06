package com.bitprogress.config;

import com.bitprogress.util.LocalDateTimeUtils;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * @author wuwuwupx
 * feign的序列化和反序列化格式化注册
 */
@Configuration
public class FeignFormatterRegister implements FeignFormatterRegistrar {

    /**
     * 格式化注册
     *
     * @param registry
     */
    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateTimeFormatter());
        registry.addFormatter(new LocalDateFormatter());
    }

    /**
     * LocalDateTime格式化
     */
    public static class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

        @Override
        public LocalDateTime parse(String text, Locale locale) {
            return LocalDateTime.parse(text);
        }

        @Override
        public String print(LocalDateTime date, Locale locale) {
            return String.valueOf(LocalDateTimeUtils.toMilliseconds(date));
        }
    }

    /**
     * LocalDate格式化
     * 在接受参数上需要加上 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 就可以接收 xxxx-xx-xx的日期格式了
     */
    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) {
            return LocalDate.parse(text);
        }

        @Override
        public String print(LocalDate date, Locale locale) {
            return String.valueOf(date);
        }
    }

}