package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;
/*
 * @author Luc Boillat
 */
public class MoveTest {

	@Test
	public void test() {
		
		Move testMove = new Move();
		Game testGame = new Game();
		User testUser = new User();
		long gameId = 1;
		
		testMove.setGameId(gameId);
		assertEquals(testMove.getGameId(), gameId);
		
		testMove.setUserId(2);
		assertEquals(testMove.getUserId(), 2);
	}

}