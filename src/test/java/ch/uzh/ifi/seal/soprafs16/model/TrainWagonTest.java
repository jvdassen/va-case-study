package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TrainWagonTest {

	@Test
	public void test() {
		TrainWagon testWagon = new TrainWagon();
		List<TrainLevel> testLevels = new ArrayList<TrainLevel>();
		TrainLevel top = new TrainLevel();
		TrainLevel bottom = new TrainLevel();
		
		testWagon.setGameId(2);
		assertEquals(testWagon.getGameId(), 2);
		
		testWagon.setLocomotive(true);
		assertEquals(testWagon.isLocomotive(), true);
		
		testLevels.add(top);
		testLevels.add(bottom);
		
		testWagon.setTrainLevels(testLevels);
		assertEquals(testWagon.getTrainLevels(), testLevels);
		
		
	}

}
