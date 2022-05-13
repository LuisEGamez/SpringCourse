package com.games.dicegame.gamecontroller;


import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.service.UserService;
import com.games.dicegame.model.util.AppUserInfo;
import com.games.dicegame.model.util.RoleToUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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

    @PutMapping("/addRoles")
    public ResponseEntity<HttpStatus> addRole(@RequestBody RoleToUserForm form){

        userService.addRoleToUser(form.getEmail(), form.getRoleName());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<AppUserDto> updateUser(@PathVariable("id") Integer id, @RequestBody AppUserInfo appUserInfo){

        ResponseEntity<AppUserDto> response;
        AppUserDto appUserDto = userService.updateUser(appUserInfo);

        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @PostMapping("/players/{id}/games")
    public ResponseEntity<Game> play(@PathVariable("id") Integer id){

        Game game = userService.play(id);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<HttpStatus> deleteGames(@PathVariable("id") Integer id){

        userService.deleteGames(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @GetMapping("/players")
    public ResponseEntity<List<AppUserDto>> getUsers(){

        List<AppUserDto> users = userService.getUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/players/{id}/games")
    public ResponseEntity<Collection<Game>> getGames(@PathVariable("id") Integer id){

        Collection<Game> games = userService.getGames(id);

        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/players/ranking")
    public ResponseEntity<List<AppUserDto>> getRanking(){

        List<AppUserDto> users = userService.getRanking();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
