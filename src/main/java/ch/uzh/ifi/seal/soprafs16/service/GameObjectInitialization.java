package ch.uzh.ifi.seal.soprafs16.service;

import ch.uzh.ifi.seal.soprafs16.model.*;
import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ThreadLocalRandom;

@Service("GameObjectInitialization")
public class GameObjectInitialization {


    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private LootRepository lootRepo;

    @Autowired
    private TrainRepository trainRepo;

    @Autowired
    private RoundCardRepository rcRepo;

    @Autowired
    private ActionCardRepository actionCardRepo;

    @Autowired
    private AmmoCardRepository ammoCardRepo;

    @Autowired
    private CardStackRepository csRepo;

    @Autowired
    private CharacterRepository charRepo;

    @Autowired
    private  ShortGameRepository sgRepo;

    /* Move the roundcards to the stack so it is easy findable. */
    public void assignRoundCards(long gameId)
    {
        List<RoundCard> result = new ArrayList<RoundCard>();
        result = rcRepo.findByGameId(gameId);

        List<CardStack> cs = new ArrayList<CardStack>();
        cs = csRepo.findByGameId(gameId);

        if (sgRepo.findFirstByGameId(gameId).isShortV())
        {
            cs.get(0).addRoundCardToStack(result.get(0));
        }
        else
        {

            for (int i = 0; i < result.size(); i++)
            {
                if (result.get(i).isTrainStation())
                {
                    int random = ThreadLocalRandom.current().nextInt(0,3);
                    cs.get(0).addRoundCardToStack(result.get(random));
                    break;
                }
            }
            int counter = 0;
            for (int i = 0; i < result.size(); i++)
            {
                if (!result.get(i).isTrainStation())
                {
                    int random;
                    do
                    {
                        random = ThreadLocalRandom.current().nextInt(3, 8);
                    }
                    while (cs.get(0).getRcStack().contains(result.get(random)));

                    cs.get(0).addRoundCardToStack(result.get(random));
                    counter++;
                    if (counter == 4)
                    {
                        break;
                    }
                }
            }
        }
        csRepo.save(cs);
    }

    /* Hand the loot to the players and wagons.*/
    public void assignLoot(long gameId) {
        Game game = gameRepo.findOne(gameId);
        List<Train> trains = new ArrayList<Train>();
        trains = trainRepo.findByGameId(gameId);

        Train train = new Train();
        train = trains.get(0);

        List<Loot> loots = new ArrayList<Loot>();
        loots = lootRepo.findByGameId(gameId);

        for (int i = 0; i < game.getPlayers().size(); i++) {
            for (int j = 0; j < loots.size(); j++) {
                if (loots.get(j) instanceof MoneyBag && loots.get(j).getValue() == 250
                        && loots.get(j).getTrainId() == 0 && loots.get(j).getUserId() == 0) {
                    loots.get(j).setUserId(game.getPlayers().get(i).getId());
                    break;
                }
            }
        }
        lootRepo.save(loots);

        List<Loot> loot = new ArrayList<Loot>();
        for (int i = 0; i < loots.size(); i++) {
            if (loots.get(i) instanceof Lockbox) {
                loot.add(loots.get(i));
                train.getWagons().get(0).getTrainLevels().get(0).setLoot(loot);
                loots.get(i).setTrainId(train.getId());
                break;

            }
        }
        trainRepo.save(train);
        lootRepo.save(loot);
        lootRepo.save(loots);

        for (int i = 0; i < loots.size(); i++) {
            if (loots.get(i) instanceof Lockbox && loots.get(i).getTrainId() == 0) {
                List<CardStack> csl = new ArrayList<CardStack>();
                csl = csRepo.findByGameId(gameId);
                csl.get(0).setLockbox(loots.get(i));
                csRepo.save(csl.get(0));
                break;
            }
        }

        for (int i = 1; i < train.getWagons().size(); i++) {
            switch (i) {
                case 1: {
                    loot = new ArrayList<Loot>();
                    int counter = 0;
                    for (int j = ThreadLocalRandom.current().nextInt(15, 24); j < loots.size(); j++) {
                        if (loots.get(j) instanceof MoneyBag && loots.get(j).getUserId() == 0) {
                            loot.add(loots.get(j));
                            loots.get(j).setTrainId(train.getId());
                            counter++;
                            if (counter > 2) {
                                break;
                            }
                        }
                    }
                    train.getWagons().get(1).getTrainLevels().get(0).setLoot(loot);
                    break;
                }
                case 2: {
                    loot = new ArrayList<Loot>();
                    int counter = 0;
                    for (int j = 0; j < loots.size(); j++) {
                        if (loots.get(j) instanceof Diamond) {
                            loot.add(loots.get(j));
                            loots.get(j).setTrainId(train.getId());
                            counter++;
                            if (counter > 2) {
                                break;
                            }
                        }
                    }
                    train.getWagons().get(2).getTrainLevels().get(0).setLoot(loot);
                    break;
                }
                case 3: {
                    loot = new ArrayList<Loot>();
                    int counter = 0;
                    for (int j = ThreadLocalRandom.current().nextInt(8, 20); j < loots.size(); j++) {
                        if (loots.get(j) instanceof MoneyBag
                                && loots.get(j).getTrainId() == 0 && loots.get(j).getUserId() == 0) {
                            loot.add(loots.get(j));
                            loots.get(j).setTrainId(train.getId());
                            counter++;
                            if (counter > 3) {
                                break;
                            }
                        }
                    }
                    for (int j = 0; j < loots.size(); j++) {
                        if (loots.get(j) instanceof Diamond && loots.get(j).getTrainId() == 0) {
                            loot.add(loots.get(j));
                            loots.get(j).setTrainId(train.getId());
                            break;
                        }
                    }
                    train.getWagons().get(3).getTrainLevels().get(0).setLoot(loot);
                    break;
                }
                case 4: {
                    loot = new ArrayList<Loot>();
                    int counter = 0;
                    for (int j = ThreadLocalRandom.current().nextInt(8, 16); j < loots.size(); j++) {
                        if (loots.get(j) instanceof MoneyBag
                                && loots.get(j).getTrainId() == 0 && loots.get(j).getUserId() == 0) {
                            loot.add(loots.get(j));
                            loots.get(j).setTrainId(train.getId());
                            counter++;
                            if (counter > 2) {
                                break;
                            }
                        }
                    }
                    for (int j = 0; j < loots.size(); j++) {
                        if (loots.get(j) instanceof Diamond && loots.get(j).getTrainId() == 0) {
                            loot.add(loots.get(j));
                            loots.get(j).setTrainId(train.getId());
                            break;
                        }
                    }
                    train.getWagons().get(4).getTrainLevels().get(0).setLoot(loot);
                    break;
                }

            }
            trainRepo.save(train);
            lootRepo.save(loot);
            lootRepo.save(loots);
        }


    }

