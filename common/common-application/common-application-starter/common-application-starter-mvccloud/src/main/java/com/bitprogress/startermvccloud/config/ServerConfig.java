package com.bitprogress.startermvccloud.config;

import com.bitprogress.startermvccloud.property.ApplicationDataProperties;
import com.bitprogress.startermvccloud.property.ApplicationProperties;
import com.bitprogress.startermvccloud.property.ApplicationTokenProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@EnableConfigurationProperties({ApplicationTokenProperties.class, ApplicationProperties.class, ApplicationDataProperties.class})
@Configuration
public class ServerConfig {

}
