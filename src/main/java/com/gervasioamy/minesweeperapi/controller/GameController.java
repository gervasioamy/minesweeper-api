package com.gervasioamy.minesweeperapi.controller;

import com.gervasioamy.minesweeperapi.controller.dto.CellRequest;
import com.gervasioamy.minesweeperapi.controller.dto.DiscoverCellResponse;
import com.gervasioamy.minesweeperapi.controller.dto.GameResponse;
import com.gervasioamy.minesweeperapi.controller.dto.NewGameRequest;
import com.gervasioamy.minesweeperapi.model.Cell;
import com.gervasioamy.minesweeperapi.model.Game;
import com.gervasioamy.minesweeperapi.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller layer. It basically implements the RESTFul API
 */
@RestController()
@RequestMapping("/api")
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GameResponse createNewGame(@RequestBody NewGameRequest request) {
        Game newGame = gameService.newGame(request.getPlayer(), request.getMines(), request.getRows(), request.getCols());
        log.info("New game just created for player {} \n {}", request.getPlayer(), newGame);
        return new GameResponse(newGame);
    }

    @GetMapping(value = "/games/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GameResponse getGame(@PathVariable("id") String gameId) {
        Game theGame = gameService.getGame(gameId);
        log.info("Game for player {} \n {}", theGame.getPlayer(), theGame);
        return new GameResponse(theGame);
    }

    @PostMapping(value = "/games/{id}/discover", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DiscoverCellResponse discoverCell(@PathVariable("id") String gameId, @RequestBody CellRequest request) {
        List<Cell> discovered = gameService.discoverCell(gameId, request.getRow(), request.getCol());
        DiscoverCellResponse response = new DiscoverCellResponse(gameService.getGame(gameId).getStatus(), discovered);
        return response;
    }

    @PostMapping(value = "/games/{id}/flag", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GameResponse flagCell(@PathVariable("id") String gameId, @RequestBody CellRequest request) {
        gameService.flagCell(gameId, request.getRow(), request.getCol());
        return this.getGame(gameId);
    }

    @DeleteMapping(value = "/games/{id}/flag", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GameResponse unflagCell(@PathVariable("id") String gameId, @RequestBody CellRequest request) {
        gameService.unflagCell(gameId, request.getRow(), request.getCol());
        return this.getGame(gameId);
    }
}
