package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TrainLevelTest {

	@Test
	public void test() {
		TrainLevel testLevel = new TrainLevel();
		Character testBelle = new Character();
		Character testDjango = new Character();
		Loot diamond1 = new Diamond();
		Loot diamond2 = new Diamond();
		testBelle.setCharacter(ECharacter.BELLE);
		testDjango.setCharacter(ECharacter.DJANGO);
		List<Character> testCharacters = new ArrayList<Character>();
		testBelle.setUserId(1234);
		testDjango.setUserId(12);
		
		assertEquals(testBelle.getUserId(), 1234);
		testCharacters.add(testDjango);
		
		Loot arrayDiamond1 = new Diamond();
		List<Loot> testloot = new ArrayList<Loot>();
		testloot.add(arrayDiamond1);

		testLevel.setBaseLevel(true);
		assertEquals(testLevel.isBaseLevel(), true);
		
		testLevel.setGameId(2);
		assertEquals(testLevel.getGameId(), 2);
		
		testLevel.setMarshal(false);
		assertEquals(testLevel.isMarshal(), false);
		
		//test loot methods
		
		testLevel.addLoot(diamond1);
		testLevel.addLoot(diamond2);
		assertEquals(testLevel.getLoot().get(0), diamond1);
		assertEquals(testLevel.getLoot().get(1), diamond2);
		
		testLevel.removeLoot(diamond1);
		testLevel.removeLoot(diamond2);
		assertTrue(testLevel.getLoot().isEmpty());
		
		testLevel.setLoot(testloot);
		assertEquals(testLevel.getLoot(), testloot);
		
		//test character methods
		
		testLevel.addCharacter(testBelle);
		assertEquals(testLevel.getCharacters().get(0), testBelle);
		
		testLevel.removeCharacter(testBelle.getUserId());
		assertTrue(testLevel.getCharacters().isEmpty());
		
		testLevel.setCharacters(testCharacters);
		assertEquals(testLevel.getCharacters(), testCharacters);
		
		
	}

}
