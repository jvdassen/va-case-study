package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import antlr.collections.List;
import ch.uzh.ifi.seal.soprafs16.model.game.PlayerSequence;
/*
 * @author Luc Boillat
 */
public class PlayerSequenceTest {

	@Test
	public void test() {
				
		PlayerSequence testPlayerSequence = new PlayerSequence();
		User testUser1 = new User();
		User testUser2 = new User();
		User testUser3 = new User();
		User testUser4 = new User();
		long gameId = 1;
		ArrayList testSequence = new ArrayList<>();
		ArrayList testHasPlayed = new ArrayList<>();

		testSequence.add(testUser1);
		testSequence.add(testUser2);
		testSequence.add(testUser3);
		testSequence.add(testUser4);
		testHasPlayed.add(false);
		testHasPlayed.add(false);
		testHasPlayed.add(false);
		testHasPlayed.add(false);

	/*
	 * testing setter/getter methods
	 */
		testPlayerSequence.setGameId(gameId);
		assertEquals(testPlayerSequence.getGameId(), gameId);
		
		testPlayerSequence.setInversed(true);
		assertEquals(testPlayerSequence.isInversed(), true);
		
		testPlayerSequence.setDoubleRound(true);
		assertEquals(testPlayerSequence.isDoubleRound(), true);
		
		testPlayerSequence.setSequence(testSequence);
		assertEquals(testPlayerSequence.getSequence(), testSequence);
		
		testPlayerSequence.setHasPlayed(testHasPlayed);
		assertEquals(testPlayerSequence.getHasPlayed(), testHasPlayed);
		
		testPlayerSequence.setFirstPlayed(true);
		assertEquals(testPlayerSequence.isFirstPlayed(), true);

	/*
	 * testing methods that reset booleans
	 */
		testPlayerSequence.resetBools();
		assertEquals(testPlayerSequence.isInversed(), false);
		assertEquals(testPlayerSequence.isDoubleRound(), false);
		assertEquals(testPlayerSequence.isFirstPlayed(), false);
		
		testPlayerSequence.resetHasPlayed();
		assertEquals(testPlayerSequence.getHasPlayed().get(0), false);
		assertEquals(testPlayerSequence.getHasPlayed().get(1), false);
		assertEquals(testPlayerSequence.getHasPlayed().get(2), false);
		assertEquals(testPlayerSequence.getHasPlayed().get(3), false);
				
	/*
	 * 	start testing functions that involve different round-types
	 */
		
	//normal round -> only the first "false" entry gets switched to "true"
		testPlayerSequence.resetBools();

		testPlayerSequence.setCurrentHasPlayed();
		testPlayerSequence.setCurrentHasPlayed();
		testPlayerSequence.setCurrentHasPlayed();
		testPlayerSequence.setCurrentHasPlayed();

		assertEquals(testPlayerSequence.getHasPlayed().get(0), true);
		assertEquals(testPlayerSequence.getHasPlayed().get(1), true);
		assertEquals(testPlayerSequence.getHasPlayed().get(2), true);
		assertEquals(testPlayerSequence.getHasPlayed().get(3), true);
		
		assertEquals(testPlayerSequence.checkIfAllPlayed(), true);
		
		testPlayerSequence.resetHasPlayed();
		
	//inversed round -> the last "false" entry gets turned into "true"
		
		testPlayerSequence.setInversed(true);
		
		testPlayerSequence.setCurrentHasPlayed();
		testPlayerSequence.setCurrentHasPlayed();
		testPlayerSequence.setCurrentHasPlayed();
		testPlayerSequence.setCurrentHasPlayed();

		assertEquals(testPlayerSequence.getHasPlayed().get(0), true);
		assertEquals(testPlayerSequence.getHasPlayed().get(1), true);
		assertEquals(testPlayerSequence.getHasPlayed().get(2), true);
		assertEquals(testPlayerSequence.getHasPlayed().get(3), true);
		
		assertEquals(testPlayerSequence.checkIfAllPlayed(), true);

		testPlayerSequence.resetHasPlayed();

	//double round -> setCurrentHasPlayed needs to be executed twice each round
		
		testPlayerSequence.setInversed(false);
		testPlayerSequence.setDoubleRound(true);
		
		testPlayerSequence.setCurrentHasPlayed();
		testPlayerSequence.setCurrentHasPlayed();		
		testPlayerSequence.setCurrentHasPlayed();
		testPlayerSequence.setCurrentHasPlayed();
		testPlayerSequence.setCurrentHasPlayed();
		testPlayerSequence.setCurrentHasPlayed();		
		testPlayerSequence.setCurrentHasPlayed();
		testPlayerSequence.setCurrentHasPlayed();
		
		assertEquals(testPlayerSequence.getHasPlayed().get(0), true);
		assertEquals(testPlayerSequence.getHasPlayed().get(1), true);
		assertEquals(testPlayerSequence.getHasPlayed().get(2), true);
		assertEquals(testPlayerSequence.getHasPlayed().get(3), true);
		
		assertEquals(testPlayerSequence.checkIfAllPlayed(), true);
		
	/*
	 * checking whose turn it is
	 */
		
		testPlayerSequence.resetBools();
		testPlayerSequence.resetHasPlayed();

	//normal and double round
		
		for(int j = 0; j < testPlayerSequence.getHasPlayed().size(); j++)
		{
			assertEquals(testPlayerSequence.getCurrentPlayer(), testPlayerSequence.getSequence().get(j));
			testPlayerSequence.setCurrentHasPlayed();
		}
		
	//inverse round

		testPlayerSequence.resetHasPlayed();
		testPlayerSequence.setInversed(true);
		
		for(int j = 3; j > -1; j--)
		{
			assertEquals(testPlayerSequence.getCurrentPlayer(), testPlayerSequence.getSequence().get(j));
			testPlayerSequence.setCurrentHasPlayed();
		}
	}
}