package com.gervasioamy.minesweeperapi.controller.dto;

import lombok.*;

@Builder
@Setter
@Getter
public class CellResponse {

    private int x, y; // coordenates

    private boolean mine; // is this cell a mine?

    private boolean discovered; // true if this cell was discovered by the player

    private int value; // how many adjacent mines this cell has?

    private boolean flagged; // true if this cell was flagged by the player

}
