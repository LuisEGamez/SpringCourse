package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.util.AppUserInfo;

import java.util.Collection;
import java.util.List;

public interface UserService {


    AppUserDto saveUser(AppUserInfo appUserInfo);

    void addRoleToUser(String email, String roleName);

    Game play(String id);

    void deleteGames(String id);

    List<AppUserDto> getUsers();

    AppUserDto updateUser(String id, AppUserInfo appUserInfo);

    Collection<Game> getGames(String id);

    List<AppUserDto> getRanking();

    AppUserDto getLoser();

    AppUserDto getWinner();
}
