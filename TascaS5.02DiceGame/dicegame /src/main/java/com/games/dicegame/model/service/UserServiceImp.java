package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.domain.Role;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.repository.AppUserRepository;
import com.games.dicegame.model.repository.GameRepository;
import com.games.dicegame.model.repository.RoleRepository;
import com.games.dicegame.model.util.AppUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImp implements UserService{
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GameRepository gameRepository;


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

                addRoleToUser(appUser.getEmail(), "ROLE_USER");
                appUserDto = new AppUserDto(appUser.getId(), appUser.getEmail(), appUser.getPassword(),
                                                appUser.getUsername(), appUser.getRegistrationDate(),appUser.getRoles());

            } else if ( (appUserDto.getUsername().equalsIgnoreCase("ANONYMOUS")) && (!appUserRepository.existsByEmail(appUserDto.getEmail())) ) {
                appUser = appUserRepository.save(new AppUser(appUserDto.getEmail(),
                                                                        appUserDto.getPassword(),
                                                                        appUserDto.getUsername(),
                                                                        appUserDto.getRegistrationDate()));
                addRoleToUser(appUser.getEmail(), "ROLE_USER");
                appUserDto = new AppUserDto(appUser.getId(), appUser.getEmail(), appUser.getPassword(),
                                                appUser.getUsername(), appUser.getRegistrationDate(), appUser.getRoles());
            }

        return appUserDto;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {

        AppUser appUser = appUserRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        appUser.getRoles().add(role);

    }

    @Override
    public Game play(Integer id) {

        Game game = null;
        AppUser appUser = null;
        Optional<AppUser> appUserData = appUserRepository.findById(id);

        if (appUserData.isPresent()){

            game = gameRepository.save(new Game());

            appUser = appUserData.get();

            appUser.getGames().add(game);


        }


        return game;
    }


}
