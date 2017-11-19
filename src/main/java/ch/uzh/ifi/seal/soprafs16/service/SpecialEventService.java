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
 * Created by Laurenz on 16/05/16.
 *
 * Isnt nicely implemented but because of the many different accessess, its necessary that these arent implemented in the class.
 */


@Service("specialEventService")
public class SpecialEventService
{


    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private LootRepository lootRepo;

    @Autowired
    private TrainRepository trainRepo;

    @Autowired
    private AmmoCardRepository ammoCardRepo;

    @Autowired
    private CardStackRepository csRepo;

    @Autowired
    private CharacterRepository charRepo;

    @Autowired
    private GameStateRepository gameStateRepo;

    @Autowired
    private SERepository seRepository;

    /* Start a special Event, according to the roundcard */
    public void doSpecialEvent(long gameId){
        GameState gameState = gameStateRepo.findByGameId(gameId);
        Train train = trainRepo.findByGameId(gameId).get(0);
        CardStack cs = csRepo.findByGameId(gameId).get(0);
        Game game = gameRepo.findById(gameId);
        switch (cs.getCurrentRoundCard(gameState.getRound()).getSpecialEvent().getSpecialEvent())
        {
            case ANGRY_MARSHAL:{
                List<AmmoCard> amc = new ArrayList<AmmoCard>();
                for (Character character : train.getWagons().get(train.findMarshal()/2).getTrainLevels().get(1).getCharacters())
                {
                    if (cs.getAmmoCard() != null)
                    {
                        AmmoCard ammoCard = cs.getAmmoCard();
                        amc.add(ammoCard);
                        cs.removeLastAmmoCard();
                        //ammoCard.setUserId(character.getUserId());
                        ammoCard.setVictim(character.getUserId());
                        csRepo.save(cs);
                        ammoCardRepo.save(ammoCard);
                    }
                }
                if (train.findMarshal() != train.getWagons().size()*2-2)
                {
                    train.moveMarshal(false);
                }
                trainRepo.save(train);

                List<Character> characters = new ArrayList<Character>();
                for (Character character : train.getWagons().get(train.findMarshal()/2).getTrainLevels().get(0).getCharacters())
                {
                    characters.add(character);
                }
                for (Character character : characters)
                {
                    int pos = train.findUserInTrain(character.getUserId());
                    train.removeCharacterInTrain(character.getUserId());
                    trainRepo.save(train);
                    train.climbCharacter(charRepo.findFirstByUserId(character.getUserId()), pos);
                    trainRepo.save(train);
                    if (cs.getAmmoCard() != null)
                    {
                        AmmoCard ammoCard = cs.getAmmoCard();
                        amc.add(ammoCard);
                        cs.removeLastAmmoCard();
                        //ammoCard.setUserId(character.getUserId());
                        ammoCard.setVictim(character.getUserId());
                        csRepo.save(cs);
                        ammoCardRepo.save(ammoCard);
                    }
                }
                for (User us:game.getPlayers())
                {
                        DTOSpecialEvent newSE = new DTOSpecialEvent();
                        newSE.setUserId(us.getId());
                        for (AmmoCard ac : amc)
                        {
                             if (us.getId()==ac.getVictim())
                             {
                                 newSE.setShotAmmoCard(ac);
                             }
                        }
                    seRepository.save(newSE);
                }
            }break;
            case CRANE:
            {
                List<Character> characters = train.getCharactersOnTop();
                for (Character character : characters)
                {
                    train.removeCharacterInTrain(character.getUserId());
                }
                trainRepo.save(train);
                for (Character character : characters)
                {
                    int lastWagon = train.getWagons().size() * 2 - 1;
                    train.addCharacter(character, lastWagon);
                }
                trainRepo.save(train);
            }break;
            case BRAKE:
            {
                List<Character> characters = train.getCharactersOnTop();
                List<Integer> positions = new ArrayList<Integer>();
                for (Character character : characters)
                {
                    positions.add(train.findUserInTrain(character.getUserId()));
                }
                for (Character character : characters)
                {
                    train.removeCharacterInTrain(character.getUserId());
                }
                trainRepo.save(train);
                for (Character character : characters)
                {
                    train.moveUser(character, positions.get(characters.indexOf(character)), true);
                }
                trainRepo.save(train);
            }break;
            case TAKEITALL:
            {
                train.getWagons().get(train.findMarshal()/2).getTrainLevels().get(0).addLoot(cs.getLockbox());
                cs.setLockbox(null);
                trainRepo.save(train);
                csRepo.save(cs);
            }break;
            case PASSENGERS_RESSISTANCE:
            {
                List<Character> characters = train.getCharactersOnBase();
                List<AmmoCard> amc = new ArrayList<AmmoCard>();
                for(Character character:characters)
                {
                    if (cs.getAmmoCard() != null)
                    {
                        amc.add(cs.getAmmoCard());
                        cs.removeLastAmmoCard();
                        amc.get(amc.size()-1).setVictim(character.getUserId());
                        csRepo.save(cs);
                        ammoCardRepo.save(amc.get(amc.size()-1));
                    }
                }
                for (User us:game.getPlayers())
                {
                    DTOSpecialEvent newSE = new DTOSpecialEvent();
                    newSE.setUserId(us.getId());
                    for (AmmoCard ac : amc)
                    {
                        if (ac.getVictim() == us.getId())
                        {
                            newSE.setShotAmmoCard(ac);
                        }
                    }
                    seRepository.save(newSE);
                }
            }break;
            case POCKET_PICKING:
            {
                List<MoneyBag> mbs = new ArrayList<MoneyBag>();
                for (User user : game.getPlayers())
                {
                    int pos = train.findUserInTrain(user.getId());
                    if (train.getWagons().get(pos/2).getTrainLevels().get(pos%2).getCharacters().size()<2)
                    {
                        if (train.getWagons().get(pos / 2).getTrainLevels().get(pos % 2).getLoot().size() > 0)
                        {
                            List<MoneyBag> mb = new ArrayList<MoneyBag>();
                            for (Loot loot : train.getWagons().get(pos / 2).getTrainLevels().get(pos % 2).getLoot())
                            {
                                if (loot instanceof MoneyBag)
                                {
                                    mb.add(((MoneyBag) loot));
                                }
                            }
                            if (mb.size()>0)
                            {
                                int ran = ThreadLocalRandom.current().nextInt(0, mb.size());
                                mbs.add(mb.get(ran));
                                train.getWagons().get(pos / 2).getTrainLevels().get(pos % 2).removeLoot(mb.get(ran));
                                trainRepo.save(train);
                                mb.get(ran).setUserId(user.getId());
                                lootRepo.save(mb.get(ran));
                            }
                        }
                    }
                }
                for (User us:game.getPlayers())
                {
                    DTOSpecialEvent newSE = new DTOSpecialEvent();
                    newSE.setUserId(us.getId());
                    for (MoneyBag mb : mbs)
                    {
                        if (us.getId()==mb.getUserId())
                        {
                            newSE.setTakenLoot(mb);
                        }
                    }
                    seRepository.save(newSE);
                }
            }break;
            case REVENGE_MARSHAL:
            {
                List<Character> characters = new ArrayList<Character>();
                for (Character character : train.getWagons().get(train.findMarshal()/2).getTrainLevels().get(1).getCharacters())
                {
                    List<Loot> loots = lootRepo.findByUserId(character.getUserId());
                    int counter = 0;
                    for (int i=0; i<lootRepo.findByUserId(character.getUserId()).size();i++)
                    {
                        for (int j=0; j<lootRepo.findByUserId(character.getUserId()).size(); j++)
                        {
                            if (loots.get(i).getValue()>loots.get(j).getValue())
                            {
                                break;
                            }
                            if (j == lootRepo.findByUserId(character.getUserId()).size()-1)
                            {
                                counter = i;
                            }
                        }
                        if (counter != 0)
                        {
                            break;
                        }
                    }
                    if (loots.size()>0)
                    {
                        Loot loot = loots.get(counter);
                        train.getWagons().get(train.findMarshal() / 2).getTrainLevels().get(1).addLoot(loot);
                        trainRepo.save(train);
                        loot.setUserId(0);
                        lootRepo.save(loot);
                    }
                }
            }break;
            case HOSTAGE:
            {
                List<Character> characters = new ArrayList<Character>();
                for (Character character : train.getWagons().get(0).getTrainLevels().get(0).getCharacters())
                {
                    characters.add(character);
                }
                for (Character character : train.getWagons().get(0).getTrainLevels().get(1).getCharacters())
                {
                    characters.add(character);
                }
                for (Character character : characters)
                {
                    cs.addRansom(character.getUserId());
                }
                csRepo.save(cs);
            }break;
        }
    }
}
