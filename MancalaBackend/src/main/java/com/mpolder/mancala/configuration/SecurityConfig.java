package com.mpolder.mancala.configuration;

import com.mpolder.mancala.auth.MancalaOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private MancalaOidcUserService oidcUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(
                                    "/login",
                                    "/error"
                            ).permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin().loginPage("/login")
                .and()
                .csrf().disable()
                .oauth2Login()
                .userInfoEndpoint().oidcUserService(oidcUserService);
        return http.build();
    }
}
