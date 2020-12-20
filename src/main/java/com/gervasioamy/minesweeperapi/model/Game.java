package com.gervasioamy.minesweeperapi.model;

import com.gervasioamy.minesweeperapi.exception.CellAlreadyDiscoveredException;
import com.gervasioamy.minesweeperapi.exception.GameInitException;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.*;

/**
 * The Game representation. It basically holds the matrix of cells (as an array), the player and other info about the
 * game itself.
 */
@Document(collection = "games")
@Getter
public class Game {

    @Id
    private String id;  // the game id

    private List<List<Cell>> cells; // the matrix

    private String player;  // who's playing?

    private Date startedTimestamp;  // when it started?

    private Date endedTimestamp;  // when it ended?

    private int mines; // how many mines it holds?

    private int rows, cols; // matrix X and Y

    private int flaggedMines; // how many well flagged cells there are?
    private int flaggedNonMInes; // how many wrong flagged cells there are (non mines)?

    private GameStatus status;

    private int cellsDiscovered;

    public Game(String player, int mines, int rows, int cols) {
        this.validateInit(player, mines, rows, cols);
        this.id = UUID.randomUUID().toString();
        this.player = player;
        this.mines = mines;
        this.rows = rows;
        this.cols = cols;
        this.status = GameStatus.CREATED;
        this.flaggedMines = 0;
        this.flaggedNonMInes = 0;
        this.initGame();
    }

    protected void validateInit(String player, int mines, int height, int width) {
        if (player == null || "".equals(player)) {
            throw new GameInitException("Invalid player name");
        }
        if (height < 3 || width < 3) {
            throw new GameInitException("Invalid height or width. Minimum allowed size is 3x3 ");
        }
        if (mines < 1) {
            throw new GameInitException("There should be at least 1 mine in the game");
        }
        if (mines > (height * width) ) {
            throw new GameInitException("Too much mines in the game");
        }
    }

    protected void initGame() {
        // let's start the matrix
        this.cells = new ArrayList<>(rows);
        for (int i = 0; i < this.rows; i++) {
            this.cells.add(new ArrayList<>(cols));
            for (int j = 0; j < this.cols; j++) {
                this.cells.get(i).add(new Cell(i, j));
            }
        }
        // now put the mines
        this.putRandomMines();
        // and lastly set the values for each cell (adjacent mines)
        this.calculateCellValues();
    }

    /**
     * Puts the needed amount of mines in the board randomly
     * @see Random
     */
    private void putRandomMines() {
        int minesCount = 0;
        while (minesCount < mines) {
            int rRandom = new Random().nextInt(this.rows);
            int cRandom = new Random().nextInt(this.cols);
            if (!this.cells.get(rRandom).get(cRandom).isMine()) {
                this.cells.get(rRandom).get(cRandom).setMine(true);
                this.cells.get(rRandom).get(cRandom).setValue(-1);
                minesCount++;
            }
        }
    }

