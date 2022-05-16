package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.repository.AppUserRepository;
import com.games.dicegame.model.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class GameServiceImp implements GameService{

    AppUserRepository appUserRepository;
    GameRepository gameRepository;


    @Override
    public Game play(Integer id) {
        Game game = null;

        Optional<AppUser> appUserData = appUserRepository.findById(id);

        if (appUserData.isPresent()){

            game = gameRepository.save(startGame(randomNumber(), randomNumber()));
            updateUserGame(game, id);
        }

        return game;
    }

    public void updateUserGame(Game game, Integer id){
        AppUser appUser;
        AppUserDto appUserDto ;
        Optional<AppUser> appUserData = appUserRepository.findById(id);
        if (appUserData.isPresent()){
            appUser = appUserData.get();
            appUser.getGames().add(game);
            appUserDto = new AppUserDto(appUser);
            appUser.setSuccessRate(appUserDto.getSuccessRate());
            appUserRepository.save(appUser);

        }

    }

    public Game startGame(double dice1, double dice2) {

        double total;
        String result;
        total = dice1 + dice2;
        if( total == 7){
            result = "WIN";
        }else {
            result = "LOSE";
        }
        return new Game(dice1, dice2, total, result);
    }

    private double randomNumber(){

        return Math.floor(Math.random()*(6-1+1)+1);
    }

    @Override
    public void deleteGames(Integer id) {
        AppUser appUser;
        AppUserDto appUserDto;
        gameRepository.deleteByIdPlayer(id);
        Optional<AppUser> appUserData = appUserRepository.findById(id);

        if(appUserData.isPresent()){

            appUser = appUserData.get();
            appUserDto = new AppUserDto(appUser);
            appUser.setSuccessRate(appUserDto.getSuccessRate());


        }
    }

    @Override
    public Collection<Game> getGames(Integer id) {
        AppUser appUser;
        Optional<AppUser> appUserData = appUserRepository.findById(id);
        Collection<Game> games = null;

        if(appUserData.isPresent()){

            appUser = appUserData.get();
            games = appUser.getGames();
        }

        return games;
    }


}
