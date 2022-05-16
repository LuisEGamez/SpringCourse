package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.domain.Role;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.util.AppUserInfo;

import java.util.Collection;
import java.util.List;

public interface UserService {

    AppUserDto saveUser(AppUserInfo appUserInfo);

    Role saveRole(Role role);

    void addRoleToUser(String email, String roleName);

    List<AppUserDto> getUsers();

    AppUserDto updateUser(Integer id, AppUserInfo appUserInfo);

    List<AppUserDto> getRanking();

    AppUserDto getLoser();

    AppUserDto getWinner();
}
