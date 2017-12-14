package ch.uzh.ifi.seal.soprafs16.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.uzh.ifi.seal.soprafs16.Application;
import ch.uzh.ifi.seal.soprafs16.constant.ECharacter;
import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CharacterRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CharacterCreationServiceTest {
	
	@Autowired
	private CreationService testCreationService;
	long gameid = 3;


    @Autowired
    private CharacterRepository charRepo;


	@Test
	public void testUserCreation() {
		
		testCreationService.createCharacters(gameid);
		
		List<Character> charactersAfter= new ArrayList<Character>();
        charRepo.findAll().forEach(charactersAfter::add);
		
        //Assert.assertEquals(charactersAfter.size(), 6);
        
//        Assert.assertEquals(charactersAfter.get(0).getCharacter(), ECharacter.TUCO);
//        Assert.assertEquals(charactersAfter.get(1).getCharacter(), ECharacter.CHEYENNE);
//        Assert.assertEquals(charactersAfter.get(2).getCharacter(), ECharacter.BELLE);
//        Assert.assertEquals(charactersAfter.get(3).getCharacter(), ECharacter.DJANGO);
//        Assert.assertEquals(charactersAfter.get(4).getCharacter(), ECharacter.DOC);
//        Assert.assertEquals(charactersAfter.get(5).getCharacter(), ECharacter.GHOST);
//
//        Assert.assertEquals(charactersAfter.get(0).getGameId(), gameid);
//        Assert.assertEquals(charactersAfter.get(1).getGameId(), gameid);
//        Assert.assertEquals(charactersAfter.get(2).getGameId(), gameid);
//        Assert.assertEquals(charactersAfter.get(3).getGameId(), gameid);
//        Assert.assertEquals(charactersAfter.get(4).getGameId(), gameid);
//        Assert.assertEquals(charactersAfter.get(5).getGameId(), gameid);        
//
//        Assert.assertEquals(charactersAfter.get(0).getSpecialAbility().getCharacter(), ECharacter.TUCO);
//        Assert.assertEquals(charactersAfter.get(1).getSpecialAbility().getCharacter(), ECharacter.CHEYENNE);
//        Assert.assertEquals(charactersAfter.get(2).getSpecialAbility().getCharacter(), ECharacter.BELLE);
//        Assert.assertEquals(charactersAfter.get(3).getSpecialAbility().getCharacter(), ECharacter.DJANGO);
//        Assert.assertEquals(charactersAfter.get(4).getSpecialAbility().getCharacter(), ECharacter.DOC);
//        Assert.assertEquals(charactersAfter.get(5).getSpecialAbility().getCharacter(), ECharacter.GHOST);

        charRepo.deleteAll();
		

	}

}
