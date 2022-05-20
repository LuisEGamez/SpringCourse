package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Role;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.repository.AppUserRepository;
import com.games.dicegame.model.util.AppUserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {


    @Mock AppUserRepository appUserRepository;

    @Mock RoleService roleService;

    @Mock PasswordEncoder passwordEncoder;

    UserServiceImp userService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        userService = new UserServiceImp(appUserRepository, roleService, passwordEncoder);

    }

    @Test
    public void whenFindUserByIdThenReturnAppUserDto(){

        Optional<AppUser> appUser = Optional.of(new AppUser());
        AppUserDto expected = new AppUserDto(appUser.get());

        when(appUserRepository.findById(1)).thenReturn(appUser);


        AppUserDto appUserDto = userService.findUserById(1);

        assertEquals(expected, appUserDto);

    }

    @Test
    public void whenFindUserByIdReturnNullThenUserDtoIsNull(){

        Optional<AppUser> appUser = Optional.empty();
        AppUserDto expected = null;

        when(appUserRepository.findById(1)).thenReturn(appUser);

        AppUserDto appUserDto = userService.findUserById(1);

        assertEquals(expected, appUserDto);

    }

    @Test
    public void whenLoadUserByUsernameDoesNotFindUserThenThrowException(){

        when(appUserRepository.findByEmail("empty")).thenReturn(null);

        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("empty")
        );

        assertTrue(thrown.getMessage().contains("User not found in the database"));

    }

    @Test
    public void whenSaveUserWithKnownEmailThenReturnSameUserDtoWithoutId(){
        //given
        AppUserInfo appUserInfo = new AppUserInfo("luis@gmail.com", "123456", "luis");
        AppUserDto appUserDto = new AppUserDto(appUserInfo.getEmail(), appUserInfo.getPassword(), appUserInfo.getUsername());
        appUserDto.setId(1);

        //when
        when(appUserRepository.existsByEmail(appUserDto.getEmail())).thenReturn(true);

        //act
        AppUserDto expected = userService.saveUser(appUserInfo);

        //then
        assertThat(expected.getId()).isNull();

    }

    @Test
    public void whenSaveUserWithUnknownEmailAndUsernameThenReturnUserDtoWithId(){
        //given
        AppUserInfo appUserInfo = new AppUserInfo("luis@gmail.com", "123456", "luis");
        AppUserDto appUserDto = new AppUserDto(appUserInfo.getEmail(), appUserInfo.getPassword(), appUserInfo.getUsername());
        appUserDto.setId(1);

        //when
        when(appUserRepository.existsByEmail(appUserDto.getEmail())).thenReturn(false);
        when(appUserRepository.existsByUsername(appUserDto.getUsername())).thenReturn(false);
        when(appUserRepository.save(any())).thenReturn(new AppUser(appUserDto));

        //act
        AppUserDto expected = userService.saveUser(appUserInfo);

        //then
        assertThat(expected.getId()).isEqualTo(1);

    }

    @Test
    public void whenSaveUserWithoutUsernameThenReturnUserDtoWithId(){
        //given
        AppUserInfo appUserInfo = new AppUserInfo("luis@gmail.com", "123456", null);
        AppUserDto appUserDto = new AppUserDto(appUserInfo.getEmail(), appUserInfo.getPassword(), appUserInfo.getUsername());
        appUserDto.setId(1);

        //when
        when(appUserRepository.existsByEmail(appUserDto.getEmail())).thenReturn(false);
        when(appUserRepository.save(any())).thenReturn(new AppUser(appUserDto));

        //act
        AppUserDto expected = userService.saveUser(appUserInfo);

        //then
        assertThat(expected.getId()).isEqualTo(1);

    }

    @Test
    public void whenAddRoleToUserVerifySaveUser(){

        String email = "luis@gmail.com";
        String rolName = "ROLE_USER";

        when(appUserRepository.findByEmail(email)).thenReturn(new AppUser());
        when(roleService.findRoleByName(rolName)).thenReturn(new Role());

        userService.addRoleToUser(email, rolName);


        verify(appUserRepository).save(any());
    }

    @Test
    public void whenUpdateUserThemReturnTrue(){

        //given
        AppUserInfo appUserInfo = new AppUserInfo("luis@gmail.com", "123456", "luis");

        //when
        when(appUserRepository.existsByUsername(appUserInfo.getUsername())).thenReturn(false);

        //act
        boolean expected = userService.updateUser(1, appUserInfo);

        //then
        assertThat(expected).isTrue();


    }

    @Test
    public void whenUpdateUserWithoutUsernameThemNotUpdate(){

        //given
        AppUserInfo appUserInfo = new AppUserInfo("luis@gmail.com", "123456", null);


        //act
        boolean expected = userService.updateUser(1, appUserInfo);

        //then
        assertThat(expected).isFalse();

    }

    @Test
    public void whenUpdateUserWithKnowUsernameThemNotUpdate(){

        //given
        AppUserInfo appUserInfo = new AppUserInfo("luis@gmail.com", "123456", "luis");

        //when
        when(appUserRepository.existsByUsername(appUserInfo.getUsername())).thenReturn(true);

        //act
        boolean expected = userService.updateUser(1, appUserInfo);

        //then
        assertThat(expected).isFalse();

    }

    @Test
    public void whenGetUsersThemReturnAList(){

        //act
        userService.getUsers();

        //then
        verify(appUserRepository).findAll();

    }

    @Test
    public void whenGetRankingThemReturnAList(){

        //act
        userService.getRanking();

        //then
        verify(appUserRepository).findAllByOrderBySuccessRateDesc();

    }

}