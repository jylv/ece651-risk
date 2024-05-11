package ece651.sp22.grp8.risk.server;

import ece651.sp22.grp8.risk.Map;
import ece651.sp22.grp8.risk.MapFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GamePoolTest {

    @Test
    public void test_basic(){
        GamePool gamePool = new GamePool();
        long gameID = 5000;
        Game game = new Game(5,gameID);
        gamePool.addGame(gameID,game);
        assertEquals(game,gamePool.getGame(gameID));
        assertTrue(gamePool.containGame(gameID));
        assertFalse(gamePool.containGame(5001L));
    }

    @Test
    public void test_getGameMap(){
        GamePool gamePool = new GamePool();
        long gameID = 5000;
        Game game = new Game(5,gameID);
        gamePool.addGame(gameID,game);
        game.generateMap();
        MapFactory mapFactory = new MapFactory();
        Map expected = mapFactory.generateMapWithPlayer(5);
        Map actual = gamePool.getGameMap(gameID);
//        assertEquals(expected,actual); todo
    }


}
