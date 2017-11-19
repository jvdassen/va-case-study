package ch.uzh.ifi.seal.soprafs16.controller;

/**
 * Created by Laurenz on 14/04/16.
 */

import ch.uzh.ifi.seal.soprafs16.model.Train;
import ch.uzh.ifi.seal.soprafs16.model.repositories.TrainRepository;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class TrainController extends GenericService{

    Logger logger  = LoggerFactory.getLogger(TrainController.class);

    @Autowired
    private TrainRepository trainRepo;

    private final String   CONTEXT = "/trains";

    /* Get all trains on the server */
    @RequestMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<Train> listTrains() {
        logger.debug("listTrains");
        List<Train> result = new ArrayList<Train>();
        trainRepo.findAll().forEach(result::add);
        return result;
    }

    /* Get the train of a certain game */
    @RequestMapping(value = CONTEXT + "/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public Train getTrain(@PathVariable long gameId) {
        logger.debug("getTrain");
        Train result = new Train();
        result = trainRepo.findByGameId(gameId).get(0);
        return result;
    }
}
