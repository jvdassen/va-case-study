package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;
/*
 * @author Luc Boillat
 */
public class AmmoCardTest {

	@Test
	public void test() {
		AmmoCard testAmmoCard = new AmmoCard();
		
		testAmmoCard.setNeutral(true);
		assertEquals(testAmmoCard.isNeutral(), true);

		testAmmoCard.setUserId(12);
		assertEquals(testAmmoCard.getUserId(), 12);
		
		testAmmoCard.setVictim(new Long(1234));
		assertEquals(testAmmoCard.getVictim(), new Long(1234));
		
		testAmmoCard.setOwner(new Long(123));
		assertEquals(testAmmoCard.getOwner(), new Long(123));
		
		testAmmoCard.setBulletRound(6);
		assertEquals(testAmmoCard.getBulletRound(), 6);
		
		testAmmoCard.setCharacter(ECharacter.BELLE);
		assertEquals(testAmmoCard.getCharacter(), ECharacter.BELLE);
	}

}