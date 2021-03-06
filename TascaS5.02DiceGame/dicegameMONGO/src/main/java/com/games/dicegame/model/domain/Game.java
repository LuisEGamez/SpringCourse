package com.games.dicegame.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




@Getter
@Setter
@ToString
public class Game {

    private Integer id;
    private double dice1;
    private double dice2;
    private double total;
    private String result;

    public Game() {
        dice1 = Math.floor(Math.random()*(6-1+1)+1);
        dice2 = Math.floor(Math.random()*(6-1+1)+1);
        total = dice1 + dice2;
        if( total == 7){
            result = "WIN";
        }else {
            result = "LOSE";
        }
    }
}
