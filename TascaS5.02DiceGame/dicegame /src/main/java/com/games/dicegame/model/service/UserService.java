package com.games.dicegame.model.service;

import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.util.AppUserInfo;

public interface UserService {


    AppUserDto saveUser(AppUserInfo appUserInfo);
}
