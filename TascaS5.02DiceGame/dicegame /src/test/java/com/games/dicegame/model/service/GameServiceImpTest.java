package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.repository.AppUserRepository;
import com.games.dicegame.model.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
class GameServiceImpTest {

    @Mock AppUserRepository appUserRepository;
    @Mock GameRepository gameRepository;

    GameService gameService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        gameService = new GameServiceImp(appUserRepository, gameRepository);
    }

    @Test
    public void whenDiceIsEqualToSevenThemWinGame(){
        Game game = gameService.startGame(4,3);
        assertEquals("WIN", game.getResult());
    }

    @Test
    public void whenDiceIsNotEqualToSevenThemLoseGame(){
        Game game = gameService.startGame(3,3);
        assertEquals("LOSE", game.getResult());
    }

    @Test
    public void whenUserPlaysVerifyGameSave(){
        /*Optional<AppUser> appUser = Optional.of(new AppUser());
        when(appUserRepository.findById(1)).thenReturn(appUser);*/

        gameService.play();

        verify(gameRepository).save(any());
    }

    @Test
    public void whenUserDeleteGameVerifyDeleteGameById(){

        gameService.deleteGames(2);

        verify(gameRepository).deleteByIdPlayer(2);
    }

    @Test
    public void whenUserGetGamesThemReturnArray(){

        gameService.deleteGames(2);

        verify(gameRepository).deleteByIdPlayer(2);
    }

}