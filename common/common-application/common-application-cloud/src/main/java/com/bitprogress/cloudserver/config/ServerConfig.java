package com.bitprogress.cloudserver.config;

import com.bitprogress.cloudserver.property.ApplicationDataProperties;
import com.bitprogress.cloudserver.property.ApplicationProperties;
import com.bitprogress.cloudserver.property.ApplicationTokenProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@EnableConfigurationProperties({ApplicationTokenProperties.class, ApplicationProperties.class, ApplicationDataProperties.class})
@Configuration
public class ServerConfig {

}
