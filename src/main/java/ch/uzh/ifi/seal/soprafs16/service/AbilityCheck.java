package ch.uzh.ifi.seal.soprafs16.service;

import ch.uzh.ifi.seal.soprafs16.model.*;
import ch.uzh.ifi.seal.soprafs16.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("abilityService")
public class AbilityCheck {


    @Autowired
    private LootRepository lootRepo;

    @Autowired
    private TrainRepository trainRepo;

    @Autowired
    private CharacterRepository charRepo;

    @Autowired
    private GameStateRepository gameStateRepo;

    public boolean hasSpecialAbility(long gameId, long userId) {
        if (charRepo.findFirstByUserId(userId).getCharacter().equals(ECharacter.GHOST) && gameStateRepo.findByGameId(gameId).getRound().getCurrentTurn() == 1)
        {
            return true;
        }
        return false;
    }

    public boolean hasSpecialAbility(long gameId, long userId, Move move) {
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