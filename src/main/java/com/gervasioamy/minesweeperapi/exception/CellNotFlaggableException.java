package com.gervasioamy.minesweeperapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CellNotFlaggableException extends RuntimeException {

    private int row, col;
    private int errorCode = ErrorCodes.ERROR_1003_CELL_NOT_FLAGGABLE;

    public CellNotFlaggableException(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
