package com.ss.parlour.articleservice.configurations.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable();

        http.mvcMatcher("/**")
                .authorizeRequests()
                .mvcMatchers("/**")
                .access("hasAuthority('SCOPE_message.read')")
                .and()
                .oauth2ResourceServer().jwt();
        return http.build();
    }

    //This configuration can be used in testing >> Disable authorization
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.cors().and().csrf().disable();
//
//        http.mvcMatcher("/**")
//                .authorizeRequests()
//                .mvcMatchers("/**")
//                .permitAll()
//                .antMatchers("/article/**", "/article/addComment/**", "/login/**", "/auth/**", "/oauth2/**", "/createUser/**", "/authentication/**")
//                .permitAll();
//        return http.build();
//    }

}
