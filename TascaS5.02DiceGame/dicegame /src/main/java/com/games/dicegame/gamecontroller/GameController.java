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
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    private UserService userService;

    @PostMapping("/players")
    public ResponseEntity<AppUserDto> createUser(@RequestBody AppUserInfo appUserInfo){

        ResponseEntity<AppUserDto> response;

        try{

            AppUserDto appUserDto = userService.saveUser(appUserInfo);

            if(appUserDto.getId()==null){
                response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }else {
                response = new ResponseEntity<>(appUserDto, HttpStatus.CREATED);
            }
        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

        try {
            AppUserDto appUserDto = userService.updateUser(id, appUserInfo);
            if(appUserDto == null){

                response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }else {

                response = new ResponseEntity<>(appUserDto, HttpStatus.OK);
            }
        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @PostMapping("/players/{id}/games")
    public ResponseEntity<Game> play (@PathVariable("id") Integer id){

        ResponseEntity<Game> response;
        try {
            Game game = userService.play(id);
            if(game == null){
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                response = new ResponseEntity<>(game ,HttpStatus.OK);
            }
        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
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

    @GetMapping("/players/ranking/loser")
    public ResponseEntity<AppUserDto> getLoser(){

        AppUserDto users = userService.getLoser();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/players/ranking/winner")
    public ResponseEntity<AppUserDto> getWinner(){

        AppUserDto users = userService.getWinner();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
