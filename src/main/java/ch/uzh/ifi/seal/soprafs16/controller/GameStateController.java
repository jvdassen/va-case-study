package ch.uzh.ifi.seal.soprafs16.controller;

import ch.uzh.ifi.seal.soprafs16.model.GameState;
import ch.uzh.ifi.seal.soprafs16.model.repositories.GameStateRepository;
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
public class GameStateController  extends GenericService {

    Logger logger = LoggerFactory.getLogger(GameStateController.class);

    @Autowired
    private GameStateRepository gsRepo;

    private final String CONTEXT = "/gamestates";

    /* List all gamestates */
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<GameState> listGameStates() {
        logger.debug("listGameStates");
        List<GameState> result = new ArrayList<GameState>();
        gsRepo.findAll().forEach(result::add);
        return result;
    }

    /* Get the gamestate of a game */
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT+"/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public GameState getGameState(@PathVariable long gameId) {
        logger.debug("getGameSTate");
        GameState result = new GameState();
        result = gsRepo.findByGameId(gameId);
        return result;
    }
}
