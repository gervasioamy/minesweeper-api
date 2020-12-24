package com.gervasioamy.minesweeperapi.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private String message;
    private int errorCode;
}
