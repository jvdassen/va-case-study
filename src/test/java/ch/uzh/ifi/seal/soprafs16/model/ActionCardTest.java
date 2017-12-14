package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.constant.EAction;
import ch.uzh.ifi.seal.soprafs16.constant.ECharacter;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.ActionCard;

public class ActionCardTest {

	@Test
	public void test() {
		ActionCard testCard = new ActionCard();
		
		testCard.setCardNum(1);
		assertEquals(testCard.getCardNum(), 1);
		
		testCard.setUserId(1);
		assertEquals(testCard.getUserId(), 1);
		
		testCard.setVisible(true);
		assertEquals(testCard.isVisible(), true);
		
		testCard.seteAction(EAction.CLIMB);
		assertEquals(testCard.geteAction(), EAction.CLIMB);
		
		testCard.setCharacter(ECharacter.CHEYENNE);
		assertEquals(testCard.getCharacter(), ECharacter.CHEYENNE);
		
	}

}
