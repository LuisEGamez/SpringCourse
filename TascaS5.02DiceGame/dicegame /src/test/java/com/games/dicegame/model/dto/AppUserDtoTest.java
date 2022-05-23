package com.games.dicegame.model.dto;

import com.games.dicegame.model.domain.Game;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AppUserDtoTest {

    @Test
    void whenGiveFormatToUsernameThenReturnUsernameUpperCase(){

        AppUserDto appUserDto = new AppUserDto("luis@gmail.com", "123456", "luis");

        assertThat(appUserDto.getUsername()).isEqualTo("LUIS");

    }

    @Test
    void whenGiveFormatToUsernameNullThenReturnANONYMOUS(){

        AppUserDto appUserDto = new AppUserDto("luis@gmail.com", "123456", null);

        assertThat(appUserDto.getUsername()).isEqualTo("ANONYMOUS");

    }

    @Test
    void whenUpdateRateThenUpdateSuccessRateFromUserDto(){

        AppUserDto appUserDto = new AppUserDto("luis@gmail.com", "123456", "luis");
        Game game1 = new Game(3,4,7,"WIN");
        Game game2 = new Game(3,3,6,"LOSE");
        appUserDto.getGames().add(game1);
        appUserDto.getGames().add(game2);
        appUserDto.updateRate(appUserDto.getGames());

        assertThat(appUserDto.getSuccessRate()).isEqualTo(50);

    }

    @Test
    void whenUpdateRateWithArrayEmptyThenUpdateSuccessRateFromUserDto(){

        AppUserDto appUserDto = new AppUserDto("luis@gmail.com", "123456", "luis");
        assertThat(appUserDto.getSuccessRate()).isEqualTo(0);

    }

}