package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.model.game.Game;
import ch.uzh.ifi.seal.soprafs16.model.game.GameState;
import ch.uzh.ifi.seal.soprafs16.model.game.Phase;
import ch.uzh.ifi.seal.soprafs16.model.game.PlayerSequence;
import ch.uzh.ifi.seal.soprafs16.model.game.Round;
/*
 * @author Luc Boillat
 */
public class GameStateTest {

	@Test
	public void test() {
		
		GameState testGameState = new GameState();
		Round testRound = new Round();
		Game testGame = new Game();
		Phase testPhase = new Phase();
		User testPlayerTurn = new User();
		PlayerSequence testSequence = new PlayerSequence();
		long gameId = 1;
		
		testGameState.setRound(testRound);
		assertEquals(testGameState.getRound(), testRound);
		
		testGameState.setGameId(gameId);
		assertEquals(testGameState.getGameId(), gameId);
		
		testGameState.setPhase(testPhase);
		assertEquals(testGameState.getPhase(), testPhase);
		
		testGameState.setPlayerTurn(testPlayerTurn);
		assertEquals(testGameState.getPlayerTurn(), testPlayerTurn);
		
		testGameState.setPlayerSequence(testSequence);
		assertEquals(testGameState.getPlayerSequence(), testSequence);
		
		
		
		
	}

}
