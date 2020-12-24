package com.gervasioamy.minesweeperapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CellNotUnflaggableException extends RuntimeException {

    private int row, col;
    private int errorCode = ErrorCodes.ERROR_1004_CELL_NOT_UNFLAGGABLE;

    public CellNotUnflaggableException(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
