package ch.uzh.ifi.seal.soprafs16.controller;

import ch.uzh.ifi.seal.soprafs16.model.repositories.CharacterRepository;

/**
 * Created by Laurenz on 14/04/16.
 */

import ch.uzh.ifi.seal.soprafs16.model.repositories.LootRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.UserRepository;
import ch.uzh.ifi.seal.soprafs16.service.CreationService;
import org.springframework.web.bind.annotation.RestController;
import ch.uzh.ifi.seal.soprafs16.model.Loot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class LootController extends GenericService{

    Logger logger  = LoggerFactory.getLogger(LootController.class);

    @Autowired
    private LootRepository lootRepo;
    
    @Autowired
    private UserRepository userRepo;

    private final String   CONTEXT = "/loot";

    /* List all loot of the server */
    @RequestMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<Loot> listLoot() {
        logger.debug("listLoot");
        List<Loot> result = new ArrayList<Loot>();
        lootRepo.findAll().forEach(result::add);
        return result;
    }

    /* Get all loot of a game */
    @RequestMapping(value = CONTEXT +"/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Loot> getLootOfGame(@PathVariable long gameId) {
        logger.debug("getLoot");
        List<Loot> loot = lootRepo.findByGameId(gameId);
        List<Loot> result = new ArrayList<Loot>();
        for (int i=0;i<loot.size();i++)
        {
            if (loot.get(i).getUserId()!=0)
            {
                result.add(loot.get(i));
            }
        }
        return result;
    }

    /* Get the loot of a certain user */
    @RequestMapping(value = CONTEXT +"/{gameId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Loot> getLootOfUser(@PathVariable long gameId, @PathVariable long userId) {
        logger.debug("getLootOfUser");
        List<Loot> loot = lootRepo.findByGameId(gameId);
        List<Loot> result = new ArrayList<Loot>();
        for (int i=0;i<loot.size();i++)
        {
            if (loot.get(i).getUserId()== userId)
            {
                result.add(loot.get(i));
            }
        }
        return result;
    }

    /* no idea what that is used for*/
    @RequestMapping(value = CONTEXT +"/{gameId}/{userId}/username")
    @ResponseStatus(HttpStatus.OK)
    public String getUsername(@PathVariable long gameId, @PathVariable long userId) {
        logger.debug("getLootOfUser");
        List<Loot> loot = lootRepo.findByGameId(gameId);
        Long id;
        String result = "";
        for (int i=0;i<loot.size();i++)
        {
            if (loot.get(i).getUserId()== userId)
            {
               	id = loot.get(i).getUserId();
               	result = userRepo.findOne(id).getUsername();
            }
        }
        return result;
    }

}
