package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShortGameTest {

	@Test
	public void test() {
		
		ShortGame testSGame = new ShortGame();
		
		testSGame.setGameId(1);
		assertEquals(testSGame.getGameId(), 1);
		
		testSGame.setShortV(true);
		assertEquals(testSGame.isShortV(), true);
		
	}

}
