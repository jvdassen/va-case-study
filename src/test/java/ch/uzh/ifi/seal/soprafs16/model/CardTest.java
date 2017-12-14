package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.model.gamecard.Card;
/*
 * @author Luc Boillat
 */
public class CardTest {

	@Test
	public void test() {
	
		Card testCard = new Card();
		
		testCard.setGameId(new Long(1234));
		assertEquals(testCard.getGameId(),new Long(1234));
	}

}