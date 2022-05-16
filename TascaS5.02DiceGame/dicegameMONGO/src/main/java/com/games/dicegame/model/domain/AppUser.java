package com.games.dicegame.model.domain;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Document(collection = "users") // annotation helps us override the collection name by “fruits”.
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppUser {

    @Id
    private String id;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
    private String username;

    private Date registrationDate;

    private Collection<String> roles = new ArrayList<>();

    private Collection<Game> games = new ArrayList<>();

    private double successRate;


    public AppUser(String email, String password, String username, Date registrationDate) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.registrationDate = registrationDate;
    }

}
