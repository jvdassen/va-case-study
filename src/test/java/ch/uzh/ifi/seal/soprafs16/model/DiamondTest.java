package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;
import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.constant.LootType;
/*
 * @author Jan von der Assen
 */
public class DiamondTest {


	@Test
	public void test() {
		Diamond testDiamond  = new Diamond();
		testDiamond.setLootType(LootType.DIAMOND);
		assertEquals(testDiamond.getValue(), 500);
		assertEquals(testDiamond.getLootType(), LootType.DIAMOND);
		
		
	}
	
	
	

}
