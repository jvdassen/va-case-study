package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;



public class RoundCardTest {

	@Test
	public void test() {
		RoundCard testRoundCard = new RoundCard();
		Map<Integer, ETurn> testMap = new HashMap<Integer, ETurn>();
		
		testRoundCard.setCurrentTurn(1);
		assertEquals(testRoundCard.getCurrentTurn(), 1);

		testRoundCard.setGameId(new Long(2));
		assertEquals(testRoundCard.getGameId(), new Long(2));
		
		testRoundCard.setMaxTurns(9);
		assertEquals(testRoundCard.getMaxTurns(), 9);
		
		SpecialEvent testSpecialEvent = new SpecialEvent();
		
		testSpecialEvent.setSpecialEvent(ESpecialEvent.ANGRY_MARSHAL);
		
		testRoundCard.setSpecialEvent(testSpecialEvent);
		assertEquals(testRoundCard.getSpecialEvent(), testSpecialEvent);
	
		testRoundCard.setTrainStation(true);
		assertEquals(testRoundCard.isTrainStation(), true);
		
		testRoundCard.setCardNum(2);
		assertEquals(testRoundCard.getCardNum(), 2);
		
		testRoundCard.setTurns(testMap);
		assertEquals(testRoundCard.getTurns(), testMap);
		
		
		
	}

}
