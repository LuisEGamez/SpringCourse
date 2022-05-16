package com.games.dicegame.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double dice1;
    private double dice2;
    private double total;
    private String result;

    public Game(double dice1, double dice2, double total, String result) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.total = total;
        this.result = result;
    }
}
