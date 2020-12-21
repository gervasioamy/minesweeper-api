package com.gervasioamy.minesweeperapi.controller;

import com.gervasioamy.minesweeperapi.controller.dto.CellRequest;
import com.gervasioamy.minesweeperapi.controller.dto.DiscoverCellResponse;
import com.gervasioamy.minesweeperapi.controller.dto.GameResponse;
import com.gervasioamy.minesweeperapi.controller.dto.NewGameRequest;
import com.gervasioamy.minesweeperapi.model.Cell;
import com.gervasioamy.minesweeperapi.model.Game;
import com.gervasioamy.minesweeperapi.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Create a new game with the given parameters")
    @ApiResponses(value = { @ApiResponse(responseCode = "400", description = "Invalid input data, i.e., more mines than " +
            "cells, mines a negative number, player null or empty, rows or cols are less than 3") })
    @PostMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GameResponse createNewGame(@RequestBody NewGameRequest request) {
        Game newGame = gameService.newGame(request.getPlayer(), request.getMines(), request.getRows(), request.getCols());
        log.info("New game just created for player {} \n {}", request.getPlayer(), newGame);
        return new GameResponse(newGame);
    }

    @Operation(summary = "Retrieve a whole game with details of  the current status")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "GameID not found") })
    @GetMapping(value = "/games/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GameResponse getGame(@PathVariable("id") String gameId) {
        Game theGame = gameService.getGame(gameId);
        log.info("Game for player {} \n {}", theGame.getPlayer(), theGame);
        return new GameResponse(theGame);
    }

    @Operation(summary = "Discover a given cell. It emulates the click on cell while playing")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "GameID not found"),
            @ApiResponse(responseCode = "400", description = "Invalid row, col"),
            @ApiResponse(responseCode = "400", description = "The cell was already discovered"),
            @ApiResponse(responseCode = "400", description = "Invalid game status (other than CREATED or STARTED")})
    @PostMapping(value = "/games/{id}/discover", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DiscoverCellResponse discoverCell(@PathVariable("id") String gameId, @RequestBody CellRequest request) {
        List<Cell> discovered = gameService.discoverCell(gameId, request.getRow(), request.getCol());
        DiscoverCellResponse response = new DiscoverCellResponse(gameService.getGame(gameId).getStatus(), discovered);
        return response;
    }

    @Operation(summary = "Flag a cell. It ensures this cell can't be discovered (by mistake maybe?)")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "GameID not found"),
            @ApiResponse(responseCode = "400", description = "Invalid row, col"),
            @ApiResponse(responseCode = "400", description = "Invalid game status (other than CREATED or STARTED")})
    @PostMapping(value = "/games/{id}/flag", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GameResponse flagCell(@PathVariable("id") String gameId, @RequestBody CellRequest request) {
        gameService.flagCell(gameId, request.getRow(), request.getCol());
        return this.getGame(gameId);
    }

    @Operation(summary = "Unflag a cell. It left the given cell ready to be discovered")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "GameID not found"),
            @ApiResponse(responseCode = "400", description = "Invalid row, col"),
            @ApiResponse(responseCode = "400", description = "Invalid game status (other than CREATED or STARTED")})
    @DeleteMapping(value = "/games/{id}/flag", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GameResponse unflagCell(@PathVariable("id") String gameId, @RequestBody CellRequest request) {
        gameService.unflagCell(gameId, request.getRow(), request.getCol());
        return this.getGame(gameId);
    }

    @Operation(summary = "Pause a game.")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "GameID not found"),
            @ApiResponse(responseCode = "400", description = "Invalid game status (other than STARTED")})
    @PostMapping(value = "/games/{id}/pause")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void pause(@PathVariable("id") String gameId) {
        gameService.pause(gameId);
    }

    @Operation(summary = "Resume a paused a game.")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "GameID not found"),
            @ApiResponse(responseCode = "400", description = "Invalid game status (other than PAUSED")})
    @DeleteMapping(value = "/games/{id}/pause", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GameResponse resume(@PathVariable("id") String gameId) {
        gameService.resume(gameId);
        return this.getGame(gameId);
    }
}
