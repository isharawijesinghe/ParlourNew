package com.ss.parlour.apigateway.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfiguration  {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.cors().and().csrf().disable();
        http
                .authorizeExchange()
//                .pathMatchers("/",
//                        "/error",
//                        "/favicon.ico",
//                        "/**/*.png",
//                        "/**/*.gif",
//                        "/**/*.svg",
//                        "/**/*.jpg",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js")
//                .permitAll()
                .pathMatchers("/authentication/auth/**", "/login/**", "/auth/**",
                        "/oauth2/**", "/createUser/**", "/authentication/**",
                        "/user/**", "/user/upload/**")
                .permitAll()
                .anyExchange().authenticated()
                .and()
                .csrf().disable()
                .oauth2ResourceServer()
                .jwt();

        return http.build();
    }

}
