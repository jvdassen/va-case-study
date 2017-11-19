package ch.uzh.ifi.seal.soprafs16.controller;

import ch.uzh.ifi.seal.soprafs16.model.AmmoCard;
import ch.uzh.ifi.seal.soprafs16.model.repositories.AmmoCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CardStackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laurenz on 07/04/16.
 */

@RestController
public class AmmoCardController  extends GenericService {

    Logger logger  = LoggerFactory.getLogger(AmmoCardController.class);

    @Autowired
    private AmmoCardRepository amcaRepo;

    @Autowired
    private CardStackRepository csRepo;

    private final String   CONTEXT = "/ammocards";

    /* List all ammocards */
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<AmmoCard> listAmmoCards() {
        logger.debug("listAmmoCards");
        List<AmmoCard> result = new ArrayList<AmmoCard>();
        amcaRepo.findAll().forEach(result::add);
        return result;
    }

    /* Add an ammo card */
    @RequestMapping(value = CONTEXT, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public AmmoCard addAmmoCard() {
        logger.debug("addAmmoCard: ");

        AmmoCard ac = new AmmoCard();
        ac.setGameId((long)1);
        ac.setNeutral(true);
        ac.setOwner((long)123);
        ac.setBulletRound(2);
        ac.setVictim((long)12345);

        ac = amcaRepo.save(ac);

        return ac;
    }

    /* Get all ammocards of a game */
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT + "/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AmmoCard> getAmmoCards(@PathVariable long gameId) {
        logger.debug("getAmmoCards");
        //List<AmmoCard> result = new ArrayList<AmmoCard>();
        List<AmmoCard> amc = amcaRepo.findByGameId(gameId);
       /* for (int i=0;i<amc.size();i++)
        {
            //if (amc.get(i).getUserId()!=0)
            {
                result.add(amc.get(i));
            }
        }*/
        return amc;
    }
}
