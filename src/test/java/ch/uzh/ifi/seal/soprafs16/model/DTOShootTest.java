package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

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
