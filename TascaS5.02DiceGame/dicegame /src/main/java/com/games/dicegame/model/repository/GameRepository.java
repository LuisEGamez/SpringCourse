package com.games.dicegame.model.repository;

import com.games.dicegame.model.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {


}
