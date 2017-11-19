package ch.uzh.ifi.seal.soprafs16.controller;

import ch.uzh.ifi.seal.soprafs16.model.CardStack;
import ch.uzh.ifi.seal.soprafs16.model.DTOSpecialEvent;
import ch.uzh.ifi.seal.soprafs16.model.Move;
import ch.uzh.ifi.seal.soprafs16.model.SpecialEvent;
import ch.uzh.ifi.seal.soprafs16.model.repositories.*;
import ch.uzh.ifi.seal.soprafs16.service.AbilityCheck;
import ch.uzh.ifi.seal.soprafs16.service.FlowService;
import ch.uzh.ifi.seal.soprafs16.service.PostService;
import ch.uzh.ifi.seal.soprafs16.service.UpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Laurenz on 16/05/16.
 */
@RestController
public class SpecialEventController extends GenericService {

    Logger logger = LoggerFactory.getLogger(SpecialEventController.class);

    @Autowired
    private ActionCardRepository actionCardRepo;

    @Autowired
    private PostService postService;

    @Autowired
    private AbilityService abilityService;

    @Autowired
    private CharacterRepository charRepo;

    @Autowired
    private GameStateRepository gameStateRepo;

    @Autowired
    private CardStackRepository csRepo;

    @Autowired
    private UpdateService updateService;

    @Autowired
    private FlowService flowService;

    @Autowired
    private SERepository seRepo;

    private final String CONTEXT = "/specialevent";


    /* Get the last specialevent information packet */
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT + "/{gameId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public DTOSpecialEvent getLastSpecialEvent(@PathVariable long gameId, @PathVariable long userId) {
        logger.debug("getLastSpecialEvent");
        DTOSpecialEvent specialEvent = seRepo.findFirstByUserId(userId);
        seRepo.delete(specialEvent);

        return specialEvent;
    }
}
