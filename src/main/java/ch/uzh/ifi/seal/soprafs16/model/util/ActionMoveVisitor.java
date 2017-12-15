package ch.uzh.ifi.seal.soprafs16.model.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.User;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOClimb;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOInvalid;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOMarshal;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOMove;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOPunch;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTORob;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOShoot;
import ch.uzh.ifi.seal.soprafs16.model.environment.Train;
import ch.uzh.ifi.seal.soprafs16.model.environment.TrainLevel;
import ch.uzh.ifi.seal.soprafs16.model.environment.TrainWagon;
import ch.uzh.ifi.seal.soprafs16.model.game.Move;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.AmmoCard;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.CardStack;
import ch.uzh.ifi.seal.soprafs16.model.repositories.ActionCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.AmmoCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CardStackRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CharacterRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.GameRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.GameStateRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.LootRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.MoveRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.TrainRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.UserRepository;
import ch.uzh.ifi.seal.soprafs16.service.AbilityCheck;

public class ActionMoveVisitor implements Visitor {
    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private LootRepository lootRepo;

    @Autowired
    private TrainRepository trainRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ActionCardRepository actionCardRepo;

    @Autowired
    private AmmoCardRepository ammoCardRepo;

    @Autowired
    private CardStackRepository allCards;

    @Autowired
    private CharacterRepository charRepo;

    @Autowired
    private GameStateRepository gameStateRepo;

    @Autowired
    private MoveRepository moveRepo;

    @Autowired
    private AbilityCheck abilityService;
    
    Train completeTrain;
    CardStack cardStack;
    List<User> players;
    List<Move> allResultingMoves;
    
    int marshalPosition;
    TrainWagon trainWagonWithMarshal;
    TrainLevel trainLevelWithMarshal;
    
    long movingUser;
    long victim;
    int movingUserPosition;
    int victimPosition;
    Character movingCharacter;
    List<AmmoCard> ammoCardsOfMovingUser;
    
    public  ActionMoveVisitor(long gameId){
        completeTrain = trainRepo.findByGameId(gameId).get(0);
        cardStack = allCards.findByGameId(gameId).get(0);
        players = gameRepo.findOne(gameId).getPlayers();
        allResultingMoves = new ArrayList<Move>();

        marshalPosition = completeTrain.findMarshal();
        trainWagonWithMarshal = completeTrain.getWagons().get(marshalPosition);
        trainLevelWithMarshal = trainWagonWithMarshal.getTrainLevels().get(0);
    }
	
    
    public void setUp(Move move){
        movingUser = move.getUserId();
        victim = ((DTOShoot) move).getVictim().getId();

        movingUserPosition = completeTrain.findUserInTrain(movingUser);
        victimPosition = completeTrain.findUserInTrain(victim);
        
        movingCharacter = charRepo.findFirstByUserId(movingUser);
        ammoCardsOfMovingUser = ammoCardRepo.findByUserId(movingUser);
    }

	public List<Move> visit(DTOClimb move) {
		setUp(move);
		return null;
	}

	@Override
	public List<Move> visit(DTOInvalid move) {
		setUp(move);
		return null;
	}

	@Override
	public List<Move> visit(DTOMarshal move) {
		setUp(move);
		return null;
	}

	@Override
	public List<Move> visit(DTOMove move) {
		setUp(move);
		return null;
	}

	@Override
	public List<Move> visit(DTOPunch move) {
		setUp(move);
		return null;
	}

	@Override
	public List<Move> visit(DTORob move) {
		setUp(move);
		return null;
	}

	@Override
	public List<Move> visit(DTOShoot move) {
		setUp(move);
		return null;
	}
}
