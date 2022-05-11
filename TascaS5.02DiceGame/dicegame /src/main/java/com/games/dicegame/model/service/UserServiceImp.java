package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Role;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.repository.AppUserRepository;
import com.games.dicegame.model.repository.RoleRepository;
import com.games.dicegame.model.util.AppUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImp implements UserService{
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;


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


}
