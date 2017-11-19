package ch.uzh.ifi.seal.soprafs16.controller;

/**
 * Created by Laurenz on 28/04/16.
 */
import ch.uzh.ifi.seal.soprafs16.model.*;
import ch.uzh.ifi.seal.soprafs16.model.repositories.*;
import ch.uzh.ifi.seal.soprafs16.service.FlowService;
import ch.uzh.ifi.seal.soprafs16.service.PostService;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class MoveController extends GenericService {

    Logger logger = LoggerFactory.getLogger(MoveController.class);


    @Autowired
    private PostService postService;

    @Autowired
    private CardStackRepository csRepo;

    @Autowired
    private FlowService flowService;



    private final String CONTEXT = "/moves";


    /* Get the last move played */
    @RequestMapping(method = RequestMethod.GET, value = CONTEXT + "/{gameId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Move getLastMove(@PathVariable long gameId, @PathVariable long userId) {
        logger.debug("getLastMove");
        CardStack cs = csRepo.findByGameId(gameId).get(0);
        for (Move move :cs.getMoveStack())
        {
            if (move.getUserId()==userId)
            {
                cs.removeMove(move);
                flowService.checkActionFinished(gameId);
                csRepo.save(cs);
                return move;
            }
        }

        return null;
    }

    /* Add move climb to stack */
    @RequestMapping(method = RequestMethod.POST, value = CONTEXT + "/{gameId}/{userId}/climb")
    @ResponseStatus(HttpStatus.OK)
    public void addClimb(@PathVariable long gameId, @PathVariable long userId, @RequestBody DTOClimb move) {
        logger.debug("addMove");
        Move newMove = move;
        postService.addMoveToStack(gameId, newMove);
    }

    /* Add move move to stack*/
    @RequestMapping(method = RequestMethod.POST, value = CONTEXT + "/{gameId}/{userId}/move")
    @ResponseStatus(HttpStatus.OK)
    public void addMove(@PathVariable long gameId, @PathVariable long userId, @RequestBody DTOMove move) {
        logger.debug("addMove");
        Move newMove = move;
        postService.addMoveToStack(gameId, newMove);
    }

    /* Add move shoot to stack */
    @RequestMapping(method = RequestMethod.POST, value = CONTEXT + "/{gameId}/{userId}/shoot")
    @ResponseStatus(HttpStatus.OK)
    public void addShoot(@PathVariable long gameId, @PathVariable long userId, @RequestBody DTOShoot move) {
        logger.debug("addMove");
        Move newMove = move;
        postService.addMoveToStack(gameId, newMove);
    }

    /* Add move punch to stack */
    @RequestMapping(method = RequestMethod.POST, value = CONTEXT + "/{gameId}/{userId}/punch")
    @ResponseStatus(HttpStatus.OK)
    public void addPunch(@PathVariable long gameId, @PathVariable long userId, @RequestBody DTOPunch move) {
        logger.debug("addMove");
        Move newMove = move;
        postService.addMoveToStack(gameId, newMove);

    }

    /* Add move marshal to stack */
    @RequestMapping(method = RequestMethod.POST, value = CONTEXT + "/{gameId}/{userId}/marshal")
    @ResponseStatus(HttpStatus.OK)
    public void addMarshal(@PathVariable long gameId, @PathVariable long userId, @RequestBody DTOMarshal move) {
        logger.debug("addMove");
        Move newMove = move;
        postService.addMoveToStack(gameId, newMove);
    }

    /* Add move invalid to stack */
    @RequestMapping(method = RequestMethod.POST, value = CONTEXT + "/{gameId}/{userId}/invalid")
    @ResponseStatus(HttpStatus.OK)
    public void addInvalid(@PathVariable long gameId, @PathVariable long userId, @RequestBody DTOInvalid move) {
        logger.debug("addMove");
        Move newMove = move;
        postService.addMoveToStack(gameId, newMove);
    }

    /* Add move rob to stack */
    @RequestMapping(method = RequestMethod.POST, value = CONTEXT + "/{gameId}/{userId}/rob")
    @ResponseStatus(HttpStatus.OK)
    public void addRob(@PathVariable long gameId, @PathVariable long userId, @RequestBody DTORob move) {
        logger.debug("addMove");
        Move newMove = move;
        postService.addMoveToStack(gameId, newMove);
    }
}
