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
import com.games.dicegame.model.util.AppUserShowInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@AllArgsConstructor
public class UserServiceImp implements UserService, UserDetailsService {
    private AppUserRepository appUserRepository;


    private RoleService roleService;

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

    public AppUserDto findUserById(Integer id){

        Optional<AppUser> appUserData;
        AppUser appUser;
        AppUserDto appUserDto = null;
        appUserData = appUserRepository.findById(id);

        if(appUserData.isPresent()){
            appUser =  appUserData.get();
            appUserDto = new AppUserDto(appUser);
        }
        return appUserDto;
    }

    @Override
    public AppUserDto saveUser(AppUserInfo appUserInfo) {

        AppUser appUser;
        AppUserDto appUserDto = new AppUserDto(appUserInfo.getEmail(), appUserInfo.getPassword(), appUserInfo.getUsername());

        if(!appUserRepository.existsByEmail(appUserDto.getEmail())){

            if(appUserInfo.getUsername() == null || (!appUserRepository.existsByUsername(appUserDto.getUsername())) ){

                appUser = appUserRepository.save(new AppUser(appUserDto.getEmail(),
                            passwordEncoder.encode(appUserDto.getPassword()),
                            appUserDto.getUsername(),
                            appUserDto.getRegistrationDate()));

                appUserDto.setId(appUser.getId());
            }

        }
        return appUserDto;
    }

    @Override
    public void addRoleToUser(String email, String roleName) {

        log.info("Adding role {} to user {}", roleName, email);

        AppUser appUser = appUserRepository.findByEmail(email);
        Role role = roleService.findRoleByName(roleName);
        appUser.getRoles().add(role);
        appUserRepository.save(appUser);

    }

    @Override
    public boolean updateUser(Integer id, AppUserInfo appUserInfo) {

        boolean updated = false;

        if(appUserInfo.getUsername()!= null && !appUserRepository.existsByUsername(appUserInfo.getUsername())){
            appUserRepository.updateUser(appUserInfo.getUsername().toUpperCase(), id);
            updated = true;
        }

        return updated;
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

    @Override
    public void updateUserGames(AppUserDto appUserDto) {

        appUserDto.updateRate(appUserDto.getGames());
        AppUser appUser = new AppUser(appUserDto);
        appUserRepository.save(appUser);
    }

}
