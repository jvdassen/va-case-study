package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.model.dto.DTOInvalid;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.AmmoCard;

public class DTOInvalidTest {

	@Test
	public void test() {
		
		DTOInvalid testInvalid = new DTOInvalid();
		AmmoCard testAmmoCard = new AmmoCard();
		
		testInvalid.setAmmoCard(testAmmoCard);
		assertEquals(testInvalid.getAmmoCard(), testAmmoCard);
		
		assertEquals(testInvalid.isWalkedIntoMarshal(), false);
	}

}
