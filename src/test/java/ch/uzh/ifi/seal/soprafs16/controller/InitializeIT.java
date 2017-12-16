package ch.uzh.ifi.seal.soprafs16.controller;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import ch.uzh.ifi.seal.soprafs16.Application;
import ch.uzh.ifi.seal.soprafs16.constant.ECharacter;
import ch.uzh.ifi.seal.soprafs16.constant.EPhase;
import ch.uzh.ifi.seal.soprafs16.constant.LootType;
import ch.uzh.ifi.seal.soprafs16.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.User;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOClimb;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOInvalid;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOLoot;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOMarshal;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOMove;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOPunch;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTORob;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOShoot;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOSpecialEvent;
import ch.uzh.ifi.seal.soprafs16.model.dto.Move;
import ch.uzh.ifi.seal.soprafs16.model.environment.Train;
import ch.uzh.ifi.seal.soprafs16.model.game.Game;
import ch.uzh.ifi.seal.soprafs16.model.game.GameState;
import ch.uzh.ifi.seal.soprafs16.model.game.SpecialAbility;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.ActionCard;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.AmmoCard;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.CardStack;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.RoundCard;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.Loot;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.MoneyBag;
import ch.uzh.ifi.seal.soprafs16.model.repositories.ActionCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.AmmoCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CardStackRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CharacterRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.GameRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.GameStateRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.LootRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.RoundCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.ShortGameRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.TrainRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
public class InitializeIT {

	 @Value("${local.server.port}")
	    private int          port;

	    private URL          base;
	    private RestTemplate template;

	    @Autowired
	    private GameRepository gameRepo;

	    @Autowired
	    private LootRepository lootRepo;

	    @Autowired
	    private TrainRepository trainRepo;

	    @Autowired
	    private RoundCardRepository rcRepo;

	    @Autowired
	    private GameStateRepository gsRepo;

	    @Autowired
	    private UserRepository userRepo;

	    @Autowired
	    private ActionCardRepository actionCardRepo;

	    @Autowired
	    private AmmoCardRepository ammoCardRepo;

	    @Autowired
	    private CardStackRepository csRepo;

	    @Autowired
	    private CharacterRepository charRepo;

	    @Before
	    public void setUp()
	            throws Exception {
	        this.base = new URL("http://localhost:" + port + "/");
	        this.template = new TestRestTemplate();
//	    	gameRepo.deleteAll();
//	    	lootRepo.deleteAll();
//	    	trainRepo.deleteAll();
//	    	gsRepo.deleteAll();
//	    	userRepo.deleteAll();
//	    	actionCardRepo.deleteAll();
//	    	ammoCardRepo.deleteAll();
//	    	csRepo.deleteAll();
//	    	charRepo.deleteAll();
	    }
	    
