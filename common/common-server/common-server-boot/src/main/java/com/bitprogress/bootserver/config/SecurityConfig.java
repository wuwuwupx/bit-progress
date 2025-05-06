package com.bitprogress.bootserver.config;

import com.bitprogress.bootserver.propertity.AuthProperties;
import com.bitprogress.bootserver.service.route.AnonymousRouteMatchService;
import com.bitprogress.securityroute.service.match.AbstractAnonymousRouteMatchService;
import com.bitprogress.securityroute.service.match.AbstractInnerRouteMatchService;
import com.bitprogress.securityspring.handler.ForbiddenExceptionHandler;
import com.bitprogress.securityspring.handler.InvalidAuthenticationEntryPoint;
import com.bitprogress.util.CollectionUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableAutoConfiguration
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties({AuthProperties.class})
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AccessDeniedHandler accessDeniedHandler,
                                                   AuthenticationEntryPoint authenticationEntryPoint,
                                                   AnonymousRouteMatchService anonymousRouteMatchService,
                                                   AbstractInnerRouteMatchService innerRouteMatchService) throws Exception {
        return http
                // 禁用basic明文验证
                .httpBasic(AbstractHttpConfigurer::disable)
                // 前后端分离架构不需要csrf保护
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用默认登录页
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用默认登出页
                .logout(AbstractHttpConfigurer::disable)
                // 前后端分离是无状态的，不需要session了，直接禁用。
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // 允许所有OPTIONS请求通过
                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()
                        .requestMatchers(CollectionUtils
                                .map(anonymousRouteMatchService.getRoutes(),
                                        apiRoute -> {
                                            HttpMethod method = apiRoute.getMethod();
                                            return new AntPathRequestMatcher(apiRoute.getUrl(), method.name());
                                        })
                                .toArray(AntPathRequestMatcher[]::new)
                        ).permitAll()
                )
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(AccessDeniedHandler.class)
    public AccessDeniedHandler forbiddenExceptionHandler() {
        return new ForbiddenExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
    public AuthenticationEntryPoint invalidAuthenticationEntryPoint() {
        return new InvalidAuthenticationEntryPoint();

    }

}