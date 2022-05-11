package com.games.dicegame.gamecontroller;


import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.service.UserService;
import com.games.dicegame.model.util.AppUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    private UserService userService;

    @PostMapping("/players")
    public ResponseEntity<AppUserDto> createUser(@RequestBody AppUserInfo appUserInfo){

        ResponseEntity<AppUserDto> response;
        AppUserDto appUserDto = userService.saveUser(appUserInfo);

        if(appUserDto.getId()==null){
            response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }else {
            response = new ResponseEntity<>(appUserDto, HttpStatus.CREATED);
        }

        return response;
    }


}
