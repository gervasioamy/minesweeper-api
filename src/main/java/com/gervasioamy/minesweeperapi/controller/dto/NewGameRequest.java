package com.gervasioamy.minesweeperapi.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NewGameRequest {

    private int cols, rows, mines;

    private String player;

}
