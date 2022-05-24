package com.games.dicegame.gamecontroller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.dicegame.model.domain.Game;
import com.games.dicegame.model.dto.AppUserDto;
import com.games.dicegame.model.service.GameService;
import com.games.dicegame.model.service.UserService;
import com.games.dicegame.model.util.AppUserInfo;
import com.games.dicegame.model.util.RoleToUserForm;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ContextConfiguration(classes = {GameController.class})
@ExtendWith(SpringExtension.class)
class GameControllerTest {


    private GameController gameController;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private AppUserInfo appUserInfo;

    private AppUserDto appUserDto;

    private Game game;

    private ArrayList<AppUserDto> appUserDtoArrayList;

    private ArrayList<Game> games;

    @BeforeEach
    void setUp() {
        gameController = new GameController(userService, gameService);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController)
                .build();
        objectMapper = new ObjectMapper();
        appUserInfo = new AppUserInfo("luis@gmail.com", "123456", "luis");
        appUserDto = new AppUserDto("luis@gmail.com", "123456", "luis");
        game = new Game(4,3,7,"WIN");
        appUserDtoArrayList = new ArrayList<>();
        games = new ArrayList<>();
    }

    @Test
    void whenCreateUserThatAlreadyExistThenResponseNotAceptable() throws Exception {
        when(userService.saveUser(any())).thenReturn(appUserDto);
        String content = objectMapper.writeValueAsString(appUserInfo);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(406))
                .andDo(print());
    }

    @Test
    void whenCreateUserReturnNullThenResponseInternalServerError() throws Exception {
        when(this.userService.saveUser(any())).thenReturn(null);
        String content = objectMapper.writeValueAsString(appUserInfo);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andDo(print());
    }

    @Test
    void whenCreateUserSuccessfullyThenUsernameIsEqualToBody() throws Exception {
        appUserDto.setId(1);
        when(userService.saveUser(any())).thenReturn(appUserDto);

        String content = objectMapper.writeValueAsString(appUserInfo);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(jsonPath("$.username").value("LUIS"))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#addRole(RoleToUserForm)}
     */
    @Test
    void whenAddRoleResponseOk() throws Exception {
        doNothing().when(userService).addRoleToUser(any(), any());

        RoleToUserForm roleToUserForm = new RoleToUserForm();
        roleToUserForm.setEmail("luis@gmail.com");
        roleToUserForm.setRoleName("ROLE_USER");
        String content = objectMapper.writeValueAsString(roleToUserForm);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/addRoles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#updateUser(Integer, AppUserInfo)}
     */
    @Test
    void checkWhenUpdateUserSuccessfullyThenUsernameIsEqualToBody() throws Exception {

        when(userService.findUserById(any())).thenReturn(appUserDto);
        when(userService.updateUser(any(),any())).thenReturn(true);

        String content = objectMapper.writeValueAsString(appUserInfo);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/players/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.username").value("LUIS"))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#updateUser(Integer, AppUserInfo)}
     */
    @Test
    void checkWhenUpdateUserThatAlreadyExistThenResponseNotAceptable() throws Exception {
        when(userService.findUserById(any())).thenReturn(appUserDto);
        when(userService.updateUser(any(),any())).thenReturn(false);

        String content = objectMapper.writeValueAsString(appUserInfo);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/players/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(406))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#updateUser(Integer, AppUserInfo)}
     */
    @Test
    void checkWhenUpdateUserThatNotExistThenResponseNotFound() throws Exception {
        when(userService.findUserById(any())).thenReturn(null);
        when(userService.updateUser(any(),any())).thenReturn(false);

        String content = objectMapper.writeValueAsString(appUserInfo);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/players/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#updateUser(Integer, AppUserInfo)}
     */
    @Test
    void checkWhenUpdateUserWithEmptyUsernameThenResponseInternalServerError() throws Exception {
        appUserInfo.setUsername(null);
        when(userService.findUserById(any())).thenReturn(appUserDto);
        when(userService.updateUser(any(),any())).thenReturn(true);

        String content = (new ObjectMapper()).writeValueAsString(appUserInfo);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/players/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#play(Integer)}
     */
    @Test
    void checkWhenPlayThenResponseTheResultOfGame() throws Exception {
        when(userService.findUserById(any())).thenReturn(appUserDto);
        doNothing().when(userService).updateUserGames(any());
        game.setId(1);
        when(gameService.play()).thenReturn(game);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/players/{id}/games", 1);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"dice1\":4.0,\"dice2\":3.0,\"total\":7.0,\"result\":\"WIN\"}"))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#play(Integer)}
     */
    @Test
    void checkWhenPlayAndNotFoundTheUserThenResponse404() throws Exception {
        when(userService.findUserById(any())).thenReturn(null);
        doNothing().when(userService).updateUserGames(any());
        when(gameService.play()).thenReturn(game);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/players/{id}/games", 1);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#play(Integer)}
     */

    @Test
    void checkWhenPlayAndThrowAnExceptionThenResponse500() throws Exception {
        doNothing().when(userService).updateUserGames(any());
        when(userService.findUserById(any())).thenThrow(NullPointerException.class);
        when(this.gameService.play()).thenReturn(game);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/players/{id}/games", 1);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#deleteGames(Integer)}
     */
    @Test
    void checkWhenDeleteGamesThenResponse204() throws Exception {
        when(userService.findUserById(any())).thenReturn(appUserDto);
        doNothing().when(userService).updateUserGames(any());
        doNothing().when(gameService).deleteGames(any());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/players/{id}/games", 1);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#deleteGames(Integer)}
     */
    @Test
    void checkWhenDeleteGamesButNotFoundUserThenResponse404() throws Exception {
        when(userService.findUserById(any())).thenReturn(null);
        doNothing().when(userService).updateUserGames(any());
        doNothing().when(this.gameService).deleteGames(any());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/players/{id}/games", 1);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#deleteGames(Integer)}
     */
    @Test
    void checkWhenDeleteGamesThrowAnExceptionThenResponse500() throws Exception {
        when(this.userService.findUserById(any())).thenThrow(NullPointerException.class);
        doNothing().when(this.userService).updateUserGames(any());
        doNothing().when(this.gameService).deleteGames(any());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/players/{id}/games", 1);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#getUsers()}
     */
    @Test
    void checkWhenGetUsersThenResponseAnArrayOfUsers() throws Exception {
        appUserDtoArrayList.add(appUserDto);
        when(userService.getUsers()).thenReturn(appUserDtoArrayList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/players");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$[0].username").value("LUIS"))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#getUsers()}
     */
    @Test
    void checkWhenGetUsersThrowAnExceptionThenResponse500() throws Exception {
        when(userService.getUsers()).thenThrow(NullPointerException.class);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/players");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#getGames(Integer)}
     */
    @Test
    void checkWhenGetGamesThenResponseAnArrayOfGames() throws Exception {
        when(userService.findUserById(any())).thenReturn(appUserDto);
        games.add(game);
        when(gameService.getGames(any())).thenReturn(games);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/players/{id}/games", 1);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$[0].result").value("WIN"))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#getGames(Integer)}
     */
    @Test
    void checkWhenGetGamesAndNotFoundUserThenResponse404() throws Exception {
        when(userService.findUserById(any())).thenReturn(null);
        when(gameService.getGames(any())).thenReturn(games);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/players/{id}/games", 1);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#getGames(Integer)}
     */
    @Test
    void checkWhenGetGamesThrowAnExceptionThenResponse500() throws Exception {
        when(userService.findUserById(any())).thenThrow(NullPointerException.class);
        when(gameService.getGames(any())).thenReturn(games);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/players/{id}/games", 1);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#getRanking()}
     */
    @Test
    void whenGetRankingThenResponseAnArrayOfUsers() throws Exception {
        appUserDtoArrayList.add(appUserDto);
        when(userService.getRanking()).thenReturn(appUserDtoArrayList);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/players/ranking");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$[0].username").value("LUIS"))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#getRanking()}
     */
    @Test
    void whenGetRankingThrowAnExceptionThenResponseAnArrayOfUsers() throws Exception {
        appUserDtoArrayList.add(null);
        when(userService.getRanking()).thenReturn(appUserDtoArrayList);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/players/ranking");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#getLoser()}
     */
    @Test
    void whenGetLoserThenResponseTheLoser() throws Exception {
        when(userService.getLoser()).thenReturn(appUserDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/players/ranking/loser");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.username").value("LUIS"))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#getLoser()}
     */
    @Test
    void whenGetLoserThrowAnExceptionThenResponseTheLoser() throws Exception {
        when(this.userService.getLoser()).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/players/ranking/loser");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#getWinner()}
     */
    @Test
    void whenGetWinnerThenResponseTheWinner() throws Exception {
        when(userService.getWinner()).thenReturn(appUserDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/players/ranking/winner");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.username").value("LUIS"))
                .andDo(print());
    }

    /**
     * Method under test: {@link GameController#getWinner()}
     */
    @Test
    void whenGetWinnerThrowAnExceptionThenResponseTheWinner() throws Exception {
        when(this.userService.getWinner()).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/players/ranking/winner");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andDo(print());
    }
}