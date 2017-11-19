package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;
/*
 * @author Luc Boillat
 */

import org.junit.Test;

public class SpecialEventTest {

	@Test
	public void test() {
		
		//missing startSpecialEvent
		SpecialEvent testEvent = new SpecialEvent();
		Game testGame = new Game();

		testEvent.setGameId(2);
		assertEquals(testEvent.getGameId(), 2);
		
		testEvent.setSpecialEvent(ESpecialEvent.NOTHING);
		assertEquals(testEvent.getSpecialEvent(), ESpecialEvent.NOTHING);
		
		testEvent.setSpecialEvent(ESpecialEvent.POCKET_PICKING);
		assertEquals(testEvent.getSpecialEvent(), ESpecialEvent.POCKET_PICKING);
	}

}
