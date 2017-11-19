package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DTOSpecialEventTest {

	@Test
	public void test() {
		
		DTOSpecialEvent testDTO = new DTOSpecialEvent();
		MoneyBag testLoot = new MoneyBag();
		MoneyBag testLoot2 = new MoneyBag();
		AmmoCard testAmmoCard = new AmmoCard();
		testAmmoCard.setUserId(1);

		testLoot.setUserId(1);
		testLoot2.setUserId(2);
		
		testDTO.setUserId(1);
		assertEquals(testDTO.getUserId(), 1);
		
		testDTO.setMoney(500);
		assertEquals(testDTO.getMoney(), 500);
		
		testDTO.setLostLoot(testLoot);
		assertEquals(testDTO.getLostLoot(), testLoot);
		
		testDTO.setTakenLoot(testLoot2);
		assertEquals(testDTO.getTakenLoot(), testLoot2);
		
		testDTO.setShotAmmoCard(testAmmoCard);
		assertEquals(testDTO.getShotAmmoCard(), testAmmoCard);
		
		
		
	}

}
