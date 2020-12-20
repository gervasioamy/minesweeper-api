package com.gervasioamy.minesweeperapi.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CellRequest {

    private int row, col;

}
