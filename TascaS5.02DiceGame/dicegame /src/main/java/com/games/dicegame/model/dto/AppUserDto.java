package com.games.dicegame.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Calendar;
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

    public AppUserDto(String email, String password, String username) {
        id = null;
        this.email = email;
        this.password = password;
        if(username==null){
            this.username = "ANÓNIMO";
        }else {
            this.username = username.toUpperCase();
        }
        this.registrationDate = Calendar.getInstance().getTime();
    }

    public AppUserDto(Integer id, String mail, String password, String username, Date registrationDate) {
        this.id = id;
        this.email = mail;
        this.password = password;
        if(username==null){
            this.username = "ANONYMOUS";
        }else {
            this.username = username.toUpperCase();
        }
        this.registrationDate = registrationDate;
    }
}
