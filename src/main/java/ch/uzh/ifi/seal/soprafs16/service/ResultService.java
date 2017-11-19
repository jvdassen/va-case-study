package ch.uzh.ifi.seal.soprafs16.service;

import ch.uzh.ifi.seal.soprafs16.model.*;
import ch.uzh.ifi.seal.soprafs16.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;


@Service("resultService")
public class ResultService
{


    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private LootRepository lootRepo;

    @Autowired
    private AmmoCardRepository ammoCardRepo;

    @Autowired
    private CardStackRepository csRepo;

    /* Determines the ones that have the most victims. There can be several revolverheros
    @return list<long>: list with the user ids that are revolver heros.
     */
    public List<Long> findRevolverHero(long gameId)
    {
        List<User> players = gameRepo.findOne(gameId).getPlayers();
        List<AmmoCard> amc = new ArrayList<AmmoCard>();
        for (User us : players)
        {
            for (AmmoCard am : ammoCardRepo.findByUserId(us.getId()))
            {
                amc.add(am);
            }
        }


        List<Long> heros = new ArrayList<Long>();
        int counter0 = 0;
        int counter1 = 0;
        int counter2 = 0;
        int counter3 = 0;

        if (players.size()==2)
        {
            counter2 = 100;
            counter3 = 100;
        }
        else if (players.size()== 3)
        {
            counter3 = 100;
        }

        for (User us : players)
        {
            for (AmmoCard am : amc)
            {
                if (am.getUserId() == us.getId() && am.getVictim() == 0)
                {
                    if (players.indexOf(us) == 0)
                    {
                        counter0++;
                    }
                    else if (players.indexOf(us) == 1)
                    {
                        counter1++;
                    }
                    else if (players.indexOf(us) == 2)
                    {
                        counter2++;
                    }
                    else if (players.indexOf(us) == 3)
                    {
                        counter3++;
                    }
                }
            }
        }

        List<Integer> sum = new ArrayList<Integer>();
        sum.add(counter0);
        sum.add(counter1);
        sum.add(counter2);
        sum.add(counter3);

        for (int j=0;j<sum.size();j++)
        {
            for (int i=0;i< sum.size();i++)
            {
                if (sum.get(j) > sum.get(i))
                {
                    break;
                }
                if (i==sum.size()-1)
                {
                    heros.add(players.get(j).getId());
                }
            }
        }

        return heros;
    }

    /* Adds together all values the player has made and assigns it to a hash table
    @return map<User, integer>: The player is the key, and the integer is the whole money he has at the end of the game
                                with the case that he was a revolver hero.
     */
    public Map<Long, Integer> summarizeResult(long gameId)
    {
        List<Long> heros = findRevolverHero(gameId);
        Map<Long, Integer> end = new HashMap<Long, Integer>();
        List<User> players = gameRepo.findById(gameId).getPlayers();
        CardStack cs = csRepo.findByGameId(gameId).get(0);


        for (User us : players)
        {
            int value = 0;
            if (heros.contains(us.getId()))
            {
                value += 1000;
            }
            for (Loot loot : lootRepo.findByUserId(us.getId()))
            {
                value += loot.getValue();
            }
            if (cs.getRansom().contains(us.getId()))
            {
                value +=250;
            }
            end.put(us.getId(), value);
        }


        return end;
    }

}
