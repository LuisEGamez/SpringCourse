package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.Game;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface GameService {

    Game play(Integer id);

    void deleteGames(Integer id);

    Collection<Game> getGames(Integer id);

    Game startGame();

}
