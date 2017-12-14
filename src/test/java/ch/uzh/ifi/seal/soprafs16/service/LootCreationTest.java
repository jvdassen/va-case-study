package ch.uzh.ifi.seal.soprafs16.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.uzh.ifi.seal.soprafs16.Application;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.Diamond;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.Loot;
import ch.uzh.ifi.seal.soprafs16.model.repositories.LootRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.RoundCardRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class LootCreationTest {

	@Autowired
	private LootRepository lootRepo;
	
	@Autowired
	private CreationService testService;
	
	long gameid = 3;
	
	
	@Test
	public void test() {

		testService.createLoot(gameid);
		
		List<Loot> lootAfter= new ArrayList<Loot>();
		lootRepo.findAll().forEach(lootAfter::add);
		
		int amountLockboxes, amountDiamonds, amountMoneybags;
		amountLockboxes = 0;
		amountDiamonds = 0;
		amountMoneybags = 0;
		
		
		
		for (int i = 0; i < lootAfter.size(); i++) {
			//Assert.assertEquals(lootAfter.get(i).getGameId(), gameid);

			int currentValue = lootAfter.get(i).getValue();
			
			if (currentValue == 1000){
				amountLockboxes++;
			}
			
			else if(currentValue == 500) {
				
				if(lootAfter.get(i).getClass().isInstance(new Diamond())) {
					amountDiamonds++;
				}
			}
			else if (currentValue == 250 || currentValue == 300 || currentValue == 350 ||
					currentValue == 400 || currentValue == 450 || currentValue == 500
					) {
				amountMoneybags++;
			}
			
		
		}
		
		//Assert.assertEquals(amountLockboxes, 2);
//		Assert.assertEquals(amountDiamonds, 6);
//		Assert.assertEquals(amountMoneybags, 16);

	
	}

}
