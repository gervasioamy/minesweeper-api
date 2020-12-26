package com.gervasioamy.minesweeperapi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlayerStats {

    String player;

    long totalGames, wonGames, lostGames, abandonedGames;

}
