package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.constant.LootType;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOLoot;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTORob;

public class DTORobTest {

	@Test
	public void test() {
		DTORob testRob = new DTORob();
		DTOLoot testLoot = new DTOLoot();
		
		testLoot.setLootType(LootType.DIAMOND);
		
		testRob.setDTOLoot(testLoot);
		assertEquals(testRob.getDTOLoot(), testLoot);
	}

}
