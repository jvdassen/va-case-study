package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.constant.LootType;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.MoneyBag;
/*
 * @author Jan von der Assen
 */
public class MoneyBagTest {

	@Test
	public void test() {

		MoneyBag testMoneyBag = new MoneyBag();
		
		/*
		 	check that a moneyBag's value cannot be set 
		 	to an invalid value
		*/
		testMoneyBag.setLootType(LootType.MONEYBAG);
		
		testMoneyBag.setValue(200);
		assertNotEquals(testMoneyBag.getValue(), 200);
		testMoneyBag.setValue(600);
		assertNotEquals(testMoneyBag.getValue(), 600);
		
		testMoneyBag.setValue(400);
		assertEquals(testMoneyBag.getValue(), 400);
		assertEquals(testMoneyBag.getLootType(), LootType.MONEYBAG);
		/*
		 	check that a moneyBag's value can be set to a
		 	valid value
		*/
		
		testMoneyBag.setValue(250);
		assertEquals(testMoneyBag.getValue(), 250);
		
		//testing functions from loot class
		
		testMoneyBag.setGameId(1);
		assertEquals(testMoneyBag.getGameId(), 1);
		
		testMoneyBag.setTrainId(1);
		assertEquals(testMoneyBag.getTrainId(), 1);
		
		testMoneyBag.setUserId(123);
		assertEquals(testMoneyBag.getUserId(), 123);
	}

}
