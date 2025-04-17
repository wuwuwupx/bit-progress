package com.bitprogress.securityspring.config;

import com.bitprogress.securityspring.handler.ForbiddenExceptionHandler;
import com.bitprogress.securityspring.handler.InvalidAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AccessDeniedHandler accessDeniedHandler,
                                                   AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
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
                        // 允许白名单请求通过
//                        .requestMatchers(Stream.of(ignoreWhiteProperties.getAnonymousApiWhites(), ignoreWhiteProperties.getInnerApiWhites())
//                                .flatMap(Collection::stream)
//                                .map(api -> new AntPathRequestMatcher(api.getUrl(), api.getHttpMethod()))
//                                .toList()
//                                .toArray(new AntPathRequestMatcher[]{}))
//                        .permitAll()
                        // 任意请求通过AuthorizationManager验证是否通过
//                        .anyRequest().access(customAuthorizationManager)
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