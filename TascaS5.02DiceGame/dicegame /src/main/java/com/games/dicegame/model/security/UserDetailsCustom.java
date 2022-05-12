package com.games.dicegame.model.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/*
*
* We need to create a custom class from User to be able to save the users' id from database
*
 */

@Getter
@Setter
public class UserDetailsCustom extends User {

    private final Integer id;

    public UserDetailsCustom(Integer id ,String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.id = id;
    }
}
