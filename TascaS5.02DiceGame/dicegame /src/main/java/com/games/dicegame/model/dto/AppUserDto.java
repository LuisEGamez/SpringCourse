package com.games.dicegame.model.dto;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.domain.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;

@Data
public class AppUserDto implements Serializable {
    private Integer id;
    @Email
    private String email;
    @NotEmpty
    private String password;
    private String username;
    private Date registrationDate;

    private double successRate = 0;

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

    public AppUserDto(Integer id, String email, String password, String username, Date registrationDate, Collection<Role> roles, Collection<Game> games) {
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

    public void updateRate(Collection<Game> games){

        double totalGames;
        int winGames = 0;

        if(!games.isEmpty()){
            totalGames = games.size();
            for (Game game: games) {
                if(game.getResult().equalsIgnoreCase("WIN")){
                    winGames++;
                }
            }
            successRate = (winGames/totalGames)*100;
        }else{
            successRate = 0;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUserDto that = (AppUserDto) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
