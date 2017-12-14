package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.model.dto.DTOMove;

public class DTOMoveTest {

	@Test
	public void test() {
		DTOMove testMove = new DTOMove();
		
		testMove.setLeft(true);
		assertEquals(testMove.isLeft(), true);
		
		testMove.setDistance(2);
		assertEquals(testMove.getDistance(), 2);
		
	}

}
