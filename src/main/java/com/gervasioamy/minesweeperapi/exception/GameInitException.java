package com.gervasioamy.minesweeperapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameInitException extends RuntimeException {

    private int errorCode = ErrorCodes.ERROR_1005_GAME_INIT_ERROR;

    public GameInitException(String message) {
        super(message);
    }
}
