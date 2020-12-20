package com.gervasioamy.minesweeperapi.model;

import lombok.Getter;
import lombok.Setter;

/**
 * A cell in the game matrix. It knows if it's a mine, or else how many adjacent mines it has. It also holds state if
 * the player discovered or flag it
 */
@Getter
@Setter
public class Cell {

    private int row, col; // coordenates

    private boolean mine; // is this cell a mine?

    private boolean discovered; // true if this cell was discovered by the player

    private int value; // how many adjacent mines this cell has?

    private boolean flagged; // true if this cell was flagged by the player

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

}
