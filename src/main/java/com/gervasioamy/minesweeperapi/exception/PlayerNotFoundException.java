package com.gervasioamy.minesweeperapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerNotFoundException extends RuntimeException{

    private String player;

    private int errorCode = ErrorCodes.ERROR_1000_GAME_NOT_FOUND;

    public PlayerNotFoundException(String player) {
        this.player = player;
    }
}
