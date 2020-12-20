package com.gervasioamy.minesweeperapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameNotFoundException extends RuntimeException {

    private String gameId;
}
