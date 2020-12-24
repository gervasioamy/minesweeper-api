package com.gervasioamy.minesweeperapi.exception;


import com.gervasioamy.minesweeperapi.model.GameStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameStatusException extends RuntimeException {

    private GameStatus currentStatus;
    private int errorCode = ErrorCodes.ERROR_1001_WRONG_GAME_STATUS;

    public GameStatusException(GameStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
}
