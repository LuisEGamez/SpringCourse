package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.repository.AppUserRepository;
import com.games.dicegame.model.util.AppUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public AppUserDto saveUser(AppUserInfo appUserInfo) {

        AppUser appUser;
        AppUserDto appUserDto = new AppUserDto(appUserInfo.getEmail(), appUserInfo.getPassword(), appUserInfo.getUsername());


            if(( !appUserDto.getUsername().equalsIgnoreCase("ANONYMOUS") )&&
                    (!appUserRepository.existsByUsername(appUserDto.getUsername())) &&
                        (!appUserRepository.existsByEmail(appUserDto.getEmail())) ){

                appUser = appUserRepository.save(new AppUser(appUserDto.getEmail(),
                        appUserDto.getPassword(),
                        appUserDto.getUsername(),
                        appUserDto.getRegistrationDate()));
                appUserDto = new AppUserDto(appUser.getId(), appUser.getEmail(), appUser.getPassword(), appUser.getUsername(), appUser.getRegistrationDate());

            } else if ( (appUserDto.getUsername().equalsIgnoreCase("ANONYMOUS")) && (!appUserRepository.existsByEmail(appUserDto.getEmail())) ) {
                appUser = appUserRepository.save(new AppUser(appUserDto.getEmail(),
                        appUserDto.getPassword(),
                        appUserDto.getUsername(),
                        appUserDto.getRegistrationDate()));
                appUserDto = new AppUserDto(appUser.getId(), appUser.getEmail(), appUser.getPassword(), appUser.getUsername(), appUser.getRegistrationDate());
            }

        return appUserDto;
    }
}
