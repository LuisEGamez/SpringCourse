package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.domain.Role;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.repository.AppUserRepository;
import com.games.dicegame.model.repository.GameRepository;
import com.games.dicegame.model.repository.RoleRepository;
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
    private RoleRepository roleRepository;

    @Autowired
    private GameRepository gameRepository;

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
            authorities.add(new SimpleGrantedAuthority(role.getName())); // How Spring Security doesn't know to handle roles
                                                                            // We need convert this roles in SimpleGrantedAuthority that extend of GrantedAuthorities
        });

        return new UserDetailsCustom(appUser.getId(), appUser.getEmail(), appUser.getPassword(), authorities); // Return a valid user for Spring Security
    }

    @Override
    public AppUserDto saveUser(AppUserInfo appUserInfo) {

        AppUser appUser;
        AppUserDto appUserDto = new AppUserDto(appUserInfo.getEmail(), appUserInfo.getPassword(), appUserInfo.getUsername());


            if(( !appUserDto.getUsername().equalsIgnoreCase("ANONYMOUS") )&&
                    (!appUserRepository.existsByUsername(appUserDto.getUsername())) &&
                        (!appUserRepository.existsByEmail(appUserDto.getEmail())) ){

                appUser = appUserRepository.save(new AppUser(appUserDto.getEmail(),
                                                                        passwordEncoder.encode(appUserDto.getPassword()),
                                                                        appUserDto.getUsername(),
                                                                        appUserDto.getRegistrationDate()));

                addRoleToUser(appUser.getEmail(), "ROLE_USER");
                appUserDto = new AppUserDto(appUser.getId(), appUser.getEmail(), appUser.getPassword(),
                                                appUser.getUsername(), appUser.getRegistrationDate(),appUser.getRoles());

            } else if ( (appUserDto.getUsername().equalsIgnoreCase("ANONYMOUS")) && (!appUserRepository.existsByEmail(appUserDto.getEmail())) ) {
                appUser = appUserRepository.save(new AppUser(appUserDto.getEmail(),
                                                                        passwordEncoder.encode(appUserDto.getPassword()),
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
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {

        log.info("Adding role {} to user {}", roleName, email);

        AppUser appUser = appUserRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        appUser.getRoles().add(role);

    }

    @Override
    public AppUserDto updateUser(AppUserInfo appUserInfo) {

        AppUserDto appUserDto = new AppUserDto(appUserInfo.getEmail(), null, appUserInfo.getUsername());
        AppUser appUser = null;

        if(appUserRepository.existsByEmail(appUserDto.getEmail())){

            appUserRepository.updateUser(appUserDto.getUsername(), appUserDto.getEmail());

            appUser = appUserRepository.findByEmail(appUserDto.getEmail());

            appUserDto.setUsername(appUser.getUsername());

        }

        return appUserDto;
    }


    @Override
    public Game play(Integer id) {

        Game game = null;
        AppUser appUser = null;
        AppUserDto appUserDto = null;
        Optional<AppUser> appUserData = appUserRepository.findById(id);

        if (appUserData.isPresent()){

            game = gameRepository.save(new Game());
            appUser = appUserData.get();
            appUser.getGames().add(game);
            appUserDto = new AppUserDto(appUser);
            appUser.setSuccessRate(appUserDto.getSuccessRate());
        }

        return game;
    }

    @Override
    public void deleteGames(Integer id) {

        gameRepository.deleteByIdPlayer(id);
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

}
