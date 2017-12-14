package ch.uzh.ifi.seal.soprafs16.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.uzh.ifi.seal.soprafs16.Application;
import ch.uzh.ifi.seal.soprafs16.constant.ECharacter;
import ch.uzh.ifi.seal.soprafs16.constant.ERound;
import ch.uzh.ifi.seal.soprafs16.constant.ESpecialEvent;
import ch.uzh.ifi.seal.soprafs16.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs16.model.*;
import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.environment.Train;
import ch.uzh.ifi.seal.soprafs16.model.environment.TrainLevel;
import ch.uzh.ifi.seal.soprafs16.model.environment.TrainWagon;
import ch.uzh.ifi.seal.soprafs16.model.game.Game;
import ch.uzh.ifi.seal.soprafs16.model.game.GameState;
import ch.uzh.ifi.seal.soprafs16.model.game.Round;
import ch.uzh.ifi.seal.soprafs16.model.game.SpecialAbility;
import ch.uzh.ifi.seal.soprafs16.model.game.SpecialEvent;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.CardStack;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.RoundCard;
import ch.uzh.ifi.seal.soprafs16.model.repositories.ActionCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.AmmoCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CardStackRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CharacterRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.GameRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.GameStateRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.LootRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.RoundCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.SERepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.TrainRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class SpecialEventServiceTest {
	
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

    @Autowired
    private FlowService flowService;

    @Autowired
    private SERepository seRepository;

	@Autowired
	private CreationService testService;
	
	@Autowired
	private SpecialEventService testSEService;
	long gameId = 1;
	
	@Test
	public void test() {
		
		//create trains and characters
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
            charRepo.save(newChar);
		
        ArrayList<TrainWagon> wagons = new ArrayList<TrainWagon>();
        for(int j=0; j<4; j++)
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
        Train train = new Train();
        train.setGameId(gameId);
        train.setWagons(wagons);
        trainRepo.save(train);
        
        GameState testState = new GameState();
        Round testRound = new Round();
        testRound.setCurrentRound(ERound.FIVE);
        testRound.setGameId(gameId);
        testRound.setCurrentTurn(5);
        testState.setRound(testRound);
        testState.setGameId(gameId);
        gsRepo.save(testState);
        
        RoundCard testCard = new RoundCard();
        SpecialEvent testEvent = new SpecialEvent();
        testEvent.setGameId(gameId);
        testEvent.setSpecialEvent(ESpecialEvent.BRAKE);
        testCard.setSpecialEvent(testEvent);
        testCard.setCurrentTurn(5);
        testCard.setGameId(gameId);
        testCard.setTrainStation(false);
        testCard.setMaxTurns(5);
        rcRepo.save(testCard);
       
        
        CardStack testStack = new CardStack();
        testStack.setGameId(gameId);
        testStack.addRoundCardToStack(testCard);
        
        csRepo.save(testStack);

        Game testGame = new Game();
        testGame.setId(new Long(1));
        testGame.setName("game1");
        testGame.setOwner("user1");
        testGame.setStatus(GameStatus.RUNNING);
        gameRepo.save(testGame);
        
        //testSEService.doSpecialEvent(gameId);
	}
}
}
