package com.mpolder.mancala.auth;

import com.mpolder.mancala.model.User;
import com.mpolder.mancala.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class MancalaOidcUserService extends OidcUserService {
    @Autowired
    private IUserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidc = super.loadUser(userRequest);
        userService.registerIfMissing(toUser(oidc));
        return oidc;
    }

    private User toUser(OidcUser oidc) {
        GoogleUserInfo info = new GoogleUserInfo(oidc.getAttributes());
        return new User(info.getEmail(), info.getName());
    }
}
