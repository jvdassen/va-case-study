package ch.uzh.ifi.seal.soprafs16.controller;


import ch.uzh.ifi.seal.soprafs16.model.CardStack;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CardStackRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CharacterRepository;
import org.springframework.web.bind.annotation.RestController;
import ch.uzh.ifi.seal.soprafs16.model.Character;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CharacterController extends GenericService{

    Logger logger  = LoggerFactory.getLogger(CharacterController.class);

    @Autowired
    private CharacterRepository charRepo;

    @Autowired
    private CardStackRepository csRepo;

    private final String CONTEXT = "/characters";


    /*//Start Game dummy
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT + "/start")
    @ResponseStatus(HttpStatus.OK)
    public boolean startGameD() {
        logger.debug("startGameD");

        if (first){
        long dummyGameId = 1;
        creaSer.createCharacters(dummyGameId);
        creaSer.createLoot(dummyGameId);
        creaSer.createTrains(dummyGameId);
        creaSer.createRoundCards(dummyGameId);
        creaSer.createGameState(dummyGameId);
        creaSer.createActionCards(dummyGameId);
        creaSer.createAmmoCards(dummyGameId);
        creaSer.createCardStack(dummyGameId);
        assSer.assignRoundCards(dummyGameId);
        assSer.assignLoot(dummyGameId);
            first = false;
            return true;
        }

        else {
            return false;
        }
    }
*/
    /* List all characters of the game */
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<Character> listCharacters() {
        logger.debug("listCharacters");
        List<Character> result = new ArrayList<Character>();
        charRepo.findAll().forEach(result::add);
        return result;
    }

    /* List all free characters of a game */
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT + "/free/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Character> listFreeCharacters(@PathVariable Long gameId) {
        logger.debug("listFreeCharacters");
        List<Character> charList = new ArrayList<Character>();
        List<Character> result = new ArrayList<Character>();
        charRepo.findAll().forEach(charList::add);
        for (int i=0;i<charList.size();i++)
        {
            if (charList.get(i).getGameId() == gameId)
            {
                if (charList.get(i).getUserId() == 0) {
                    result.add(charList.get(i));
                }
            }
        }
        return result;
    }

    /* Assign a player to a character */
    @RequestMapping(value = CONTEXT + "/{gameId}/{userId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void assignCharacter(@PathVariable Long gameId, @PathVariable Long userId, @RequestBody Character character) {
        logger.debug("assignCharacter: " + userId);

        Character assChar = charRepo.findByCharacterAndGameId(character.getCharacter(), gameId);

        if (assChar != null && assChar.getUserId() == 0) {
            assChar.setUserId(userId);
            charRepo.save(assChar);
        }
    }

    /* User for dev */
    @RequestMapping(value = CONTEXT + "/stacks", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CardStack> listStack() {
        logger.debug("listStack");

        List<CardStack> cardStack = new ArrayList<CardStack>();
        csRepo.findAll().forEach(cardStack::add);

        return cardStack;
    }
}
