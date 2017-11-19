package ch.uzh.ifi.seal.soprafs16.service;


import ch.uzh.ifi.seal.soprafs16.model.*;
import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Laurenz on 24/04/16.
 */

@Service("updateService")
public class UpdateService {


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
    private GameStateRepository gameStateRepo;

    @Autowired
    private FlowService flowService;



    /* Updates the player sequence class after every turn.*/
    public void updateSequence(long gameId)
    {
        GameState gameState = gameStateRepo.findByGameId(gameId);
        PlayerSequence playerSequence = gameState.getPlayerSequence();

        playerSequence.setCurrentHasPlayed();
        gameState.setPlayerTurn(playerSequence.getCurrentPlayer());
        gameState.setPlayerSequence(playerSequence);
        gameStateRepo.save(gameState);
        flowService.checkSequence(gameId);
    }

    /*Updates the objects round, gamestate, playersequence and roundcard after a turn.*/
    public void updateTurn(long gameId)
    {
        GameState gameState = gameStateRepo.findByGameId(gameId);

        PlayerSequence playerSequence = gameState.getPlayerSequence();
        playerSequence.resetHasPlayed();
        playerSequence.resetBools();
        gameState.setPlayerSequence(playerSequence);
        gameState.setPlayerTurn(playerSequence.getCurrentPlayer());

        Round round = gameState.getRound();
        round.setCurrentTurn(round.getCurrentTurn()+1);
        gameStateRepo.save(gameState);

        CardStack cardStack = csRepo.findByGameId(gameId).get(0);
        RoundCard roundCard = cardStack.getCurrentRoundCard(round);
        roundCard.setCurrentTurn(roundCard.getCurrentTurn()+1);
        rcRepo.save(roundCard);
        if(cardStack.getCurrentRoundCard(round).getTurns().get(round.getCurrentTurn()).equals(ETurn.INVERSED)){
             playerSequence.setInversed(true);
             gameState.setPlayerTurn(playerSequence.getCurrentPlayer());
         }
        else if (cardStack.getCurrentRoundCard(round).getTurns().get(round.getCurrentTurn()).equals(ETurn.DOUBLE_TURN))
        {
            playerSequence.setDoubleRound(true);
        }
        gameState.setPlayerSequence(playerSequence);
        gameStateRepo.save(gameState);
    }

    /* If a phase is finished updates the phase*/
    public void updatePhase(long gameId)
    {
        GameState gameState = gameStateRepo.findByGameId(gameId);

        PlayerSequence playerSequence = gameState.getPlayerSequence();
        playerSequence.resetHasPlayed();
        playerSequence.resetBools();
        Phase phase = gameState.getPhase();
        phase.updatePhase();
        gameState.setPlayerSequence(playerSequence);
        gameState.setPhase(phase);
        if (phase.getPhase().equals(EPhase.ACTION))
        {
            gameState.setPlayerTurn(userRepo.findOne(csRepo.findByGameId(gameId).get(0).getFirstActionCard().getUserId()));
        }
        else {
            gameState.setPlayerTurn(playerSequence.getCurrentPlayer());
        }
        gameStateRepo.save(gameState);

        if (phase.getPhase().equals(EPhase.SPECIAL_EVENT))
        {
            flowService.checkSpecialEvent(gameId);
        }
    }

    /*Updates the turns in the action phase.*/
    public void updateActionPhase(long gameId)
    {
        GameState gameState = gameStateRepo.findByGameId(gameId);
        CardStack cs = csRepo.findByGameId(gameId).get(0);
        long oldId = cs.getFirstActionCard().getUserId();
        cs.removePlayedACCard();
        gameState.setSecondMove(false);
        if (oldId == cs.getFirstActionCard().getUserId())
        {
            gameState.setSecondMove(true);
        }
        csRepo.save(cs);
        gameState.setPlayerTurn(userRepo.findOne(csRepo.findByGameId(gameId).get(0).getFirstActionCard().getUserId()));
        gameStateRepo.save(gameState);

    }

    /*Updates the round and roundcard when a round is finished, also resets playerSequence.*/
    public void updateRound(long gameId)
    {
        GameState gameState = gameStateRepo.findByGameId(gameId);

        PlayerSequence playerSequence = gameState.getPlayerSequence();
        playerSequence.resetHasPlayed();
        playerSequence.resetBools();
        Phase phase = gameState.getPhase();
        phase.updatePhase();
        gameState.setPlayerSequence(playerSequence);
        gameState.setPhase(phase);
        gameState.setPlayerTurn(playerSequence.getCurrentPlayer());
        Round round = gameState.getRound();
        round.setCurrentTurn(1);
        round.updateRound();
        gameState.setRound(round);
        gameStateRepo.save(gameState);

        CardStack cs = csRepo.findByGameId(gameId).get(0);
        cs.clearMoveStack();
        csRepo.save(cs);
    }
}