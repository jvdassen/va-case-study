package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.constant.LootType;

public class DTOLootTest {

	@Test
	public void test() {
		
		DTOLoot testDTOLoot = new DTOLoot();
		testDTOLoot.setValue(500);
		assertEquals(testDTOLoot.getValue(), 500);
		
		testDTOLoot.setLootType(LootType.MONEYBAG);
		assertEquals(testDTOLoot.getLootType(), LootType.MONEYBAG);
		
	}

}
