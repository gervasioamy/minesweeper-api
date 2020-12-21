package com.gervasioamy.minesweeperapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gervasioamy.minesweeperapi.controller.dto.CellRequest;
import com.gervasioamy.minesweeperapi.controller.dto.NewGameRequest;
import com.gervasioamy.minesweeperapi.exception.CellAlreadyDiscoveredException;
import com.gervasioamy.minesweeperapi.exception.GameInitException;
import com.gervasioamy.minesweeperapi.exception.GameNotFoundException;
import com.gervasioamy.minesweeperapi.model.Game;
import com.gervasioamy.minesweeperapi.model.GameStatus;
import com.gervasioamy.minesweeperapi.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameService gameServiceMock;

    @Test
    public void givenValidInputDataWhenCreateANewGameReturn200() throws Exception {
        int rows = 10;
        int cols = 8;
        int mines = 3;
        String player = "player1";
        Game mockedGame = mock(Game.class);
        when(mockedGame.getStatus()).thenReturn(GameStatus.CREATED);
        NewGameRequest request = new NewGameRequest(rows, cols, mines, player);
        when(gameServiceMock.newGame(eq(player), eq(mines), eq(rows), eq(cols))).thenReturn(mockedGame);
        mockMvc.perform(post("/api/games/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidInputDataWhenCreateANewGameReturn400() throws Exception {
        int rows = -1;
        int cols = 4;
        int mines = 3;
        String player = "player1";
        Game mockedGame = mock(Game.class);
        when(mockedGame.getStatus()).thenReturn(GameStatus.CREATED);
        NewGameRequest request = new NewGameRequest(rows, cols, mines, player);
        when(gameServiceMock.newGame(eq(player), eq(mines), eq(rows), eq(cols))).thenThrow(new GameInitException("Invalid rows"));
        mockMvc.perform(post("/api/games/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenMissingRequiredFieldInInputDataWhenCreateANewGameReturn400() throws Exception {
        int rows = -1;
        int cols = 4;
        int mines = 3;
        Game mockedGame = mock(Game.class);
        when(mockedGame.getStatus()).thenReturn(GameStatus.CREATED);
        NewGameRequest request = new NewGameRequest(rows, cols, mines, null);
        when(gameServiceMock.newGame(isNull(), eq(mines), eq(rows), eq(cols))).thenThrow(new GameInitException("Invalid player name"));
        mockMvc.perform(post("/api/games/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidInputWhenDiscoverACellShouldReturn200() throws Exception {
        String gameId = "123";
        int row = 1;
        int col = 4;
        Game mockedGame = mock(Game.class);
        when(mockedGame.getPlayer()).thenReturn("player1");
        when(mockedGame.getStatus()).thenReturn(GameStatus.STARTED);
        CellRequest request = new CellRequest(row, col);
        when(gameServiceMock.getGame(eq(gameId))).thenReturn(mockedGame);
        mockMvc.perform(post("/api/games/{gameId}/discover", gameId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenADiscoveredCellWhenDiscoverThatCellAgainShouldReturn400() throws Exception {
        String gameId = "123";
        int row = 1;
        int col = 4;
        CellRequest request = new CellRequest(row, col);
        when(gameServiceMock.discoverCell(eq(gameId), eq(row), eq(col))).thenThrow(new CellAlreadyDiscoveredException(row, col));
        mockMvc.perform(post("/api/games/{gameId}/discover", gameId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenAMisingGameWhenDiscoverACellAgainShouldReturn404() throws Exception {
        String gameId = "123";
        int row = 1;
        int col = 4;
        CellRequest request = new CellRequest(row, col);
        when(gameServiceMock.discoverCell(eq(gameId), eq(row), eq(col))).thenThrow(new GameNotFoundException(gameId));
        mockMvc.perform(post("/api/games/{gameId}/discover", gameId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
