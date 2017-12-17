package ch.uzh.ifi.seal.soprafs16.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.soprafs16.constant.ETurn;
import ch.uzh.ifi.seal.soprafs16.model.dto.Move;
import ch.uzh.ifi.seal.soprafs16.model.dto.VisitableMove;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.ActionCard;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.CardStack;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.RoundCard;
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
import ch.uzh.ifi.seal.soprafs16.model.util.ActionMoveVisitor;


@Service("postService")
public class PostService {


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

    /*Adds an actioncard to the stack.*/
    public void addActionCardToStack(long gameId, ActionCard actionCard) {
        List<CardStack> cardStack = allCards.findByGameId(gameId);
        // Check if cards should be visible
        RoundCard rc = allCards.findByGameId(gameId).get(0).getCurrentRoundCard(gameStateRepo.findByGameId(gameId).getRound());
        if (rc.getTurns().get(rc.getCurrentTurn()).equals(ETurn.TUNNEL)) {
            actionCard.setVisible(false);
        }
        cardStack.get(0).addActionCardToStack(actionCard);
        allCards.save(cardStack);
        actionCardRepo.save(actionCard);
    }

    /*Adds a move to the stack*/
    public void addMoveToStack(long gameId, VisitableMove move)
    {
        List<Move> movesAfterMoveWasExecuted = applyMove(gameId, move);

        CardStack playedCards = allCards.findByGameId(gameId).get(0);
        for (Move executedMove : movesAfterMoveWasExecuted) {
        	playedCards.addMove(executedMove);
            moveRepo.save(executedMove);
        }
        allCards.save(playedCards);
    }

    /* Apply the consequences of the move onto the game
        @param move: The move the client has posted.
    */
    public List<Move> applyMove(long gameId, VisitableMove moveToApply) {
    	ActionMoveVisitor actionMoveVisitor = new ActionMoveVisitor(gameId);
    	//actionMoveVisitor.visit(moveToApply);
    	return moveToApply.accept(actionMoveVisitor);
    }
    public void applyAllMovesAgain(long gameId, List<Move> executedMoves) {
    	ActionMoveVisitor actionMoveVisitor = new ActionMoveVisitor(gameId);
    	for (Move executedMove: executedMoves){
    		executedMove.accept(actionMoveVisitor);
    	}
    }
}
