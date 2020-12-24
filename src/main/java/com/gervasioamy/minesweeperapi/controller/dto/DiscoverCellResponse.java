package com.gervasioamy.minesweeperapi.controller.dto;

import com.gervasioamy.minesweeperapi.model.Cell;
import com.gervasioamy.minesweeperapi.model.GameStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Response for endpoint POST api/games/{id}/discover, which is basically the game status and the list of cells
 * discovered (only row, col and value)
 */
@Getter
@Setter
public class DiscoverCellResponse {

    private GameStatus gameStatus;

    private List<DiscoveredCellResponse> discoveredCells;

    public DiscoverCellResponse(GameStatus status, List<Cell> discovered) {
        this.gameStatus = status;
        this.discoveredCells = discovered.stream().map(cell -> DiscoveredCellResponse.builder()
                .row(cell.getRow())
                .col(cell.getCol())
                .value(cell.getValue())
                .build()).collect(Collectors.toList());
    }
}

@Builder
@Setter
@Getter
class DiscoveredCellResponse {

    private Integer row, col; // coordinates
    private Integer value; // how many adjacent mines this cell has?

}