    /**
     * Once the mines are placed, then calculates the value for each of non-mines, i.e., how many adjacent mines it has
     *
     */
    protected void calculateCellValues() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (!cells.get(i).get(j).isMine()) {
                    cells.get(i).get(j).setValue((int) this.getAdjacents(i, j).stream().filter(cell -> cell.isMine()).count());
                }
            }
        }
    }

    protected List<Cell> getAdjacents(int x, int y) {
        List<Cell> adjacents = new ArrayList<>();
        /*
         * (x-1,y-1) (x, y-1) (x+1, y-1)
         *  (x-1,y)   (x, y)   (x+1, y)
         * (x-1,y+1) (x, y+1) (x+1, y+1)
         */
        getCell(x-1, y-1).ifPresent(cell -> adjacents.add(cell));
        getCell(x, y-1).ifPresent(cell -> adjacents.add(cell));
        getCell(x+1, y-1).ifPresent(cell -> adjacents.add(cell));
        getCell(x+1, y).ifPresent(cell -> adjacents.add(cell));
        getCell(x+1, y+1).ifPresent(cell -> adjacents.add(cell));
        getCell(x, y+1).ifPresent(cell -> adjacents.add(cell));
        getCell(x-1, y+1).ifPresent(cell -> adjacents.add(cell));
        getCell(x-1, y).ifPresent(cell -> adjacents.add(cell));
        return adjacents;
    }

    protected Optional<Cell> getCell(int row, int col) {
        if (row < 0 || row > this.rows -1 || col < 0 || col > this.cols -1) {
            return Optional.empty();
        } else {
          return Optional.of(cells.get(row).get(col));
        }
    }

    public void startPlaying() {
        if (this.status == GameStatus.CREATED) {
            this.status = GameStatus.STARTED;
            this.startedTimestamp = new Date();
        }
    }

    /**
     * Discover the selected cell and verifies it's not a mine. If the cell value is 0 (no adjacent mines) then it
     * clears recurrently the adjacent cells. It a mind is found, GAME OVER
     * @param x
     * @param y
     * @return true if the game ended (check status to verify if the player won)
     */
    public boolean discoverCell(int x, int y) {
        startPlaying(); // will only take effect the first time a cell is discovered to let the game get started
        Cell cell = this.getCell(x, y).orElseThrow();
        if (cell.isDiscovered()) {
            throw new CellAlreadyDiscoveredException(x, y);
        }
        cell.setDiscovered(true);
        cellsDiscovered++;
        if (cell.isMine()) {
            this.gameOver();
            return true;
        } else if (cell.getValue() == 0) {
            this.discoverAdjacentCells(x, y);
        }
        // if all non-mines cells was discovered, then the player wins!
        if (cellsDiscovered == rows * cols - mines) {
            // all non-mine cells was discovered, the player wins
            this.win();
            return true;
        } else
            return false;
    }

    private void discoverAdjacentCells(int x, int y) {
        Cell cell = cells.get(x).get(y);
        getAdjacents(x, y).stream().forEach(c -> {
            if (!c.isDiscovered() && !c.isMine())
                discoverCell(c.getRow(), c.getCol());
        });

    }

    private void gameOver() {
        this.status = GameStatus.GAME_OVER;
        this.endedTimestamp = new Date();
    }

    private void win() {
        this.status = GameStatus.WON;
        this.endedTimestamp = new Date();
    }

    /**
     * Flag the cell, i.e. the player identify this cell as a mine
     * @param row
     * @param col
     * @return true if the cell was flagged ok, false if the cell was already discovered or flagged (not able to be flagged)
     */
    public boolean flagCell(int row, int col) {
        Cell cell = getCell(row, col).get();
        if (cell.isDiscovered() || cell.isFlagged()) {
            return false;
        } else {
            cell.setFlagged(true);
            if (cell.isMine()) {
                flaggedMines++;
            } else {
                flaggedNonMInes++;
            }
            return true;
        }
    }

    /**
     * Undo the flag on the cell, i.e. the player identify this cell as a mine
     * @param row
     * @param col
     * @return true if the cell was unflagged ok, false if the cell was already discovered or not yet flagged
     */
    public boolean unflagCell(int row, int col) {
        Cell cell = getCell(row, col).get();
        if (cell.isDiscovered() || !cell.isFlagged()) {
            return false;
        } else {
            cell.setFlagged(false);
            if (cell.isMine()) {
                flaggedMines--;
            } else {
                flaggedNonMInes--;
            }
            return true;
        }
    }


    // utility for debugging propose
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Game:\n");
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                Cell cell = cells.get(i).get(j);
                sb.append(cell.isFlagged() ? " ✎": "  ");
                sb.append(cell.getValue() == -1 ? " X" : " " + cell.getValue() );
                sb.append(cell.isDiscovered() ? "+": "-");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}


