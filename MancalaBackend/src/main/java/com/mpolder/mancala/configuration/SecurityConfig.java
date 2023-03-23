package com.mpolder.mancala.configuration;

import com.mpolder.mancala.auth.MancalaOAuth2Service;
import com.mpolder.mancala.auth.OAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private MancalaOAuth2Service mancalaOAuth2Service;
    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .oauth2Login()
                .userInfoEndpoint().userService(mancalaOAuth2Service).and().successHandler(oAuth2SuccessHandler);
        return http.build();
    }

    @Bean
    public OAuth2SuccessHandler oauth() {
        return new OAuth2SuccessHandler();
    }
}
