package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs16.model.game.Game;

public class GameTest {

	@Test
	public void test() {
		Game testGame = new Game();
		User player1 = new User();
		User player2 = new User();
		ArrayList playerlist = new ArrayList();
		playerlist.add(player1);
		playerlist.add(player2);
		
		
		
		testGame.setPlayers(playerlist);
		assertEquals(testGame.getPlayers(), playerlist);
		
		testGame.setId(new Long(2));
		assertEquals(testGame.getId(), new Long(2));
		
		testGame.setName("game1");
		assertEquals(testGame.getName(), "game1");
		
		testGame.setOwner("testOwner");
		assertEquals(testGame.getOwner(), "testOwner");
		
		testGame.setStatus(GameStatus.RUNNING);
		assertEquals(testGame.getStatus(), GameStatus.RUNNING);
		
		testGame.setCurrentPlayer(new Integer(1));
		assertEquals(testGame.getCurrentPlayer(),new Integer(1));
		
	}

}
