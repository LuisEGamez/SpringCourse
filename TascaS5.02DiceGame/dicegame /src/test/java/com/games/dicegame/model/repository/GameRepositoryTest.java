package com.games.dicegame.model.repository;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.dto.AppUserDto;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ContextConfiguration(classes = {GameRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.games.dicegame.model.domain"})
@DataJpaTest
class GameRepositoryTest {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    GameRepository gameRepository;

    @Test
    @Disabled
    void itShouldDeleteAllGamesFromUserById() {
        //give
        AppUserDto appUserDto = new AppUserDto("luis@gmail.com", "123456", "luis");
        AppUser appUser = new AppUser(appUserDto);
        Game game = new Game(3, 4, 7, "WIN");
        appUser.getGames().add(game);
        appUserRepository.save(appUser);
        gameRepository.save(game);

        //when

        gameRepository.deleteByIdPlayer(1);
        Game expected = gameRepository.findById(1).get();

        //then
        assertThat(expected).isNull();

    }


}