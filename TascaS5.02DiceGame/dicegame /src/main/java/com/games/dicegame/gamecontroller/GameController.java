package com.games.dicegame.gamecontroller;


import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.service.GameService;
import com.games.dicegame.model.service.UserService;
import com.games.dicegame.model.util.AppUserInfo;
import com.games.dicegame.model.util.AppUserShowInfo;
import com.games.dicegame.model.util.RoleToUserForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {

    private UserService userService;

    private GameService gameService;

    public GameController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @PostMapping("/players")
    public ResponseEntity<AppUserDto> createUser(@RequestBody AppUserInfo appUserInfo){

        ResponseEntity<AppUserDto> response;
        AppUserDto appUserDto;
        try{

            appUserDto = userService.saveUser(appUserInfo);

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
        AppUserDto appUserDto;

        try {
            appUserDto = userService.updateUser(id, appUserInfo);
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
        Game game;
        AppUserDto appUserDto;

        try {
            appUserDto = userService.findUserById(id);
            if(appUserDto != null){
                game = gameService.play(id);
                appUserDto.getGames().add(game);
                userService.updateUserGames(appUserDto);
                response = new ResponseEntity<>(game ,HttpStatus.OK);
            }else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<HttpStatus> deleteGames(@PathVariable("id") Integer id){

        ResponseEntity<HttpStatus> response;
        AppUserDto appUserDto;

        try {
            appUserDto = userService.findUserById(id);
            if(appUserDto != null){
                gameService.deleteGames(id);
                appUserDto.getGames().clear();
                userService.updateUserGames(appUserDto);
                response = new ResponseEntity<>( HttpStatus.NO_CONTENT);
            }else {
                response = new ResponseEntity<>( HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping("/players")
    public ResponseEntity<List<AppUserDto>> getUsers(){

        ResponseEntity<List<AppUserDto>> response;
        List<AppUserDto> users;
        try {
            users = userService.getUsers();
            response = new ResponseEntity<>(users, HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping("/players/{id}/games")
    public ResponseEntity<Collection<Game>> getGames(@PathVariable("id") Integer id){

        ResponseEntity<Collection<Game>> response;
        try {
            Collection<Game> games = gameService.getGames(id);
            if(games == null){
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                response = new ResponseEntity<>(games, HttpStatus.OK);
            }
        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping("/players/ranking")
    public ResponseEntity<List<AppUserShowInfo>> getRanking(){
        ResponseEntity<List<AppUserShowInfo>> response;
        List<AppUserDto> users;
        List<AppUserShowInfo> usersShowInfo = new ArrayList<>();
        try {
            users = userService.getRanking();
            users.forEach(appUserDto -> usersShowInfo.add(new AppUserShowInfo(appUserDto)));
            response = new ResponseEntity<>(usersShowInfo, HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping("/players/ranking/loser")
    public ResponseEntity<AppUserShowInfo> getLoser(){

        ResponseEntity<AppUserShowInfo> response;
        AppUserDto user;
        AppUserShowInfo appUserShowInfo;
        try {
            user = userService.getLoser();
            appUserShowInfo = new AppUserShowInfo(user);
            response = new ResponseEntity<>(appUserShowInfo, HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping("/players/ranking/winner")
    public ResponseEntity<AppUserShowInfo> getWinner(){

        ResponseEntity<AppUserShowInfo> response;
        AppUserDto user;
        AppUserShowInfo appUserShowInfo;
        try {
            user = userService.getWinner();
            appUserShowInfo = new AppUserShowInfo(user);
            response = new ResponseEntity<>(appUserShowInfo, HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

}
