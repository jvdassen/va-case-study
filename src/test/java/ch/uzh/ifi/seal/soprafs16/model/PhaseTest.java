package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.constant.EPhase;
import ch.uzh.ifi.seal.soprafs16.model.game.Game;
import ch.uzh.ifi.seal.soprafs16.model.game.Phase;

public class PhaseTest {

	@Test
	public void test() {
		
		Phase testPhase = new Phase();
		Game testGame = new Game();
		long gameId = 1;
		
		testPhase.setGameId(gameId);
		assertEquals(testPhase.getGameId(), gameId);
		
		testPhase.setPhase(EPhase.ACTION);
		assertEquals(testPhase.getPhase(), EPhase.ACTION);
		
		testPhase.setPhase(EPhase.PLANNING);
		assertEquals(testPhase.getPhase(), EPhase.PLANNING);
		
		testPhase.setPhase(EPhase.SPECIAL_EVENT);
		assertEquals(testPhase.getPhase(), EPhase.SPECIAL_EVENT);
		
		testPhase.updatePhase();
		testPhase.updatePhase();
		testPhase.updatePhase();

		assertEquals(testPhase.getPhase(), EPhase.SPECIAL_EVENT);

	}

}
