package com.bitprogress.config;

import com.bitprogress.property.SwaggerProperties;
import com.bitprogress.util.CollectionUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.bitprogress.constant.VerifyConstant.*;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

/**
 * @author wuwuwupx
 *  swagger配置
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerProperties.getEnable())
                .groupName(swaggerProperties.getGroup())
                .apiInfo(apiInfo())
                .select()
                .apis(withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getApplicationName())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        Map<String, String> securitySchemeMap = swaggerProperties.getSecurityScheme();
        if (CollectionUtils.isNotEmpty(securitySchemeMap)) {
            securitySchemeMap.forEach((name, keyName) -> {
                ApiKey apiKey = new ApiKey(name, keyName, HEADER);
                securitySchemes.add(apiKey);
            });
        }
        return securitySchemes;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contexts = new ArrayList<>();
        SecurityContextBuilder contextBuilder = SecurityContext.builder();
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        AuthorizationScope scope = new AuthorizationScope(GLOBAL, ACCESS_EVERY_THING);
        scopes[0] = scope;
        List<SecurityReference> references = new ArrayList<>();
        Set<String> securityContextSet = swaggerProperties.getSecurityContext();
        if (CollectionUtils.isNotEmpty(securityContextSet)) {
            securityContextSet.forEach(reference -> {
                SecurityReference securityReference = new SecurityReference(reference, scopes);
                references.add(securityReference);
            });
        }
        SecurityContext context = contextBuilder.securityReferences(references).build();
        contexts.add(context);
        return contexts;
    }

}
