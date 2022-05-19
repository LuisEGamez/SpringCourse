package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.repository.AppUserRepository;
import com.games.dicegame.model.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {


    @Mock AppUserRepository appUserRepository;

    @Mock RoleRepository roleRepository;

    @Mock PasswordEncoder passwordEncoder;

    UserServiceImp userService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        userService = new UserServiceImp(appUserRepository, roleRepository, passwordEncoder);

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

        when(appUserRepository.findByEmail("vacio")).thenReturn(null);

        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("vacio")
        );

        assertTrue(thrown.getMessage().contains("User not found in the database"));

    }

}