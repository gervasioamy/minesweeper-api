package com.gervasioamy.minesweeperapi.repository;

import com.gervasioamy.minesweeperapi.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<Game, String>{

    List<Game> findByPlayer(String player);

}
