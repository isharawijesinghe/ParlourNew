package com.ss.parlour.userservice.configurations.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.mvcMatcher("/**")
//                .authorizeRequests()
//                .mvcMatchers("/**")
//                .access("hasAuthority('SCOPE_message.read')")
//                .and()
//                .oauth2ResourceServer()
//                .jwt();
//        return http.build();
//    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll();
        return http.build();
    }

}