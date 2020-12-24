package com.gervasioamy.minesweeperapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CellNotFlaggableException extends RuntimeException {

    private int row, col;

}
