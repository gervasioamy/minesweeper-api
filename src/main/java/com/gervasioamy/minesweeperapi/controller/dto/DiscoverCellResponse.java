package com.gervasioamy.minesweeperapi.controller.dto;

import com.gervasioamy.minesweeperapi.model.Cell;
import com.gervasioamy.minesweeperapi.model.GameStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DiscoverCellResponse {

    private GameStatus gameStatus;

    private List<CellResponse> discoveredCells;

    public DiscoverCellResponse(GameStatus status, List<Cell> discovered) {
        this.gameStatus = status;
        this.discoveredCells = discovered.stream().map(cell -> CellResponse.builder()
                .row(cell.getRow())
                .col(cell.getCol())
                .value(cell.getValue())
                .discovered(cell.isDiscovered())
                .flagged(cell.isFlagged())
                .build()).collect(Collectors.toList());
    }

}
