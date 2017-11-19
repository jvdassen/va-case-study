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
 * Class to check if an ability should happen.
 */

@Service("abilityService")
public class AbilityService {


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

    /*  checks if the situation is right for ghosts ability to happen

     */
    public boolean checkForAbility(long gameId, long userId) {
        if (charRepo.findFirstByUserId(userId).getCharacter().equals(ECharacter.GHOST) && gameStateRepo.findByGameId(gameId).getRound().getCurrentTurn() == 1)
        {
            return true;
        }
        return false;
    }

    /* Checks if the situation is right for Djangos or Cheyennes ability to happen.*/
    public boolean checkForAbility(long gameId, long userId, Move move) {
        switch (charRepo.findFirstByUserId(userId).getCharacter())
        {
            case DJANGO:
            {
                Train train = trainRepo.findByGameId(gameId).get(0);
                if (move instanceof DTOShoot)
                {
                    if (train.findUserInTrain(((DTOShoot) move).getVictim().getId()) / 2 == 0
                            || train.findUserInTrain(((DTOShoot) move).getVictim().getId()) / 2 == train.getWagons().size() - 1)
                    {
                        return false;
                    } else
                    {
                        return true;
                    }
                }
                else
                {
                    return false;
                }
            }
            case CHEYENNE:
            {
                if (move instanceof DTOPunch)
                {
                    long victimId = ((DTOPunch) move).getVictim().getId();
                    for (Loot loot : lootRepo.findByUserId(victimId))
                    {
                        if (loot instanceof MoneyBag)
                        {
                            return true;
                        }
                    }
                    return false;
                }
                else
                {
                    return false;
                }
            }
            default: return false;
        }
    }
}