	    @Test
	    @SuppressWarnings("unchecked")
	    public void testCreateGameSuccess() {
	     

	    	/*
	    	 * Make sure that there are no users in the beginning
	    	 */
	        List<User> usersBefore = template.getForObject(base + "/users", List.class);
	        Assert.assertEquals(0, usersBefore.size());
	    	
	    	
	    	/*
	    	 * Create a sample User to create a new game
	    	 */
	        User sampleUser1 = new User();
	        sampleUser1.setName("Mike Meyers");
	        sampleUser1.setUsername("userone");
	        
	    	/*
	    	 * POST a user 
	    	 */
	        HttpEntity<User> httpEntity = new HttpEntity<User>(sampleUser1);

	        ResponseEntity<User> userResponse1 = template.exchange(base + "/users/", HttpMethod.POST, httpEntity, User.class);
	        
	        Assert.assertSame(1L, userResponse1.getBody().getId());
	    	
	    	
	        /*
	         * Test that the user was added successfully
	         */
	        List<User> usersAfter = template.getForObject(base + "/users", List.class);
	        Assert.assertEquals(1, usersAfter.size());

	        ResponseEntity<User> userResponseEntity = template.getForEntity(base + "/users/" + userResponse1.getBody().getId(), User.class);
	        /*
	         * Check that the posted User is set correctly
	         */
	        assertEquals(userResponse1.getBody().getStatus(), UserStatus.OFFLINE);
	        assertNotEquals(userResponse1.getBody().getToken(), null);
	        assertEquals(userResponse1.getBody().getId(), new Long(1));
	        assertEquals(userResponse1.getBody().getName(), sampleUser1.getName());
	        assertEquals(userResponse1.getBody().getUsername(), sampleUser1.getUsername());
	        assertEquals(userResponse1.getBody().getGames(), null);
	        
	        /*
	         * create another testuser for further testing
	         */
	        User sampleUser2 = new User();
	        sampleUser2.setName("John Doe");
	        sampleUser2.setUsername("jdoe");
	        
	        HttpEntity<User> httpEntityJD = new HttpEntity<User>(sampleUser2);

	        ResponseEntity<User> userResponse2 = template.exchange(base + "/users/", HttpMethod.POST, httpEntityJD, User.class);
	        List<User> usersAfter2 = template.getForObject(base + "/users", List.class);
	    	/*
	    	 * check that there are 2 users now
	    	 */
	    	Assert.assertEquals(2, usersAfter2.size());
	    	
	    	
	    	/*
	    	 * Check that there are no games at the beginning
	    	 */
	        List<Game> gamesBefore = template.getForObject(base + "/games", List.class);
	        Assert.assertEquals(0, gamesBefore.size());
	    	
	        
	        
	    	/*
	    	 * POST a game 
	    	 */
	        HttpEntity<User> httpEntityGame = new HttpEntity<User>(sampleUser1);
	        ResponseEntity<String> gameResponse = template.exchange(base + "/games?token=" +userResponse1.getBody().getToken(), HttpMethod.POST, httpEntityGame, String.class);
	       
	        /*
	        * Checkt that the created game has game ID 1, save the gameid for further testing
	        */
	        String gameId = gameResponse.getBody();
	        Assert.assertEquals(gameId, "1");
	        	        
	        /*
	         * post a game with an invalid user/token and check that the game was not created
	         */
	        ResponseEntity<String> invalidGameResponse = template.exchange(base + "/games?token=" , HttpMethod.POST, httpEntityGame, String.class);
	    	Assert.assertEquals("0", invalidGameResponse.getBody());
	    	
	        
	        
	        /*
	         * Check that there is now a game 
	         */
	        List<Game> gamesAfter = template.getForObject(base + "/games", List.class);
	        Assert.assertEquals(1, gamesAfter.size());
	        
	        Game postedGame = template.getForObject(base + "/games/" + gameId, Game.class);
	        Assert.assertEquals(postedGame.getOwner(), "userone");
	        /*
	         * Declare the posted game as short game
	         */
	        String shortGameResponse = template.getForObject(base + "games/1/shorts", String.class);

	        
	        /*
	         * Test that the games name and owner were set to the user that made the post request and that an ID was
	         * 
	         */
	        Assert.assertEquals(postedGame.getName(), userResponse1.getBody().getName());
	        Assert.assertEquals(postedGame.getOwner(), userResponse1.getBody().getUsername());
	        Assert.assertNotEquals(postedGame.getId(), null);
	        /*
	         * check that null is returned if the game does not exist
	         */
	        List<User> usersInInvalidGame = template.getForObject(base + "/games/99/players", List.class);
	        Assert.assertEquals(null, usersInInvalidGame); 
	        /*
	         * Post our 2 sample users to the game
	         */
	        List<User> usersInGameBefore = template.getForObject(base + "/games/1/players", List.class);
	        Assert.assertEquals(0, usersInGameBefore.size());
	        
	        
	        HttpEntity<User> httpEntityUser1 = new HttpEntity<User>(sampleUser1);
	        ResponseEntity<String> playerid1 = template.exchange(base + "/games/1/players?token=" +userResponse1.getBody().getToken(), HttpMethod.POST, httpEntityUser1, String.class);
	        /*
	         * check that the returned id is correct
	         */
	        Assert.assertEquals(userResponse1.getBody().getId().toString(), playerid1.getBody());
	        
	        List<User> usersInGameAfter = template.getForObject(base + "/games/1/players", List.class);
	       /*
	        * check that there is now 1 user in the game
	        */
	        Assert.assertEquals(1, usersInGameAfter.size());
	        
	        HttpEntity<User> httpEntityUser2 = new HttpEntity<User>(sampleUser1);
	        ResponseEntity<String> playerid2 = template.exchange(base + "/games/1/players?token=" +userResponse2.getBody().getToken(), HttpMethod.POST, httpEntityUser2, String.class);
	        /*
	         * check that the returned id is correct
	         */
	        Assert.assertEquals(userResponse2.getBody().getId().toString(), playerid2.getBody());
	        
	        usersInGameAfter = template.getForObject(base + "/games/1/players", List.class);
	        /*
	         * check that there are now 2 users in the game
	         */
	        Assert.assertEquals(2, usersInGameAfter.size());
	        
	        /*
	         * check that we cannot add an invalid user to a game
	         */
	        User invalidUser = new User();
	        HttpEntity<User> httpEntityinvalidUser = new HttpEntity<User>(invalidUser);
	        /*we dont send a token as the user is not valid*/
	        ResponseEntity<String> invalidPlayerResponse = template.exchange(base + "/games/1/players?token=" , HttpMethod.POST, httpEntityUser2, String.class);
	        /*
	         * check that we cannpt add a user to an invalid game
	         */
	        ResponseEntity<String> invalidGameResponse2 = template.exchange(base + "/games/99/players?token="+ userResponse1.getBody().getToken() , HttpMethod.POST, httpEntityUser2, String.class);
	        Assert.assertEquals("0", invalidGameResponse2.getBody());
	        
	        /*
	         * check that 0 was returned for an unsuccessful
	         */
	        Assert.assertEquals("0", invalidPlayerResponse.getBody());
	        
	        usersInGameAfter = template.getForObject(base + "/games/1/players", List.class);
	        /*
	         * check that there are still only 2 users in the game
	         */
	        Assert.assertEquals(2, usersInGameAfter.size());
	     
	        
	        
	        /*
	         * retrieve players via their id 
	         */
	        User specificUserInGame = template.getForObject(base + "/games/1/players/" + userResponse1.getBody().getId(), User.class);

	        Assert.assertEquals(sampleUser1.getName(), specificUserInGame.getName());
	        Assert.assertEquals(sampleUser1.getUsername(), specificUserInGame.getUsername());

	        specificUserInGame = template.getForObject(base + "/games/1/players/" + userResponse2.getBody().getId(), User.class);

	        Assert.assertEquals(sampleUser2.getName(), specificUserInGame.getName());
	        Assert.assertEquals(sampleUser2.getUsername(), specificUserInGame.getUsername());
	        

	       	/*
	         * check that a user that is not the owner cannot start the game
	         */
	        /*we dont send a token as the user is not valid*/
	        ResponseEntity<String> gameStartResponse = template.exchange(base + "/games/1/start?token=" + userResponse2.getBody().getToken() , HttpMethod.POST, httpEntityinvalidUser, String.class);

	       	/*
	         * check that a user cannot start an invalid game
	         */
	        httpEntityinvalidUser = new HttpEntity<User>(sampleUser1);
	        /*we dont send a token as the user is not valid*/
	        gameStartResponse = template.exchange(base + "/games/99/start?token=" + userResponse2.getBody().getToken() , HttpMethod.POST, httpEntityinvalidUser, String.class);
	        
	        
	        /*
	         * Check that there are now 12 unused characters
	         */
	        List<Character> characters = template.getForObject(base + "/characters/", List.class);
	        Assert.assertEquals(6, characters.size());
	        
	        /*
	         * POST a character, BELLE, to the user we created earlier
	         */
	        Character requestCharacter = new Character();
	        requestCharacter.setCharacter(ECharacter.DJANGO);
	        requestCharacter.setGameId(1);
	        requestCharacter.setUserId(1);
	        SpecialAbility testAbility = new SpecialAbility();
	        testAbility.setCharacter(ECharacter.DJANGO);
	        testAbility.setGameId(1);
	        requestCharacter.setSpecialAbility(testAbility);
	        

	        HttpEntity<Character> characterPost = new HttpEntity<Character>(requestCharacter);
	        ResponseEntity<String> characterPostResponse = template.exchange(base + "/characters/1/1" , HttpMethod.POST, characterPost, String.class);

	        Character requestCharacter2 = new Character();
	        requestCharacter2.setCharacter(ECharacter.CHEYENNE);
	        testAbility.setCharacter(ECharacter.CHEYENNE);
	        requestCharacter2.setSpecialAbility(testAbility);
	        requestCharacter2.setGameId(1);
	        requestCharacter2.setUserId(2);

	        HttpEntity<Character> characterPost2 = new HttpEntity<Character>(requestCharacter2);
	        
	        ResponseEntity<String> characterPostResponse2 = template.exchange(base + "/characters/1/2"  , HttpMethod.POST, characterPost2, String.class);
	        
	        /*
	         * error prone TODO
	         */
	        HttpEntity<User> httpEntityOwner = new HttpEntity<User>(sampleUser1);
	        gameStartResponse = template.exchange(base + "/games/1/start?token=" + userResponse1.getBody().getToken() , HttpMethod.POST, httpEntityOwner, String.class);

	        
	        //	        /*
//	        test return value as soon as a return value is implemented in the /characters/{gameid}/{userid}
//	        */
//	        
//	        System.out.println(characterPostResponse);
	        
	        
	        List<Character> charactersFree = template.getForObject(base + "/characters/free/1", List.class);
	        Assert.assertEquals(4, charactersFree.size());
	        /*
	         * retrieve cardstack
	         */
	        
	        List<CardStack> cardStack = template.getForObject(base + "/characters/stacks", List.class);

	        /*
	         * Checkt that there is now a valid roundcard assigned for our game
	         */
	        RoundCard currentroundcard = template.getForObject(base + "/roundcards/" + 1, RoundCard.class);
//	        Assert.assertEquals(gameId, currentroundcard.getGameId().toString());
//	        Assert.assertNotNull(currentroundcard.getCardNum());
//	        
//	        if (currentroundcard.getCardNum() <= 0 || currentroundcard.getCardNum() >7){
//	        	Assert.assertTrue(false);
//	        }
//	        else {
//	        	Assert.assertTrue(true);
//	        }	        
//	        
//	        Assert.assertNotNull(currentroundcard.getTurns());
//	        Assert.assertNotNull(currentroundcard.getSpecialEvent());
	        /*
	         * get all roundcards
	         */
	        List<RoundCard> allRoundCards = template.getForObject(base + "/roundcards" , List.class);
	        Assert.assertNotEquals(null, allRoundCards);
	        
	        /*
	         * Create a sample actioncard
	         */
	        
	        ActionCard testAction = new ActionCard();
	        testAction.setCardNum(3);
	        testAction.setGameId(new Long(1));
	        testAction.setUserId(1);
	        
	        /*
	         * POST sample actioncard to deck
	         */
	        
	        HttpEntity<ActionCard> postCard = new HttpEntity<ActionCard>(testAction);
	        
	        ResponseEntity<String> actionResponse = template.exchange(base + "/actioncards/" + "1/" + "1" , HttpMethod.POST, postCard, String.class);
	        ResponseEntity<String> drawResponse = template.exchange(base + "/actioncards/" + "1/" + "draw" , HttpMethod.POST, postCard, String.class);

	        /*  
		     * no return type
		     * TODO test returned value after implementation
		     */
	        List<ActionCard> actionCardsBefore = template.getForObject(base + "/actioncards", List.class);
	        Assert.assertNotEquals(0, actionCardsBefore.size());

	        /*
	         * 
	         */
	        ActionCard actionCardsInGame = template.getForObject(base + "/actioncards/1", ActionCard.class);

	        Assert.assertNotEquals(null, actionCardsInGame);
	        
	        
	        
	        /*
	         * test that the played actioncard is the actioncard we posted before
	         */
	        ActionCard played = template.getForObject(base + "/actioncards/1/played", ActionCard.class);
	        Assert.assertEquals(testAction.getCardNum(), played.getCardNum());
	        Assert.assertEquals(testAction.getGameId(), played.getGameId());
	        Assert.assertEquals(testAction.getUserId(), played.getUserId());
	        
	        /*
	         * check that loot objects are created for the games
	         */
	        List<Loot> allLoot = template.getForObject(base + "/loot", List.class);
	        Assert.assertNotEquals(0, allLoot.size());
	        
	        /*
	         * check that there are no loot objects in an invalid game
	         */
	        List<Loot> lootBefore = template.getForObject(base + "/loot/2", List.class);
	        Assert.assertEquals(0, lootBefore.size());
	        /*
	         * check that there are loot objects assigned for a game
	         */
	        lootBefore = template.getForObject(base + "/loot/1", List.class);
	        Assert.assertNotNull(lootBefore);
	        
	        lootBefore = template.getForObject(base + "/loot/1", List.class);
	        Assert.assertNotNull(lootBefore);
	        /*
	         * check that there are loot objects assigned for a game and user
	         */
	        lootBefore = template.getForObject(base + "/loot/1/1", List.class);
	        Assert.assertNotNull(lootBefore);
	        // TODO
	        /*
	         * test that the initial values are all of value 250
	         */
	        
	        /*
	         * checkt that the username associated with the loot objects
	         */
	        String username1 = template.getForObject(base + "/loot/1/1/username", String.class);

	        
	        String usernameInvalidGame = template.getForObject(base + "/loot/2/1/username", String.class);
	        
	        int amountAmmocards;
	        
	        List<AmmoCard> AmmocardsBefore = template.getForObject(base + "/ammocards", List.class);
	        Assert.assertNotEquals(null, AmmocardsBefore);
	        amountAmmocards = AmmocardsBefore.size();
	        /*
	         * create a sample ammocard to post
	         */
	        AmmoCard testAmmoCard = new AmmoCard();
	        testAmmoCard.setGameId((long)1);
	        testAmmoCard.setVictim((long) 12345);
	        
	        /*
	         * post the ammocard to /actioncards
	         */
	        HttpEntity<AmmoCard> postAmmocard = new HttpEntity<AmmoCard>(testAmmoCard);
	        ResponseEntity<AmmoCard> postedAmmocard = template.exchange(base + "/ammocards" , HttpMethod.POST, postAmmocard, AmmoCard.class);

/*
	         * test the returnedammocard
	         */
	        Assert.assertEquals(testAmmoCard.getGameId(), postedAmmocard.getBody().getGameId());
	        Assert.assertEquals(testAmmoCard.getVictim(), postedAmmocard.getBody().getVictim());
	        
	        /*
	         * test that there is now 1 more ammocard than before
	         */
	        List<AmmoCard> ammocardsAfter = template.getForObject(base + "/ammocards", List.class);
	        Assert.assertEquals(amountAmmocards+1, ammocardsAfter.size());
	        /*
	         * 
	         */
	        List<AmmoCard> ammocardsInGame = template.getForObject(base + "/ammocards/1", List.class);
	        Assert.assertNotEquals(ammocardsInGame.size()+1, ammocardsAfter.size());
	        Assert.assertEquals(2*6+14, ammocardsInGame.size());
	        
	        /*
	         * check that there is only one gamestate as we have created only one game
	         */
	        List<GameState> initialState = template.getForObject(base + "/gamestates", List.class);
	        Assert.assertEquals(1, initialState.size());
	        
	        
	        GameState specificGameState = template.getForObject(base + "/gamestates/1", GameState.class);
	        
	        /*
	         * make sure that its the first players turn in planningphase
	         */
	        Assert.assertEquals("Mike Meyers", specificGameState.getPlayerTurn().getName());
	        Assert.assertEquals(EPhase.PLANNING, specificGameState.getPhase().getPhase());
	        
	        /*
	         * test the specific gamestate for game with gameid 1
	         */
	        
	        Assert.assertEquals(1, specificGameState.getGameId());
	        
	        //Assert.assertEquals(1, specificGameState.getPhase());
	        
	        String emptyS = "";
	        HttpEntity<String> empty = new HttpEntity<String>(emptyS);
	        
	        
/*
 * TODO test result
 */
		      	        
	        
	        /*
	         * make sure thath there are no moves posted at the beginning
	         */

	        Move lastMove = template.getForObject(base + "/moves/1/1", Move.class);
	        Assert.assertEquals(null, lastMove);
	        
	        /*
	         * create a DTO climb move
	         */
	        
	        DTOClimb climbMove = new DTOClimb();
	        climbMove.setBase(true);
	        climbMove.setGameId(1);
	        climbMove.setUserId(new Long(1));
	        
	        /*
	         * POST the climb move [working]
	         */
	        HttpEntity<DTOClimb> httpClimbMove = new HttpEntity<DTOClimb>(climbMove);
	        ResponseEntity<String> climbMoveResponse = template.exchange(base + "/moves/1/1/climb", HttpMethod.POST, httpClimbMove, String.class);

	        DTOClimb lastMoveAfterClimb1 = template.getForObject(base + "/moves/1/1", DTOClimb.class);

	        DTOClimb lastMoveAfterClimb2 = template.getForObject(base + "/moves/1/2", DTOClimb.class);

	        /*
	         * test that the last move is the posted move
	         */
	        Assert.assertEquals(climbMove.getGameId(), lastMoveAfterClimb1.getGameId());
	        Assert.assertEquals(climbMove.getUserId(), lastMoveAfterClimb1.getUserId());
	        Assert.assertEquals(climbMove.isBase(), lastMoveAfterClimb1.isBase());
	        
	        /*
	         * create a DTO move
	         */
	        DTOMove move = new DTOMove();
	        move.setGameId(1);
	        move.setUserId(2);
	        move.setLeft(false);
	        move.setDistance(2);
	        /*
	         * POST the move [working]
	         */
	        HttpEntity<DTOMove> httpMove = new HttpEntity<DTOMove>(move);
	        ResponseEntity<String> moveResponse = template.exchange(base + "/moves/1/2/move", HttpMethod.POST, httpMove, String.class);

	        /*
	         * test the last move
	         */
	        DTOMove lastMoveAfterMove = template.getForObject(base + "/moves/1/2", DTOMove.class);
	        DTOMove lastMoveAfterMove1 = template.getForObject(base + "/moves/1/1", DTOMove.class);
	        
	        Assert.assertEquals(move.getGameId(), lastMoveAfterMove.getGameId());
	        Assert.assertEquals(move.getUserId(), lastMoveAfterMove.getUserId());
	        Assert.assertEquals(move.getDistance(), lastMoveAfterMove.getDistance());
	        /*
	         * create a marshal move [working]
	         */
	        DTOMarshal marshalmove = new DTOMarshal();
	        marshalmove.setGameId(1);
	        marshalmove.setLeft(false);
	        marshalmove.setUserId(1);

	        HttpEntity<DTOMarshal> httpMarshal = new HttpEntity<DTOMarshal>(marshalmove);
	        ResponseEntity<String> marshalResponse = template.exchange(base + "/moves/1/1/marshal", HttpMethod.POST, httpMarshal, String.class);
	        
	        DTOMarshal lastMoveAfterMarshal = template.getForObject(base + "/moves/1/1", DTOMarshal.class);
	        DTOMarshal lastMoveAfterMarshal1 = template.getForObject(base + "/moves/1/2", DTOMarshal.class);

	        // Assert.assertEquals(marshalmove.getGameId(), lastMoveAfterMarshal.getGameId());
	        Assert.assertEquals(marshalmove.getUserId(), lastMoveAfterMarshal.getUserId());
	        
	        
	        /*
	         * post an invalid move
	         */
	        
	        DTOInvalid invalidMove = new DTOInvalid();
	        invalidMove.setUserId(2);
	        invalidMove.setGameId(1);	        
	        HttpEntity<DTOInvalid> httpInvalid = new HttpEntity<DTOInvalid>(invalidMove);
	        ResponseEntity<String> invalidResponse = template.exchange(base +"/moves/1/2/invalid", HttpMethod.POST, httpInvalid, String.class);
	        
	        DTOInvalid lastMoveAfterInvalid = template.getForObject(base + "/moves/1/1", DTOInvalid.class);
	        DTOInvalid lastMoveAfterInvalid1 = template.getForObject(base + "/moves/1/2", DTOInvalid.class);

	        Assert.assertEquals(1, lastMoveAfterInvalid.getGameId());
	        
	        Assert.assertEquals(2, lastMoveAfterInvalid1.getUserId());
	        
	        /*
	         * move the first character back down to post a shoot move
	         */
	        
	        DTOClimb secondClimbMove = new DTOClimb();
	        secondClimbMove.setBase(true);
	        secondClimbMove.setGameId(1);
	        secondClimbMove.setUserId(new Long(1));
	        
	        /*
	         * POST the second climb move [working]
	         */
	        HttpEntity<DTOClimb> httpSecondClimbMove = new HttpEntity<DTOClimb>(secondClimbMove);
	        ResponseEntity<String> secondClimbMoveResponse = template.exchange(base + "/moves/1/1/climb", HttpMethod.POST, httpSecondClimbMove, String.class);
	        
	        /*
	         * POST a shoot move [working]
	         */
	        DTOShoot shootMove = new DTOShoot();
	        shootMove.setGameId(1);
	        shootMove.setUserId(2);
	        sampleUser2.setId(new Long(1));
	        shootMove.setVictim(sampleUser2);
	        
	        HttpEntity<DTOShoot> httpShootMove = new HttpEntity<DTOShoot>(shootMove);
	        ResponseEntity<String> shootResponse = template.exchange(base + "/moves/1/2/shoot", HttpMethod.POST, httpShootMove, String.class);

	        DTOShoot lastMoveAfterShoot = template.getForObject(base + "/moves/1/1", DTOShoot.class);
	        DTOShoot lastMoveAfterShoot1 = template.getForObject(base + "/moves/1/2", DTOShoot.class);
	        
	        Assert.assertEquals(1, lastMoveAfterShoot.getUserId());
	        Assert.assertEquals(2, lastMoveAfterShoot1.getUserId());


	        /*
	         * POST a rob move
	         */
	        
	        DTORob robMove = new DTORob();
	        DTOLoot dtoloot = new DTOLoot();
	        dtoloot.setLootType(LootType.MONEYBAG);
	        dtoloot.setValue(300);
	        robMove.setDTOLoot(dtoloot);
	        robMove.setGameId(1);
	        robMove.setUserId(2);
	        
	        HttpEntity<DTORob> httpRobMove = new HttpEntity<DTORob>(robMove);
	        ResponseEntity<String> robResponse = template.exchange(base + "/moves/1/1/rob", HttpMethod.POST, httpRobMove, String.class);

	        DTORob lastMoveAfterRob = template.getForObject(base + "/moves/1/1", DTORob.class);
	        DTORob lastMoveAfterRob1 = template.getForObject(base + "/moves/1/2", DTORob.class);
	        
	        Assert.assertEquals(1, lastMoveAfterRob.getUserId());
	        Assert.assertEquals(2, lastMoveAfterRob1.getUserId());
	        Assert.assertNotEquals(null, lastMoveAfterRob.getGameId());

	        /*
	         *  post a punchmoveb 
	         */
	        
	        DTOPunch punchmove = new DTOPunch();
	        punchmove.setGameId(1);
	        punchmove.setUserId(2);
	        punchmove.setVictim(sampleUser1);
	        DTOLoot punchLoot = new DTOLoot(); 
	        punchLoot.setLootType(LootType.MONEYBAG);
	        punchLoot.setValue(300);
	        punchmove.setDTOLoot(punchLoot);


	        HttpEntity<DTOPunch> httpPunch = new HttpEntity<DTOPunch>(punchmove);

	        ResponseEntity<String> punchResponse = template.exchange(base + "/moves/1/2/punch", HttpMethod.POST, httpPunch, String.class);


	        
	        /*
	         * 
	         */
	        DTOSpecialEvent lastSpecialEvent = template.getForObject(base + "/specialevent/1/1", DTOSpecialEvent.class);
	        /*
	         * TODO test lastSpecialEvent
	         */
	        
	        /*
	         * End the game and get the end results
	         */
	        Map<Long, Integer> stopGame = template.getForObject(base + "games/1/stop", Map.class);
	        Assert.assertEquals(2, stopGame.size());
	        /*
	         * 
	         * TODO test /trains
	         */
	        List<Train> allTrainsBefore = template.getForObject(base + "/trains", List.class);
	        Assert.assertNotEquals(0, allTrainsBefore.size());

	        /*
	         * test that there are no trains in 'empty' games
	         */
	        ResponseEntity<Train> specificTrainInvalid = template.getForEntity (base + "/trains/2", Train.class);
	        Assert.assertEquals(null, specificTrainInvalid.getBody());

	        /*
	         * test that there is a train in game 1
	         */
	        template.getForObject (base + "/trains/1", Object.class);
	        Assert.assertEquals(null, specificTrainInvalid.getBody());
	        
	        String index = template.getForObject(base + "", String.class);
	        Assert.assertEquals("SoPra 2016 with Codeship!", index);
	        
	        /*
	         * let /play initialize all the characters and test for successful creation
	         */
	        //Boolean play = template.getForObject(base + "/play", boolean.class);
	        
	        //Assert.assertTrue(charactersStart);
	        
	        
	        
	    	/*
	    	 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<
	    	 */
	        System.out.println("<<<<<<<<<<<<<<");
	        User sampleUser3 = new User();
	        sampleUser3.setName("Mikey");
	        sampleUser3.setUsername("usert");
	        
		   HttpEntity<User> httpEntity3 = new HttpEntity<User>(sampleUser3);

	        ResponseEntity<User> userResponse3 = template.exchange(base + "/users/", HttpMethod.POST, httpEntity3, User.class);
	        
	        User sampleUser4 = new User();
	        sampleUser4.setName("Mikeyyyy");
	        sampleUser4.setUsername("usert1");
	        
		   HttpEntity<User> httpEntity4 = new HttpEntity<User>(sampleUser4);

	        ResponseEntity<User> userResponse4 = template.exchange(base + "/users/", HttpMethod.POST, httpEntity4, User.class);
	        

	        User sampleUser5 = new User();
	        sampleUser5.setName("johnny");
	        sampleUser5.setUsername("usert2");
	        
		   HttpEntity<User> httpEntity5 = new HttpEntity<User>(sampleUser5);

	        ResponseEntity<User> userResponse5 = template.exchange(base + "/users/", HttpMethod.POST, httpEntity5, User.class);
	        
		  HttpEntity<User> httpEntityGame2 = new HttpEntity<User>(sampleUser3);
	        ResponseEntity<String> gameResponse2 = template.exchange(base + "/games?token=" +userResponse3.getBody().getToken(), HttpMethod.POST, httpEntityGame2, String.class);

	        ResponseEntity<String> playerid3 = template.exchange(base + "/games/2/players?token=" +userResponse3.getBody().getToken(), HttpMethod.POST, httpEntity3, String.class);

	        ResponseEntity<String> playerid4 = template.exchange(base + "/games/2/players?token=" +userResponse4.getBody().getToken(), HttpMethod.POST, httpEntity4, String.class);
	        ResponseEntity<String> playerid5 = template.exchange(base + "/games/2/players?token=" +userResponse5.getBody().getToken(), HttpMethod.POST, httpEntity5, String.class);

		

	        Character requestCharacter3 = new Character();
	        requestCharacter3.setCharacter(ECharacter.GHOST);
	        requestCharacter3.setGameId(2);
	        requestCharacter3.setUserId(3);
	        SpecialAbility testAbility3 = new SpecialAbility();
	        testAbility3.setCharacter(ECharacter.GHOST);
	        testAbility3.setGameId(2);
	        requestCharacter3.setSpecialAbility(testAbility3);
	        

	        HttpEntity<Character> characterPost3 = new HttpEntity<Character>(requestCharacter3);
	        ResponseEntity<String> characterPostResponse3 = template.exchange(base + "/characters/2/3" , HttpMethod.POST, characterPost3, String.class);

	        Character requestCharacter4 = new Character();
	        requestCharacter4.setCharacter(ECharacter.DOC);
	        requestCharacter4.setGameId(2);
	        requestCharacter4.setUserId(4);
	        SpecialAbility testAbility4 = new SpecialAbility();
	        testAbility4.setCharacter(ECharacter.DOC);
	        testAbility4.setGameId(2);
	        requestCharacter4.setSpecialAbility(testAbility4);
	        

	        HttpEntity<Character> characterPost4 = new HttpEntity<Character>(requestCharacter4);
	        ResponseEntity<String> characterPostResponse4 = template.exchange(base + "/characters/2/4" , HttpMethod.POST, characterPost4, String.class);

		   Character requestCharacter5 = new Character();
	        requestCharacter5.setCharacter(ECharacter.BELLE);
	        requestCharacter5.setGameId(2);
	        requestCharacter5.setUserId(5);
	        SpecialAbility testAbility5 = new SpecialAbility();
	        testAbility5.setCharacter(ECharacter.BELLE);
	        testAbility5.setGameId(2);
	        requestCharacter3.setSpecialAbility(testAbility5);
	        

	        HttpEntity<Character> characterPost5 = new HttpEntity<Character>(requestCharacter5);
	        ResponseEntity<String> characterPostResponse5 = template.exchange(base + "/characters/2/5" , HttpMethod.POST, characterPost5, String.class);

	        HttpEntity<User> httpEntityOwner2 = new HttpEntity<User>(sampleUser3);
	        gameStartResponse = template.exchange(base + "/games/2/start?token=" + userResponse3.getBody().getToken() , HttpMethod.POST, httpEntityOwner2, String.class);
	    	
	        String shortGame2 = template.getForObject(base + "/games/2/shorts", String.class);
	        /*
	         * post a move to game 2
	         */
	        DTOClimb climbMove2 = new DTOClimb();
	        climbMove.setBase(true);
	        climbMove.setGameId(2);
	        climbMove.setUserId(new Long(3));
	        
	        /*
	         * POST the climb move [working]
	         */
	        HttpEntity<DTOClimb> httpClimbMove2 = new HttpEntity<DTOClimb>(climbMove2);
	        //ResponseEntity<String> climbMoveResponse2 = template.exchange(base + "/moves/2/3/climb", HttpMethod.POST, httpClimbMove2, String.class);
	        
	        DTOClimb lastMoveAfterClimb22 = template.getForObject(base + "/moves/2/3", DTOClimb.class);
	        DTOClimb lastMoveAfterClimb23 = template.getForObject(base + "/moves/2/4", DTOClimb.class);
	        DTOClimb lastMoveAfterClimb24 = template.getForObject(base + "/moves/2/4", DTOClimb.class);
	        /*
	    	 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<
	    	 */
	        System.out.println("<<<<<<<<<<<<<<");
	    }
	    
	    @After
	    public void clearRepos(){

	    	gameRepo.deleteAll();
	    	trainRepo.deleteAll();
	   
	    	actionCardRepo.deleteAll();

	    	charRepo.deleteAll();
	    	
	    	
	    	
	    	
	    }

}
