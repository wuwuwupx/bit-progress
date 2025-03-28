package com.bitprogress.elasticsearch.config;

import org.dromara.easyes.spring.annotation.EsMapperScan;
import org.dromara.easyes.starter.EsAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

@Configuration
@EsMapperScan("com.bitprogress.**.esmapper")
@AutoConfigureAfter(EsAutoConfiguration.class)
public class EasyEsAutoConfiguration {

}
