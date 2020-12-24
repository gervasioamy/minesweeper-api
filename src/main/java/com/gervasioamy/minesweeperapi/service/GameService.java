package com.gervasioamy.minesweeperapi.service;

import com.gervasioamy.minesweeperapi.exception.CellAlreadyDiscoveredException;
import com.gervasioamy.minesweeperapi.model.Cell;
import com.gervasioamy.minesweeperapi.model.Game;

import java.util.List;

public interface GameService {

    /**
     * Creates a new {@link Game} given the initial parameters
     * @param player the username of who's playing
     * @param mines the numner of mines to be placed in the game
     * @param rows rows in the game
     * @param cols columns in the game
     * @return the Game instance just created
     * @throws com.gervasioamy.minesweeperapi.exception.GameInitException if the initial values are wrong
     */
    Game newGame(String player, int mines, int rows, int cols);

    /**
     * Looks for the game with the given id in the DB
     * @param gameId the game ID to be retrieved
     * @return the {@link Game} with the given id
     * @throws com.gervasioamy.minesweeperapi.exception.GameNotFoundException if the id is not found in the DB
     */
    Game getGame(String gameId);

    /**
     * Discover a cell in the game. It emulates the "click" in a cell when playing
     * @param gameId the game id being playing
     * @param row row
     * @param col column
     * @return true if the player wins wheh the current cell was discovered, false if the player loose becaus ethe cell
     * was a mine
     * @throws java.util.NoSuchElementException if the cell was not found (wrong row, col coordenates)
     * @throws CellAlreadyDiscoveredException if the cell was already discovered
     */
    List<Cell> discoverCell(String gameId, int row, int col);

    /**
     * Flags a cell in the game. It blocks the cell to be discovered.
     * This shuold be used when a player is pretty sure in this cell there's a mine
     * @param gameId the game id being playing
     * @param row row
     * @param col column
     * @return true if the cell was flagged ok, false if the cell was already discovered or flagged (not able to be flagged)
     * @throws java.util.NoSuchElementException if the cell was not found (wrong row, col coordenates)
     */
    boolean flagCell(String gameId, int row, int col);

    /**
     * Undo a flag in the game, so the cell is now able to be discovered.
     * This shuold be used when a player is flagged a wrong cell and wants to undo it
     * @param gameId the game id being playing
     * @param row row
     * @param col column
     * @return true if the cell was unflagged ok, false if the cell was already discovered or not yet flagged
     * @throws java.util.NoSuchElementException if the cell was not found (wrong row, col coordenates)
     */
    boolean unflagCell(String gameId, int row, int col);

    void pause(String gameId);

    void resume(String gameId);

}
