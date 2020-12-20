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
    public Map<String, String> handleGameNotFound(GameNotFoundException e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Game ID: " + e.getGameId() + " was not found");
        return response;
    }

    @ExceptionHandler(value = GameInitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleGameInitFailure(GameInitException e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return response;
    }
    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleNoSuchElementException(NoSuchElementException e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Wrong cell coordinates");
        return response;
    }

    @ExceptionHandler(value = CellAlreadyDiscoveredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleCellAlreadyDiscoveredException(CellAlreadyDiscoveredException e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Cell (" + e.getRow() + ", " + e.getCol() + ") is already discovered");
        return response;
    }

}
