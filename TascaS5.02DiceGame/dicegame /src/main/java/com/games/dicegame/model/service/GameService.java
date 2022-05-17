package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.dto.AppUserDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface GameService {

    Game play();

    void deleteGames(Integer id);

    Collection<Game> getGames(AppUserDto appUserDto);

    Game startGame(double dice1, double dice2);

}
