package com.gervasioamy.minesweeperapi.service;

import com.gervasioamy.minesweeperapi.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration Test with embedded mongoDB
 */
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    //@Test
    public void test() {
        Game gameCreated = gameService.newGame("player1", 4, 10,10);
        Game gameRetrieved = gameService.getGame(gameCreated.getId());
        assertEquals(gameCreated.getId(), gameRetrieved.getId());
        assertEquals(gameCreated.getPlayer(), gameRetrieved.getPlayer());
        assertEquals(gameCreated.getMines(), gameRetrieved.getMines());
    }

    /*
    @Test
    public void test(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

        // when
        mongoTemplate.save(objectToSave, "collection");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
                .containsOnly("value");
    }

     */

}
