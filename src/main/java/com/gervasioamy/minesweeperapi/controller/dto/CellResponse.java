package com.gervasioamy.minesweeperapi.controller.dto;

import lombok.*;

@Builder
@Setter
@Getter
public class CellResponse {

    private Integer row, col; // coordinates

    private Boolean discovered; // true if this cell was discovered by the player

    private Integer value; // how many adjacent mines this cell has?

    private Boolean flagged; // true if this cell was flagged by the player

}
