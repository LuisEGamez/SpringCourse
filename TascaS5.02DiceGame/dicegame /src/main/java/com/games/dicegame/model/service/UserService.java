package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.domain.Role;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.util.AppUserInfo;
import com.games.dicegame.model.util.AppUserShowInfo;

import java.util.Collection;
import java.util.List;

public interface UserService {

    AppUserDto saveUser(AppUserInfo appUserInfo);

    AppUserDto findUserById(Integer id);

    AppUserDto appUserToAppUserDto(AppUser appUser);

    AppUser appUserDtoToAppUser(AppUserDto appUserDto);

    AppUserShowInfo appUserDtoToAppUserInfo(AppUserDto appUserDto);

    void addRoleToUser(String email, String roleName);

    List<AppUserDto> getUsers();

    boolean updateUser(Integer id, AppUserInfo appUserInfo);

    List<AppUserDto> getRanking();

    AppUserDto getLoser();

    AppUserDto getWinner();

    void updateUserGames(AppUserDto appUserDto);
}
