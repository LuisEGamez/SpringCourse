package com.games.dicegame.model.domain;



import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;
    @NotEmpty
    private String password;
    private String username;

    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private Collection<Game> games = new ArrayList<>();

    @Column(name = "successrate")
    private double successRate;


    public AppUser(String email, String password, String username, Date registrationDate) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.registrationDate = registrationDate;
    }

}
