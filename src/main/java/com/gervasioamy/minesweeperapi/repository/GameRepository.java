package com.gervasioamy.minesweeperapi.repository;

import com.gervasioamy.minesweeperapi.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, String>{

}
