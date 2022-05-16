package com.games.dicegame.model.dto;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Game;
import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;

@Data
public class AppUserDto implements Serializable {
    private String id;
    @Email
    private String email;
    @NotEmpty
    private String password;
    private String username;
    private Date registrationDate;

    private double successRate;

    private Collection<String> roles = new ArrayList<>();

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

    public AppUserDto(String id, String mail, String password, String username, Date registrationDate, Collection<String> roles) {
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

    public AppUserDto(String id, String email, String password, String username, Date registrationDate, Collection<String> roles, Collection<Game> games) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.registrationDate = registrationDate;
        this.roles = roles;
        this.games = games;
        successRate = calculateRate(this.games);
    }

    public AppUserDto(AppUser appUser) {
        id = appUser.getId();
        email = appUser.getEmail();
        password = appUser.getPassword();
        username = appUser.getUsername();
        registrationDate = appUser.getRegistrationDate();
        roles = appUser.getRoles();
        games = appUser.getGames();
        successRate = calculateRate(this.games);
    }

    public double calculateRate(Collection<Game> games){

        double totalGames;
        int winGames = 0;
        double result = 0;


        if(!games.isEmpty()){
            totalGames = games.size();
            for (Game game: games) {
                if(game.getResult().equalsIgnoreCase("WIN")){
                    winGames++;
                }
            }
            result = (winGames/totalGames)*100;
        }


        return result;


    }
}
