package ch.uzh.ifi.seal.soprafs16.controller;

/**
 * Created by Laurenz on 24/04/16.
 */
import ch.uzh.ifi.seal.soprafs16.model.ActionCard;
import ch.uzh.ifi.seal.soprafs16.model.SpecialAbility;
import ch.uzh.ifi.seal.soprafs16.model.repositories.*;

import ch.uzh.ifi.seal.soprafs16.service.AbilityCheck;
import ch.uzh.ifi.seal.soprafs16.service.PostService;
import ch.uzh.ifi.seal.soprafs16.service.UpdateService;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ActionCardController extends GenericService {

    Logger logger = LoggerFactory.getLogger(ActionCardController.class);

    @Autowired
    private ActionCardRepository actionCardRepo;

    @Autowired
    private PostService postService;

    @Autowired
    private AbilityCheck abilityService;

    @Autowired
    private CharacterRepository charRepo;

    @Autowired
    private CardStackRepository csRepo;

    @Autowired
    private UpdateService updateService;

    private final String CONTEXT = "/actioncards";


    /* List all actioncards in the game */
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<ActionCard> listActionCards() {
        logger.debug("listActionCards");

        List<ActionCard> result = new ArrayList<ActionCard>();
        actionCardRepo.findAll().forEach(result::add);
        return result;
    }

    /* Inform the server that the player didn't play an action card but draw new cards */
    @RequestMapping(method = RequestMethod.POST, value = CONTEXT + "/{gameId}/draw")
    @ResponseStatus(HttpStatus.OK)
    public void drawActionCard(@PathVariable long gameId) {
        logger.debug("drawActionCard");

        updateService.updateSequence(gameId);
    }

    /* Add actioncard to the the action card stack */
    @RequestMapping(value = CONTEXT + "/{gameId}/{userId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addActionCard(@RequestBody ActionCard actionCard, @PathVariable long gameId, @PathVariable long userId) {
        logger.debug("addActionCard");

        List<ActionCard> actionCards = actionCardRepo.findByCardNumAndUserId(actionCard.getCardNum(),userId);
        for (ActionCard ac : actionCards)
        {
            if (!csRepo.findByGameId(gameId).get(0).checkACIfInStack(ac)) {
                actionCard = ac;
            }
        }

        if (abilityService.hasSpecialAbility(gameId, userId))
        {
            SpecialAbility sa = charRepo.findFirstByUserId(userId).getSpecialAbility();
            sa.doAbility(actionCard);
            actionCardRepo.save(actionCard);
        }
        postService.addActionCardToStack(gameId, actionCard);

        updateService.updateSequence(gameId);
    }

    /* PLANNING Phase: Get the action card that has been added last*/
    @RequestMapping(value = CONTEXT + "/{gameId}/played", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ActionCard getPlayedCard(@PathVariable long gameId)
    {
        return csRepo.findByGameId(gameId).get(0).getLastActionCardFromStack();
    }

    /* ACTION Phase: Get the action card that is being played right now */
    @RequestMapping(value = CONTEXT + "/{gameId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ActionCard getCurrentCard(@PathVariable long gameId)
    {
        return csRepo.findByGameId(gameId).get(0).getFirstActionCard();
    }
}