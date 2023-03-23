package com.mpolder.mancala.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class MancalaOAuth2User implements OAuth2User {
    private final OAuth2User principal;

    public MancalaOAuth2User(OAuth2User principal) {
        this.principal = principal;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return principal.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return principal.getAuthorities();
    }

    @Override
    public String getName() {
        return principal.getAttribute("name");
    }

    public String getEmail() {
        return principal.getAttribute("email");
    }
}
