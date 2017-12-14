package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.model.dto.DTOShoot;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.AmmoCard;

public class DTOShootTest {

	@Test
	public void test() {
		DTOShoot testShoot = new DTOShoot();
		User testvictim = new User();
		AmmoCard testAmmoCard = new AmmoCard();
		
		testShoot.setVictimAmmoCard(testAmmoCard);
		assertEquals(testShoot.getVictimAmmoCard(), testAmmoCard);
	}

}
