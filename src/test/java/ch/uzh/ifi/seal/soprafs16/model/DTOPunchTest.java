package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.model.dto.DTOPunch;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.Diamond;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.Loot;

public class DTOPunchTest {

	@Test
	public void test() {
		DTOPunch testPunch = new DTOPunch();
		User testVictim = new User();
		Loot testLoot = new Diamond();
		Loot testCheyenneLoot = new Diamond();
		
		testPunch.setCheyenneLoot(testCheyenneLoot);
		assertEquals(testPunch.getCheyenneLoot(), testCheyenneLoot);
		
		testPunch.setStolenLoot(testLoot);
		assertEquals(testPunch.getStolenLoot(), testLoot);
		
		
		
	}

}
