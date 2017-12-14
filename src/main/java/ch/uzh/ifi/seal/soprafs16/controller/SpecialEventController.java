package ch.uzh.ifi.seal.soprafs16.controller;

import ch.uzh.ifi.seal.soprafs16.model.dto.DTOSpecialEvent;
import ch.uzh.ifi.seal.soprafs16.model.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpecialEventController extends GenericService {

    Logger logger = LoggerFactory.getLogger(SpecialEventController.class);

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
