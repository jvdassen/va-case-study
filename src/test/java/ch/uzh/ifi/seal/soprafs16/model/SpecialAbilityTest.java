package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
/*
 * @author Luc Boillat
 */
public class SpecialAbilityTest {

	@Test
	public void test() {
		//initialize train &characters for django's test
		int gameId = 1;
		long lgameId = 1;
		List<Character> testCharacters = new ArrayList<Character>();
		for (int i=0;i<6;i++)
		{
			SpecialAbility sa = new SpecialAbility();
			sa.setGameId(gameId);
			switch (i)
			{
				case 0: sa.setCharacter(ECharacter.TUCO);
					break;
				case 1: sa.setCharacter(ECharacter.CHEYENNE);
					break;
				case 2: sa.setCharacter(ECharacter.BELLE);
					break;
				case 3: sa.setCharacter(ECharacter.DJANGO);
					break;
				case 4: sa.setCharacter(ECharacter.DOC);
					break;
				case 5: sa.setCharacter(ECharacter.GHOST);
					break;
			}

			Character newChar = new Character();
			newChar.setCharacter(sa.getCharacter());
			newChar.setGameId(gameId);
			newChar.setSpecialAbility(sa);
			newChar.setUserId(i);
			testCharacters.add(newChar);
		}

		ArrayList<TrainWagon> wagons = new ArrayList<TrainWagon>();
		for(int i=0; i<5; i++)
		{
			TrainLevel tL = new TrainLevel();
			List<TrainLevel> tLs = new ArrayList<TrainLevel>();
			tL.setGameId(gameId);
			tL.setBaseLevel(true);
			TrainLevel tLT = new TrainLevel();
			tLT.setGameId(gameId);
			tLT.setBaseLevel(false);
			tLs.add(tL);
			tLs.add(tLT);
			TrainWagon tW = new TrainWagon();
			tW.setGameId(gameId);
			tW.setTrainLevels(tLs);
			if (i==0)
			{
				tW.setLocomotive(true);
				tL.setMarshal(true);
			}
			else
			{
				tW.setLocomotive(false);
				tL.setMarshal(false);
			}
			wagons.add(tW);
		}

		Train testTrain = new Train();

		testTrain.setGameId(gameId);
		assertEquals(testTrain.getGameId(), 1);

		testTrain.setWagons(wagons);
		assertEquals(testTrain.getWagons(), wagons);
		//add characters to train
		testTrain.addCharacter(testCharacters.get(3), 4);
		testTrain.addCharacter(testCharacters.get(2), 5);

		//generate ammocard for django's test
		AmmoCard testCard = new AmmoCard();
		testCard.setBulletRound(6);
		testCard.setCharacter(ECharacter.DJANGO);
		testCard.setGameId(lgameId);
		testCard.setNeutral(false);
		testCard.setUserId(3);

		//generate dtoshoot
		DTOShoot testShoot = new DTOShoot();
		testShoot.setGameId(gameId);
		testShoot.setUserId(testCharacters.get(3).getUserId());
		testShoot.setVictimAmmoCard(testCard);

		//Ghost's action card
		ActionCard testActionCard = new ActionCard();
		testActionCard.setVisible(true);

		SpecialAbility testAbility = new SpecialAbility();

		testAbility.setCharacter(ECharacter.BELLE);
		assertEquals(testAbility.getCharacter(), ECharacter.BELLE);

		testAbility.setGameId(new Long (1234));
		assertEquals(testAbility.getGameId(), 1234);

		testAbility.doAbility(testActionCard);
		assertEquals(testActionCard.isVisible(), false);

		//django and cheyenne doability missing
		//idk how to test django because of atugenerated ids

		//cheyenne test
		DTOPunch testPunch = new DTOPunch();
		testPunch.setUserId(1);
		Diamond testDiamond = new Diamond();
		testDiamond.setGameId(lgameId);
		testDiamond.setUserId(0);

		testAbility.doAbility(testPunch, testDiamond);
		assertEquals(testDiamond.getUserId(), 1);

	}


}
