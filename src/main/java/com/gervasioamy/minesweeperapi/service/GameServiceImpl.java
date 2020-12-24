package com.gervasioamy.minesweeperapi.service;

import com.gervasioamy.minesweeperapi.exception.GameNotFoundException;
import com.gervasioamy.minesweeperapi.model.Cell;
import com.gervasioamy.minesweeperapi.model.Game;
import com.gervasioamy.minesweeperapi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer.
 */
@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game newGame(String player, int mines, int rows, int cols) {
        Game newGame = new Game(player, mines, rows, cols);
        gameRepository.save(newGame);
        return newGame;
    }

    @Override
    public List<Cell> discoverCell(String gameId, int row, int col) {
        Game game = getGame(gameId);
        List<Cell> result = game.discoverCell(row, col);
        gameRepository.save(game);
        return result;
    }

    @Override
    public void flagCell(String gameId, int row, int col) {
        Game game = getGame(gameId);
        game.flagCell(row, col);
        gameRepository.save(game);

    }

    @Override
    public void unflagCell(String gameId, int row, int col) {
        Game game = getGame(gameId);
        game.unflagCell(row, col);
        gameRepository.save(game);
    }

    @Override
    public void pause(String gameId) {
        Game game = getGame(gameId);
        game.pause();
        gameRepository.save(game);
    }

    @Override
    public void resume(String gameId) {
        Game game = getGame(gameId);
        game.resume();
        gameRepository.save(game);
    }

    public Game getGame(String gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
    }


}
