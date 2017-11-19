package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CharacterTest {

	@Test
	public void test() {
		
		Character testCharacter = new Character();
		
		testCharacter.setGameId(2);
		assertEquals(testCharacter.getGameId(), 2);
		
		testCharacter.setUserId(3);
		assertEquals(testCharacter.getUserId(), 3);
		
		testCharacter.setCharacter(ECharacter.BELLE);
		assertEquals(testCharacter.getCharacter(), ECharacter.BELLE);

		testCharacter.setCharacter(ECharacter.CHEYENNE);
		assertEquals(testCharacter.getCharacter(), ECharacter.CHEYENNE);
		
		testCharacter.setCharacter(ECharacter.DJANGO);
		assertEquals(testCharacter.getCharacter(), ECharacter.DJANGO);
		
		testCharacter.setCharacter(ECharacter.DOC);
		assertEquals(testCharacter.getCharacter(), ECharacter.DOC);
		
		testCharacter.setCharacter(ECharacter.GHOST);
		assertEquals(testCharacter.getCharacter(), ECharacter.GHOST);
		
		testCharacter.setCharacter(ECharacter.TUCO);
		assertEquals(testCharacter.getCharacter(), ECharacter.TUCO);
		
		SpecialAbility testSpecialAbility = new SpecialAbility();
		
		testSpecialAbility.setCharacter(testCharacter.getCharacter());
		testSpecialAbility.setGameId(testCharacter.getGameId());
		testCharacter.setSpecialAbility(testSpecialAbility);
		
		assertEquals(testCharacter.getGameId(), testSpecialAbility.getGameId());
		
		assertEquals(testCharacter.getCharacter(), testSpecialAbility.getCharacter());
		
		assertEquals(testCharacter.getSpecialAbility(), testSpecialAbility);
	
	}

}
