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

@Service("flowService")
public class FlowService {


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
    private UpdateService updateService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private SpecialEventService specialEventService;

    @Autowired
    private ShortGameRepository sgRepo;


    /* Checks if the object playersequence should get updated. */
    public void checkSequence(long gameId){
        GameState gameState = gameStateRepo.findByGameId(gameId);
        PlayerSequence playerSequence = gameState.getPlayerSequence();

        if(!playerSequence.checkIfAllPlayed())
          {
              //do nothing
          }
        else
        {
            //Check that it wasn't the last turn
            if(csRepo.findByGameId(gameId).get(0).getCurrentRoundCard(gameState.getRound()).getCurrentTurn()
                    < csRepo.findByGameId(gameId).get(0).getCurrentRoundCard(gameState.getRound()).getMaxTurns()) {
                updateService.updateTurn(gameId);
            }
            else{
                updateService.updatePhase(gameId);
            }
                    //update round
        }
    }

    /* Checks if the Action turn is finished and if the whole action phase is finished
    * */
    public void checkActionFinished(long gameId)
    {
        if(csRepo.findByGameId(gameId).get(0).getMoveStack().size()==0)
        {
            if(csRepo.findByGameId(gameId).get(0).getAcStack().size()==1)
            {
                //beauty bug
                CardStack cs = csRepo.findByGameId(gameId).get(0);
                cs.removePlayedACCard();
                csRepo.save(cs);
                updateService.updatePhase(gameId);
            }
            else
            {
                updateService.updateActionPhase(gameId);
            }
        }
        else
        {
            //do nothing
        }
    }

    /*Checks if the roundcard has a special event.*/
    public void checkSpecialEvent(long gameId)
    {
        if (csRepo.findByGameId(gameId).get(0).getCurrentRoundCard(gameStateRepo.findByGameId(gameId).getRound()).getSpecialEvent().getSpecialEvent().equals(ESpecialEvent.NOTHING))
        {
            checkEndOfGame(gameId);
        }
        else {
            specialEventService.doSpecialEvent(gameId);
            checkEndOfGame(gameId);
        }
    }

    /*Checks if all rounds are played and if so, updates the gamestate to inform the client.*/
    public void checkEndOfGame(long gameId)
    {
        if (gameStateRepo.findByGameId(gameId).getRound().getCurrentRound().equals(ERound.FIVE)
                || (sgRepo.findFirstByGameId(gameId).isShortV() && gameStateRepo.findByGameId(gameId).getRound().getCurrentRound().equals(ERound.ONE)))
        {
            GameState gameState = gameStateRepo.findByGameId(gameId);
            Round round = gameState.getRound();
            round.setCurrentRound(ERound.END);
            gameState.setRound(round);
            gameStateRepo.save(gameState);
        }
        else
        {
            updateService.updateRound(gameId);
        }
    }
}