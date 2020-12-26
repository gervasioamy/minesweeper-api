package com.gervasioamy.minesweeperapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class represents the main exception handler for the controller layer.
 * All the non caught runtime exceptions thrown will be handled here and provide a proper HTTP response
 */
@ControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(value = GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleGameNotFound(GameNotFoundException e) {
        return ErrorResponse.builder().
                message("Game ID: " + e.getGameId() + " was not found").
                errorCode(e.getErrorCode()).
                build();
    }

    @ExceptionHandler(value = PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleGameNotFound(PlayerNotFoundException e) {
        return ErrorResponse.builder().
                message("Player: " + e.getPlayer() + " was not found").
                errorCode(e.getErrorCode()).
                build();
    }

    @ExceptionHandler(value = GameInitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleGameInitFailure(GameInitException e) {
        return ErrorResponse.builder().
                message(e.getMessage()).
                errorCode(e.getErrorCode()).
                build();
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleNoSuchElementException(NoSuchElementException e) {
        return ErrorResponse.builder().
                message("Wrong cell coordinates").
                errorCode(ErrorCodes.ERROR_1006_INVALID_CELL).
                build();
    }

    @ExceptionHandler(value = CellAlreadyDiscoveredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCellAlreadyDiscoveredException(CellAlreadyDiscoveredException e) {
        return ErrorResponse.builder().
                message("Cell (" + e.getRow() + ", " + e.getCol() + ") is already discovered").
                errorCode(e.getErrorCode()).
                build();
    }

    @ExceptionHandler(value = GameStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleGameStatusException(GameStatusException e) {
        return ErrorResponse.builder().
                message("The game current status doesn't allow the operation").
                errorCode(e.getErrorCode()).
                build();
    }

    @ExceptionHandler(value = CellNotFlaggableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCellNotFlaggableException(CellNotFlaggableException e) {
        return ErrorResponse.builder().
                message("The cell is not able to be flagged, it's discovered or already flagged").
                errorCode(e.getErrorCode()).
                build();
    }

    @ExceptionHandler(value = CellNotUnflaggableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCellNotUnflaggableException(CellNotUnflaggableException e) {
        return ErrorResponse.builder().
                message("The cell is not able to be unflagged, it's discovered or not yet flagged").
                errorCode(e.getErrorCode()).
                build();
    }

}
