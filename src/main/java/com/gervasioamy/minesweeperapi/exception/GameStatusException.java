package com.gervasioamy.minesweeperapi.exception;

import com.gervasioamy.minesweeperapi.model.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameStatusException extends RuntimeException {

    private GameStatus currentStatus;
}
