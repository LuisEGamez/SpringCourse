package com.games.dicegame.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double dice1;
    private double dice2;
    private String result;

    public Game() {
        dice1 = Math.floor(Math.random()*(6-1+1)+1);
        dice2 = Math.floor(Math.random()*(6-1+1)+1);
        if( dice1 + dice2 == 7){
            result = "WIN";
        }else {
            result = "LOSE";
        }
    }
}
