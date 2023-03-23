package com.mpolder.mancala.auth;

import com.mpolder.mancala.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("oAuth2SuccessHandler")
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private IUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication instanceof OAuth2AuthenticationToken auth) {
            MancalaOAuth2User oauthUser = new MancalaOAuth2User(auth.getPrincipal());
            userService.processOAuthLogin(oauthUser);
            response.sendRedirect("/");
        } else {
            response.sendError(401);
        }
    }
}
