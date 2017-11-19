package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DTOClimbTest {

	@Test
	public void test() {
		DTOClimb testClimb = new DTOClimb();
		
		testClimb.setBase(true);
		assertEquals(testClimb.isBase(), true);
	}

}
