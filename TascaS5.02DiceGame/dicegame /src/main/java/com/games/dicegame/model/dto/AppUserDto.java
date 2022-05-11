package com.games.dicegame.model.dto;

import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.domain.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Data
public class AppUserDto implements Serializable {
    private final Integer id;
    @Email
    private final String email;
    @NotEmpty
    private final String password;
    private final String username;
    private final Date registrationDate;

    private Collection<Role> roles = new ArrayList<>();

    private Collection<Game> games = new ArrayList<>();

    public AppUserDto(String email, String password, String username) {
        id = null;
        this.email = email;
        this.password = password;
        if(username==null){
            this.username = "ANONYMOUS";
        }else {
            this.username = username.toUpperCase();
        }
        this.registrationDate = Calendar.getInstance().getTime();
    }

    public AppUserDto(Integer id, String mail, String password, String username, Date registrationDate, Collection<Role> roles) {
        this.id = id;
        this.email = mail;
        this.password = password;
        if(username==null){
            this.username = "ANONYMOUS";
        }else {
            this.username = username.toUpperCase();
        }
        this.registrationDate = registrationDate;
        this.roles = roles;
    }
}
