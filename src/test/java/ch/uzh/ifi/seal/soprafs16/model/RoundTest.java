package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoundTest {
/*
 * @author Luc Boillat
 */
	@Test
	public void test() {
		
		Round testRound = new Round();
		Game testGame = new Game();
		long gameId = 1;
		
		testRound.setGameId(gameId);
		assertEquals(testRound.getGameId(), gameId);
		
		testRound.setCurrentRound(ERound.ONE);
		assertEquals(testRound.getCurrentRound(), ERound.ONE);
		
		assertEquals(testRound.getMaxRounds(), 5);
		
		testRound.setCurrentTurn(2);
		assertEquals(testRound.getCurrentTurn(), 2);
		
		testRound.updateRound();
		testRound.updateRound();
		testRound.updateRound();
		testRound.updateRound();
		assertEquals(testRound.getCurrentRound(), ERound.FIVE);
		
		
	}

}
