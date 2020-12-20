package com.gervasioamy.minesweeperapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewGameRequest {

    private int rows, cols, mines;

    private String player;

}
