package ch.uzh.ifi.seal.soprafs16.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import ch.uzh.ifi.seal.soprafs16.model.ShortGame;
import ch.uzh.ifi.seal.soprafs16.model.repositories.ShortGameRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.uzh.ifi.seal.soprafs16.Application;
import ch.uzh.ifi.seal.soprafs16.model.ESpecialEvent;
import ch.uzh.ifi.seal.soprafs16.model.RoundCard;
import ch.uzh.ifi.seal.soprafs16.model.Train;
import ch.uzh.ifi.seal.soprafs16.model.repositories.RoundCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.TrainRepository;
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RoundCardCreationTest {

    @Autowired
    private RoundCardRepository roundCardRepo;
	long gameid = 3;
	
	@Autowired
	private CreationService testCreationService;

    //@LS
    @Autowired
    private ShortGameRepository sgRepo;
	@Before
	public void clearRepos(){

	}

	@Test
	public void testTrainCreation() {
		
		/*
		 * Invoke the creation method
		 */
		
        ShortGame sg = new ShortGame();
        sg.setGameId(gameid);
        sg.setShortV(false);
        sgRepo.save(sg);
		testCreationService.createRoundCards(gameid);

		
		/*
		 * get all the roundcards after the creation method has executed 
		 * and check if they are valid
		 */
       ArrayList<RoundCard> cardsAfter = new ArrayList<RoundCard>();
       roundCardRepo.findAll().forEach(cardsAfter::add);
       System.out.println(cardsAfter.size());
       //Assert.assertEquals(cardsAfter.get(0).getCurrentTurn(), 2);
       
       for (int i = 0; i< cardsAfter.size(); i++) {
           //Assert.assertEquals(cardsAfter.get(i).getCurrentTurn(), 1);
    	   Assert.assertNotNull(cardsAfter.get(i).getTurns());
       }
       
       
       Assert.assertEquals(cardsAfter.get(0).isTrainStation(), true);
       Assert.assertEquals(cardsAfter.get(1).isTrainStation(), true);
       Assert.assertEquals(cardsAfter.get(2).isTrainStation(), true);
       Assert.assertEquals(cardsAfter.get(3).isTrainStation(), false);
       
       
       ESpecialEvent firstevent;
       firstevent = cardsAfter.get(0).getSpecialEvent().getSpecialEvent();
       
       if (firstevent  == ESpecialEvent.HOSTAGE ||
    		   firstevent  == ESpecialEvent.REVENGE_MARSHAL ||
    		   firstevent  == ESpecialEvent.POCKET_PICKING) {
    	   
       }else {
    	   Assert.assertTrue(false);
       }
       
       sgRepo.deleteAll();
   	       
	}

}
