package ch.uzh.ifi.seal.soprafs16.model;


import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import ch.uzh.ifi.seal.soprafs16.constant.UserStatus;


import org.junit.Test;

public class UserTest {

	@Test
	public void test() {
		User testUser = new User();		
		
		List<Game> testGames = new ArrayList<Game>();
		Game game1 = new Game();
		
		testGames.add(game1);
		
		testUser.setId(new Long(2));
		assertEquals(testUser.getId(), new Long(2));
		
		testUser.setName("user1");
		assertEquals(testUser.getName(), "user1");
		
		testUser.setUsername("username1");
		assertEquals(testUser.getUsername(), "username1");
		
		testUser.setToken("token1");
		assertEquals(testUser.getToken(), "token1");
		
		testUser.setStatus(UserStatus.ONLINE);
		assertEquals(testUser.getStatus(), UserStatus.ONLINE);
		
		testUser.setGames(testGames);
		assertEquals(testUser.getGames(), testGames);
	}

}
