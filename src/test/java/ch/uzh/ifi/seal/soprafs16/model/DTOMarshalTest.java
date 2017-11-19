package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DTOMarshalTest {

	@Test
	public void test() {
		DTOMarshal testMarshal = new DTOMarshal();
		AmmoCard testCard = new AmmoCard();
		
		testMarshal.setLeft(true);
		assertEquals(testMarshal.isLeft(), true);
		
		testMarshal.setShotAmmoCard(testCard);
		assertEquals(testMarshal.getShotAmmoCard(), testCard);
		
	}

}
