package ch.uzh.ifi.seal.soprafs16.controller;

/**
 * Created by Laurenz on 16/04/16.
 */
import ch.uzh.ifi.seal.soprafs16.model.CardStack;
import ch.uzh.ifi.seal.soprafs16.model.RoundCard;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CardStackRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.GameStateRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.RoundCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoundCardController  extends GenericService {

    Logger logger = LoggerFactory.getLogger(RoundCardController.class);

    @Autowired
    private RoundCardRepository rcRepo;

    @Autowired
    private CardStackRepository cardStackRepo;

    @Autowired
    private GameStateRepository gameStateRepo;

    private final String CONTEXT = "/roundcards";

    /* List all roundcards in the server */
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<RoundCard> listRoundCards() {
        logger.debug("listRoundCards");
        List<RoundCard> result = new ArrayList<RoundCard>();
        rcRepo.findAll().forEach(result::add);
        return result;
    }

    /* Get the roundcard of the current round */
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT + "/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public RoundCard getCurrentRoundCards(@PathVariable Long gameId) {
        logger.debug("getCurrentRoundCards");
        return cardStackRepo.findByGameId(gameId).get(0).getCurrentRoundCard(gameStateRepo.findByGameId(gameId).getRound());
    }

}
