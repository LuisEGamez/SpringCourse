package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.repository.AppUserRepository;
import com.games.dicegame.model.security.UserDetailsCustom;
import com.games.dicegame.model.util.AppUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImp implements UserService, UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // We need encoder the password before to save in the database.

    /*
     *
     * With this method override from UserDetailsService we create a UserDetails,
     * a valid user for Spring Security. We have to dump the information inside it
     *
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUser appUser = appUserRepository.findByEmail(email);
        if(appUser == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else {
            log.info("User found in the database {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role)); // How Spring Security doesn't know to handle roles
                                                                            // We need convert this roles in SimpleGrantedAuthority that extend of GrantedAuthorities
        });

        return new UserDetailsCustom(appUser.getId(), appUser.getEmail(), appUser.getPassword(), authorities); // Return a valid user for Spring Security
    }

    @Override
    public AppUserDto saveUser(AppUserInfo appUserInfo) {

        AppUser appUser;
        AppUserDto appUserDto = new AppUserDto(appUserInfo.getEmail(), appUserInfo.getPassword(), appUserInfo.getUsername());

        if(!appUserRepository.existsByEmail(appUserDto.getEmail())){

            if((!appUserDto.getUsername().equalsIgnoreCase("ANONYMOUS")) && (!appUserRepository.existsByUsername(appUserDto.getUsername())) ){

                appUser = appUserRepository.save(new AppUser(appUserDto.getEmail(),
                        passwordEncoder.encode(appUserDto.getPassword()),
                        appUserDto.getUsername(),
                        appUserDto.getRegistrationDate()));

                addRoleToUser(appUser.getEmail(), "ROLE_USER");
                appUserDto = new AppUserDto(appUser.getId(), appUser.getEmail(), appUser.getPassword(),
                        appUser.getUsername(), appUser.getRegistrationDate(),appUser.getRoles());

            } else if ( (appUserDto.getUsername().equalsIgnoreCase("ANONYMOUS"))) {
                appUser = appUserRepository.save(new AppUser(appUserDto.getEmail(),
                        passwordEncoder.encode(appUserDto.getPassword()),
                        appUserDto.getUsername(),
                        appUserDto.getRegistrationDate()));
                addRoleToUser(appUser.getEmail(), "ROLE_USER");
                appUserDto = new AppUserDto(appUser.getId(), appUser.getEmail(), appUser.getPassword(),
                        appUser.getUsername(), appUser.getRegistrationDate(), appUser.getRoles());
            }

        }


        return appUserDto;
    }


    @Override
    public void addRoleToUser(String email, String roleName) {

        log.info("Adding role {} to user {}", roleName, email);

        AppUser appUser = appUserRepository.findByEmail(email);
        appUser.getRoles().add(roleName);
        appUserRepository.save(appUser);

    }

    @Override
    public AppUserDto updateUser(String id, AppUserInfo appUserInfo) {

        AppUserDto appUserDto = null;
        Optional<AppUser> appUserData;
        AppUser appUser;

        if(appUserRepository.existsById(id) && appUserInfo.getUsername()!= null){

            appUserDto = new AppUserDto(null, null, appUserInfo.getUsername());

            appUserData =  appUserRepository.findById(id);

            if(appUserData.isPresent()){
                appUser = appUserData.get();
                appUser.setUsername(appUserDto.getUsername());
                appUserRepository.save(appUser);
            }
        }

        return appUserDto;
    }


    @Override
    public Game play(String id) {

        Game game = null;
        AppUser appUser = null;
        AppUserDto appUserDto = null;
        Optional<AppUser> appUserData = appUserRepository.findById(id);

        if (appUserData.isPresent()){

            game = new Game();
            appUser = appUserData.get();
            appUser.getGames().add(game);
            appUserDto = new AppUserDto(appUser);
            appUser.setSuccessRate(appUserDto.getSuccessRate());
            appUserRepository.save(appUser);
        }

        return game;
    }

    @Override
    public void deleteGames(String id) {

        Optional<AppUser> appUserData = appUserRepository.findById(id);
        AppUser appUser;
        AppUserDto appUserDto = null;
        Collection<Game> games;

        if (appUserData.isPresent()){
            appUser = appUserData.get();
            games = appUser.getGames();
            games.clear();
            appUser.setGames(games);
            appUserDto = new AppUserDto(appUser);
            appUser.setSuccessRate(appUserDto.getSuccessRate());
            appUserRepository.save(appUser);
        }

    }

    @Override
    public List<AppUserDto> getUsers() {

        List<AppUser> users;
        List<AppUserDto> usersDto = new ArrayList<>();

        users = appUserRepository.findAll();

        for(AppUser appUser: users){
            usersDto.add(new AppUserDto(appUser));
        }
        return usersDto;
    }

    @Override
    public Collection<Game> getGames(String id) {

        AppUser appUser;
        Optional<AppUser> appUserData = appUserRepository.findById(id);
        Collection<Game> games = null;

        if(appUserData.isPresent()){

            appUser = appUserData.get();
            games = appUser.getGames();
        }

        return games;
    }

    @Override
    public List<AppUserDto> getRanking() {
        List<AppUser> users;
        List<AppUserDto> usersDto = new ArrayList<>();

        users = appUserRepository.findAllByOrderBySuccessRateDesc();

        for(AppUser appUser: users){
            usersDto.add(new AppUserDto(appUser));
        }
        return usersDto;
    }

    @Override
    public AppUserDto getLoser() {
        AppUser appUser = appUserRepository.findFirstByOrderBySuccessRateAsc();
        return new AppUserDto(appUser);
    }

    @Override
    public AppUserDto getWinner() {
        AppUser appUser = appUserRepository.findFirstByOrderBySuccessRateDesc();
        return new AppUserDto(appUser);
    }

}
