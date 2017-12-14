package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.ifi.seal.soprafs16.constant.LootType;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.Lockbox;
/*
 * @author Luc Boillat
 */
public class LockboxTest {

	@Test
	public void test() {
		
		Lockbox testLockbox = new Lockbox();

		testLockbox.setLootType(LootType.LOCKBOX);
		assertEquals(testLockbox.getValue(), 1000);
		assertEquals(testLockbox.getLootType(), LootType.LOCKBOX);
		
	}
	
}
