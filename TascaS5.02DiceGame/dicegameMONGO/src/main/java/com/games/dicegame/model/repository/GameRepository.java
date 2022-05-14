package com.games.dicegame.model.repository;

import com.games.dicegame.model.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GameRepository extends JpaRepository<Game, Integer> {


    @Modifying
    @Query("DELETE FROM Game WHERE user_id = ?1")
    void deleteByIdPlayer(Integer id);
}
