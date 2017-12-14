package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.constant.ECharacter;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOMove;
import ch.uzh.ifi.seal.soprafs16.model.environment.Train;
import ch.uzh.ifi.seal.soprafs16.model.environment.TrainLevel;
import ch.uzh.ifi.seal.soprafs16.model.environment.TrainWagon;
import ch.uzh.ifi.seal.soprafs16.model.game.SpecialAbility;

import java.util.ArrayList;
import java.util.List;


public class TrainTest {

	@Test
	public void test() {
		int gameId = 1;
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
        
        //the train is now initialized
        assertEquals(testTrain.findMarshal(), 0);
        testTrain.moveMarshal(false);
        assertEquals(testTrain.findMarshal(), 2);
        testTrain.moveMarshal(false);
        assertEquals(testTrain.findMarshal(), 4);
        testTrain.moveMarshal(false);
        assertEquals(testTrain.findMarshal(), 6);
        testTrain.moveMarshal(false);
        assertEquals(testTrain.findMarshal(), 8);
        
        testTrain.moveMarshal(true);
        testTrain.moveMarshal(true);
        testTrain.moveMarshal(true);
        testTrain.moveMarshal(true);
        
        //findMarshal and moveMarshal tested
        //fill train with characters
        
        //test addcharacters

        /*testTrain.getWagons().get(1).getTrainLevels().get(0).addCharacter(testCharacters.get(0));
        testTrain.getWagons().get(1).getTrainLevels().get(0).addCharacter(testCharacters.get(1));
        testTrain.getWagons().get(3).getTrainLevels().get(0).addCharacter(testCharacters.get(2));
        testTrain.getWagons().get(4).getTrainLevels().get(1).addCharacter(testCharacters.get(3));*/


        testTrain.addCharacter(testCharacters.get(0), 2);
        testTrain.addCharacter(testCharacters.get(1), 2);
        testTrain.addCharacter(testCharacters.get(2), 6);
        testTrain.addCharacter(testCharacters.get(3), 9);

        //@LS auskommentiert da vermutlich unnötig
        //testTrain.fleeFromMarshal();
        assertTrue(testTrain.getWagons().get(0).getTrainLevels().get(0).getCharacters().isEmpty());
        assertTrue(testTrain.getWagons().get(0).getTrainLevels().get(1).getCharacters().isEmpty());

        testTrain.moveMarshal(false);
        List<Character> tempCharacters = new ArrayList<Character>();
        tempCharacters.add(testCharacters.get(0));
        tempCharacters.add(testCharacters.get(1));
        testTrain.removeFleeFromMarshal(tempCharacters);
        testTrain.addFleeFromMarshal(tempCharacters);

        assertTrue(testTrain.getWagons().get(1).getTrainLevels().get(0).getCharacters().isEmpty());
        assertFalse(testTrain.getWagons().get(1).getTrainLevels().get(1).getCharacters().isEmpty());
        
        assertEquals(testTrain.findUserInTrain(3), 9);
        assertEquals(testTrain.getCharacterInTrain(3), testCharacters.get(3));


        /*@LS not needed anymore
        Move testClimb = new DTOClimb();
        testClimb.setGameId(gameId);
        testClimb.setUserId(3);*/
        int testPos  = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.climbCharacter(testCharacters.get(3), testPos);
        assertTrue(testTrain.getWagons().get(4).getTrainLevels().get(1).getCharacters().isEmpty());
        assertFalse(testTrain.getWagons().get(4).getTrainLevels().get(0).getCharacters().isEmpty());

        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.climbCharacter(testCharacters.get(3), testPos);
        assertFalse(testTrain.getWagons().get(4).getTrainLevels().get(1).getCharacters().isEmpty());
        assertTrue(testTrain.getWagons().get(4).getTrainLevels().get(0).getCharacters().isEmpty());

        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.climbCharacter(testCharacters.get(3), testPos);
        
        /*
         * move a character through the entire train in all directions,
         * using both move methods + walk into marshall from both sides
         */
        
        DTOMove testMove = new DTOMove();
        testMove.setGameId(gameId);
        testMove.setUserId(3);
        testMove.setLeft(true);
        testMove.setDistance(1);

        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        /*testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);*/
        assertEquals(testTrain.findUserInTrain(3), 0);

        testMove.setLeft(false);

        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        /*testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);*/
        assertEquals(testTrain.findUserInTrain(3), 8);

        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.climbCharacter(testCharacters.get(3), testPos);
        testMove.setLeft(true);

        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        /*testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);*/
        assertEquals(testTrain.findUserInTrain(3), 1);

        
        testMove.setLeft(false);

        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);
        /*testPos = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos, testMove);*/

        assertEquals(testTrain.findUserInTrain(3), 9);
        
        int testPos2 = testTrain.findUserInTrain(3);
        //testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, false);
        assertEquals(testTrain.findUserInTrain(3), 9);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, true);
        assertEquals(testTrain.findUserInTrain(3), 7);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, true);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, true);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, true);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, true);
        assertEquals(testTrain.findUserInTrain(3), 1);
        
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, false);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, false);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, false);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, false);
        testPos2 = testTrain.findUserInTrain(3);

        assertEquals(testTrain.findUserInTrain(3), 9);
        testTrain.removeCharacterInTrain(3);
        testTrain.climbCharacter(testCharacters.get(3), testPos2);
        
        assertEquals(testTrain.findUserInTrain(3), 8);

        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, true);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, true);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, true);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, true);
        testTrain.removeCharacterInTrain(3);
        testTrain.climbCharacter(testCharacters.get(3), testPos2);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, true);
        assertEquals(testTrain.findUserInTrain(3), 0);
        
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, false);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, false);
        testTrain.removeCharacterInTrain(3);
        testTrain.climbCharacter(testCharacters.get(3), testPos2);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, false);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, false);
        testPos2 = testTrain.findUserInTrain(3);
        testTrain.removeCharacterInTrain(3);
        testTrain.moveUser(testCharacters.get(3), testPos2, false);
        
        assertEquals(testTrain.findUserInTrain(3), 8);

        


        testTrain.removeCharacterInTrain(3);
        assertTrue(testTrain.getWagons().get(4).getTrainLevels().get(0).getCharacters().isEmpty());
	}
}
