package com.games.dicegame.model.repository;


import com.games.dicegame.model.domain.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AppUserRepositoryTest {


    @Autowired
    private AppUserRepository appUserRepository;



    @Test
    void itShouldCheckIfExistsByUsername() {
        //give
        String username = "luis";
        AppUser appUser = new AppUser("luis@gmail.com", "123456", username, null);
        appUserRepository.save(appUser);
        //when
        boolean expected = appUserRepository.existsByUsername(username);
        //then
        assertThat(expected).isTrue();

    }

}