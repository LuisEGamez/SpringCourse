package com.games.dicegame.model.util;

import com.games.dicegame.model.dto.AppUserDto;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppUserInfo {
    private String email;
    private String password;
    private String username;
}
