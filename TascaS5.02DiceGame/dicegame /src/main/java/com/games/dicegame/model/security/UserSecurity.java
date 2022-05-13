package com.games.dicegame.model.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {
    public boolean hasId(Authentication authentication, String id) {

        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();

        return userDetailsCustom.getId().toString().equalsIgnoreCase(id);
    }
}