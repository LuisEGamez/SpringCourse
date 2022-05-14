package com.games.dicegame.model.util;

import com.games.dicegame.model.dto.AppUserDto;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppUserShowInfo {
    private String email;
    private String username;
    private double successRate;

    public AppUserShowInfo(AppUserDto appUserDto) {
        email = appUserDto.getEmail();
        username = appUserDto.getUsername();
        successRate = appUserDto.getSuccessRate();
    }
}
