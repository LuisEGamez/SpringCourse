package com.games.dicegame.model.repository;


import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.dto.AppUserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AppUserRepositoryTest {


    @Autowired
    private AppUserRepository appUserRepository;


    @AfterEach
    void tearDown() {
        appUserRepository.deleteAll();
    }

    @Test
    void itShouldReturnNullIfNotExistsByEmail() {
        //give
        String email = "luis@gmail.com";
        //when
        AppUser expected = appUserRepository.findByEmail(email);
        //then
        assertThat(expected).isNull();

    }

    @Test
    void itShouldReturnAppUserIfExistsByEmail() {
        //give
        String email = "luis@gmail.com";
        AppUser appUser = new AppUser(email, "123456", "luis", null);
        appUserRepository.save(appUser);
        //when
        AppUser expected = appUserRepository.findByEmail(email);
        //then
        assertThat(expected.getId()).isEqualTo(1);

    }

    @Test
    void itShouldCheckIfNotExistsByEmail() {
        //give
        String email = "luis@gmail.com";
        //when
        boolean expected = appUserRepository.existsByEmail(email);
        //then
        assertThat(expected).isFalse();

    }

    @Test
    void itShouldCheckIfExistsByEmail() {
        //give
        AppUser appUser = new AppUser("luis@gmail.com", "123456", "luis", null);
        appUserRepository.save(appUser);
        //when
        boolean expected = appUserRepository.existsByEmail("luis@gmail.com");
        //then
        assertThat(expected).isTrue();

    }

    @Test
    void itShouldCheckIfNotExistsByUsername() {
        //give
        String username = "luis";
        //when
        boolean expected = appUserRepository.existsByUsername(username);
        //then
        assertThat(expected).isFalse();

    }

    @Test
    void itShouldCheckIfExistsByUsername() {
        //give
        AppUser appUser = new AppUser("luis@gmail.com", "123456", "luis", null);
        appUserRepository.save(appUser);
        //when
        boolean expected = appUserRepository.existsByUsername("luis");
        //then
        assertThat(expected).isTrue();

    }

    @Test // I think that it should pass,
    void itShouldUpdateUsernameById() {
        //give
        AppUser appUser = new AppUser("luis@gmail.com", "123456", "luis", null);
        appUserRepository.save(appUser);

        //when
        System.out.println(appUser.getId());
        appUserRepository.updateUser("pepe", 1);
        AppUser expected = appUserRepository.findByEmail("luis@gmail.com");

        //then
        assertThat(expected.getUsername()).isEqualTo("pepe");

    }

    @Test
    void itShouldFindAllUsernameOrderBySuccessRateDesc() {
        //give
        AppUserDto appUserDto = new AppUserDto("luis@gmail.com", "123456", "luis");
        AppUserDto appUserDto1 = new AppUserDto("juan@gmail.com", "123456", "juan");
        AppUser appUser = new AppUser(appUserDto);
        AppUser appUser2 = new AppUser(appUserDto1);
        appUser.setSuccessRate(34);
        appUser2.setSuccessRate(24);
        appUserRepository.save(appUser);
        appUserRepository.save(appUser2);

        //when

        List<AppUser> list = appUserRepository.findAllByOrderBySuccessRateDesc();

        //then
        assertThat(list.get(0).getSuccessRate()).isEqualTo(34);

    }

    @Test
    void itShouldFindFirstByOrderBySuccessRateAsc() {
        //give
        AppUserDto appUserDto = new AppUserDto("luis@gmail.com", "123456", "luis");
        AppUserDto appUserDto1 = new AppUserDto("juan@gmail.com", "123456", "juan");
        AppUser appUser = new AppUser(appUserDto);
        AppUser appUser2 = new AppUser(appUserDto1);
        appUser.setSuccessRate(34);
        appUser2.setSuccessRate(24);
        appUserRepository.save(appUser);
        appUserRepository.save(appUser2);

        //when

        AppUser expected = appUserRepository.findFirstByOrderBySuccessRateAsc();

        //then
        assertThat(expected.getSuccessRate()).isEqualTo(24);

    }

    @Test
    void itShouldFindFirstByOrderBySuccessRateDesc() {
        //give
        AppUserDto appUserDto = new AppUserDto("luis@gmail.com", "123456", "luis");
        AppUserDto appUserDto1 = new AppUserDto("juan@gmail.com", "123456", "juan");
        AppUser appUser = new AppUser(appUserDto);
        AppUser appUser2 = new AppUser(appUserDto1);
        appUser.setSuccessRate(34);
        appUser2.setSuccessRate(24);
        appUserRepository.save(appUser);
        appUserRepository.save(appUser2);

        //when

        AppUser expected = appUserRepository.findFirstByOrderBySuccessRateDesc();

        //then
        assertThat(expected.getSuccessRate()).isEqualTo(34);

    }



}