package com.gervasioamy.minesweeperapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameNotFoundException extends RuntimeException {

    private String gameId;
    private int errorCode = ErrorCodes.ERROR_1000_GAME_NOT_FOUND;

    public GameNotFoundException(String gameId) {
        this.gameId = gameId;
    }
}
