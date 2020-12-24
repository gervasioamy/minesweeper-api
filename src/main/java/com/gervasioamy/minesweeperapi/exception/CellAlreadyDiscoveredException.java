package com.gervasioamy.minesweeperapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CellAlreadyDiscoveredException extends RuntimeException {

    private int row, col;
    private int errorCode = ErrorCodes.ERROR_1002_CELL_ALREADY_DISCOVERED;

    public CellAlreadyDiscoveredException(int row, int col) {
        this.row = row;
        this.col = col;
    }

}
