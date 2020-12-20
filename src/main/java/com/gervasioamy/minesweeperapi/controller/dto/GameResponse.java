package com.gervasioamy.minesweeperapi.controller.dto;

import com.gervasioamy.minesweeperapi.model.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GameResponse {

    private String id;  // the game id

    private List<List<CellResponse>> cells; // the matrix

    private String player;  // who's playing?

    private Date startedTimestamp;  // when it started?

    private Date endedTimestamp;  // when it ended?

    private int mines; // how many mines it holds?

    private int rows, cols; // matrix X and Y

    private String status;

    /**
     * Creates a {@link GameResponse} from a {@link Game} instance
     * @param newGame
     */
    public GameResponse(Game newGame) {
        this.id = newGame.getId();
        this.player = newGame.getPlayer();
        this.rows = newGame.getRows();
        this.cols = newGame.getCols();
        this.mines = newGame.getMines();
        this.cells = newGame.getCells().stream().map(row ->
            row.stream().map(cell -> CellResponse.builder().
                    x(cell.getRow()).
                    y(cell.getCol()).
                    mine(cell.isMine()).
                    discovered(cell.isDiscovered()).
                    flagged(cell.isFlagged()).
                    build()).collect(Collectors.toList())
        ).collect(Collectors.toList());
        this.status = newGame.getStatus().toString();
        this.startedTimestamp = newGame.getStartedTimestamp();
        this.endedTimestamp = newGame.getEndedTimestamp();
    }


}
