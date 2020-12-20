package com.gervasioamy.minesweeperapi.model;

import static org.junit.jupiter.api.Assertions.*;

import com.gervasioamy.minesweeperapi.exception.CellAlreadyDiscoveredException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Slf4j
public class GameTest {

    private Game game;

    @BeforeEach
    public  void setup() {
        this.game = new Game("JohnDoe", 10, 8,8);
        this.game.startPlaying();
    }

    @Test
    public void givenInvalidXGetCellReturnNull() {
        assertFalse(game.getCell(-1,3).isPresent());
    }

    @Test
    public void givenInvalidYGetCellReturnNull() {
        assertFalse(game.getCell(2,-3).isPresent());
    }

    @Test
    public void givenValidYGetCellReturnNotNull() {
        assertTrue(game.getCell(2,3).isPresent());
    }

    @Test
    public void givenValidCentricCellThenGetAdjacentsShouldReturn8Cells() {
        assertEquals(8, game.getAdjacents(2,3).size());
    }

    @Test
    public void givenValidCornerCellThenGetAdjacentsShouldReturn3Cells() {
        assertEquals(3, game.getAdjacents(0,0).size());
        assertEquals(3, game.getAdjacents(7,7).size());
        assertEquals(3, game.getAdjacents(0,7).size());
        assertEquals(3, game.getAdjacents(7,0).size());
    }

    @Test
    public void givenValidBorderCellThenGetAdjacentsShouldReturn5Cells() {
        assertEquals(5, game.getAdjacents(0,3).size());
        assertEquals(5, game.getAdjacents(7,3).size());
        assertEquals(5, game.getAdjacents(3,7).size());
        assertEquals(5, game.getAdjacents(3,0).size());
    }

    @Test
    public void givenAValidGameWhenDiscoverASecureCellThenCellShouldBeDiscovered() {
        // let's find a secure zero-valued cell
        Cell secureCell = null;
        while (secureCell == null) {
            int x = new Random().nextInt(8);
            int y = new Random().nextInt(8);
            Cell cell = game.getCell(x, y).get();
            if (cell.getValue() == 0) {
                secureCell = cell;
            }
        }
        log.debug("Testing discoverCell in game:\n{}", game);
        log.debug(">> secureCell: ({}, {})", secureCell.getRow(), secureCell.getCol());
        boolean result = game.discoverCell(secureCell.getRow(), secureCell.getCol());
        log.debug("Game with cell discovered:\n{}", game);
        assertTrue(game.getCell(secureCell.getRow(), secureCell.getCol()).get().isDiscovered());
    }

    @Test
    public void givenAValidGameWhenDiscoverADiscoveredCellThenShouldThrowCellAlreadyDiscoveredException() {
        // let's find a secure zero-valued cell
        Cell secureCell = null;
        while (secureCell == null) {
            int x = new Random().nextInt(8);
            int y = new Random().nextInt(8);
            Cell cell = game.getCell(x, y).get();
            if (cell.getValue() == 0) {
                secureCell = cell;
            }
        }
        final int row = secureCell.getRow();
        final int col = secureCell.getCol();
        game.discoverCell(row, col);
        assertThrows(CellAlreadyDiscoveredException.class, () -> game.discoverCell(row, col));
    }

    @Test
    public void givenAnInvalidCellWhenDiscoverCellShouldThrowNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> game.discoverCell(-1, 4));
    }

    @Test
    public void givenARandomGameWhenDiscoverAMineThenShouldGetGameOver() {
        // let's find a mine cell
        Cell unsecureCell = null;
        while (unsecureCell == null) {
            int x = new Random().nextInt(8);
            int y = new Random().nextInt(8);
            Cell cell = game.getCell(x, y).get();
            if (cell.isMine()) {
                unsecureCell = cell;
            }
        }
        boolean result = game.discoverCell(unsecureCell.getRow(), unsecureCell.getCol());
        assertTrue(result);
        assertEquals(GameStatus.GAME_OVER , game.getStatus());
        assertNotNull(game.getEndedTimestamp());
    }

    @Test
    public void givenARandomGameWhenAllNonMinesAreDiscoveredThenIShouldWin() {
        // let's find all mines
        List<Cell> notMines = new ArrayList<>();
        for (int i=0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell cell = game.getCell(i, j).get();
                if (!cell.isMine())
                    notMines.add(cell);
            }
        }
        notMines.stream().forEach(cell -> {
            if (!cell.isDiscovered()) {
                game.discoverCell(cell.getRow(), cell.getCol());
            }
        });
        log.info("cells discovered: {} - total cells: {} - total mines: {}", game.getCellsDiscovered(), 8*8, 10);
        assertEquals(GameStatus.WON , game.getStatus());
        assertNotNull(game.getEndedTimestamp());
    }

    @Test
    public void givenARandomGameWhenNotAllNonMinesAreDiscoveredThenIShouldNotWin() {
        // let's find all non-mines
        List<Cell> notMines = new ArrayList<>();
        for (int i=0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell cell = game.getCell(i, j).get();
                if (!cell.isMine() && cell.getValue() > 0)
                    notMines.add(cell);
            }
        }
        notMines.remove(0); // just get rid of one mine to verify validateMines works fine
        notMines.stream().forEach(cell -> game.discoverCell(cell.getRow(), cell.getCol()));
        assertEquals(GameStatus.STARTED , game.getStatus());
        assertNull(game.getEndedTimestamp());
    }
}