    /* Assign the action cards to the players. */
    public void assignActionCards(long gameId)
    {
        List<ActionCard> actionCards = actionCardRepo.findByGameId(gameId);

        int counter = 0;
        for (int i=0;i<gameRepo.findOne(gameId).getPlayers().size();i++)
        {
            for (int j=0;j<actionCards.size();j++)
            {
                if (counter < 10 && actionCards.get(j).getUserId() == 0)
                {
                    actionCards.get(j).setUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId());
                    counter++;
                }
                else
                {
                    if (counter > 9) {
                        counter = 0;
                        break;
                    }
                }
            }
        }
        actionCardRepo.save(actionCards);
    }

    /* Assign the ammocards to the players*/
    public void assignAmmoCards(long gameId)
    {
        List<AmmoCard> amc = ammoCardRepo.findByGameId(gameId);

        int counter = 0;
        for (int i = 0; i < gameRepo.findOne(gameId).getPlayers().size(); i++) {
            for (int j = 0; j < amc.size(); j++) {
                if (counter < 6 && amc.get(j).getUserId() == 0 && amc.get(j).isNeutral() == false && amc.get(j).getBulletRound() == counter + 1) {
                    amc.get(j).setUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId());
                    amc.get(j).setVictim((long)0);
                    counter++;
                } else {
                    if (counter > 5) {
                        counter = 0;
                        break;
                    }
                }
            }
        }

        ammoCardRepo.save(amc);

        amc = ammoCardRepo.findByGameId(gameId);
        List<CardStack> cs = csRepo.findByGameId(gameId);

        for (int i=0; i<amc.size(); i++) {
            if (amc.get(i).isNeutral() == true) {
                cs.get(0).addAmmoCard(amc.get(i));
            }
        }
        ammoCardRepo.save(amc);
        csRepo.save(cs.get(0));
    }

    /* Assign characters to the train.
        Doesn't assign the characters to a user, this gets done directly via POST request.
     */
    public void assignCharacters(long gameId)
    {
        List<Train> train = trainRepo.findByGameId(gameId);
        List<User> players = gameRepo.findById(gameId).getPlayers();
        for (int i=0; i<gameRepo.findOne(gameId).getPlayers().size();i++)
        {
            Character characters = charRepo.findFirstByUserId(players.get(i).getId());
            if ((i+1)%2==0) {
                train.get(0).getWagons().get(train.get(0).getWagons().size() - 2).getTrainLevels().get(0).addCharacter(characters);
            }
            else
            {
                train.get(0).getWagons().get(train.get(0).getWagons().size() - 1).getTrainLevels().get(0).addCharacter(characters);
            }
        }

        trainRepo.save(train);
    }
}
