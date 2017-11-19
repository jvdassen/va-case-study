package ch.uzh.ifi.seal.soprafs16.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.uzh.ifi.seal.soprafs16.model.ShortGame;
import ch.uzh.ifi.seal.soprafs16.model.repositories.ShortGameRepository;
import ch.uzh.ifi.seal.soprafs16.service.AssigningService;
import ch.uzh.ifi.seal.soprafs16.service.CreationService;
import ch.uzh.ifi.seal.soprafs16.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.seal.soprafs16.GameConstants;
import ch.uzh.ifi.seal.soprafs16.model.Game;
import ch.uzh.ifi.seal.soprafs16.model.User;
import ch.uzh.ifi.seal.soprafs16.model.repositories.GameRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.UserRepository;


@RestController
public class GameServiceController
        extends GenericService {

    Logger logger  = LoggerFactory.getLogger(GameServiceController.class);

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private CreationService creaSer;

    @Autowired
    private AssigningService assSer;

    @Autowired
    private ResultService resultService;

    @Autowired
    private ShortGameRepository sgRepo;

    private final String   CONTEXT = "/games";

    /*
     * Context: /game
     */

    /* List all games on the server */
    @RequestMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> listGames() {
        logger.debug("listGames");
        List<Game> result = new ArrayList<>();
        gameRepo.findAll().forEach(result::add);
        return result;
    }

    /* Declare a game as short game */
    @RequestMapping(value = CONTEXT + "/{gameId}/short")
    @ResponseStatus(HttpStatus.OK)
    public void isShortGame(@PathVariable Long gameId) {
        logger.debug("isShortGame");
        ShortGame sg = sgRepo.findFirstByGameId(gameId);
        sg.setShortV(true);
        sgRepo.save(sg);
    }

    /* Declare a game as short game */
    @RequestMapping(value = CONTEXT + "/{gameId}/shorts")
    @ResponseStatus(HttpStatus.OK)
    public ShortGame getShortGame(@PathVariable Long gameId) {
        logger.debug("getShortGame");
        ShortGame sg = sgRepo.findFirstByGameId(gameId);
        return sg;
    }

    /* Create a new game */
    @RequestMapping(value = CONTEXT, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public long addGame(@RequestBody Game game, @RequestParam("token") String userToken) {
        logger.debug("addGame: " + game);

        User owner = userRepo.findByToken(userToken);

        if (owner != null) {
            game.setName(owner.getName());
            game.setOwner(owner.getUsername());
            game = gameRepo.save(game);
            creaSer.createCharacters(game.getId());
            ShortGame sg = new ShortGame();
            sg.setShortV(false);
            sg.setGameId(game.getId());
            sgRepo.save(sg);
            return game.getId();
        }

        return (long)0;
    }

    /*
     * Context: /game/{game-id}
     * get the game with the gameid
     */
    @RequestMapping(value = CONTEXT + "/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGame(@PathVariable Long gameId) {
        logger.debug("getGame: " + gameId);

        Game game = gameRepo.findOne(gameId);

        return game;
    }

    /* Start a game */
    @RequestMapping(value = CONTEXT + "/{gameId}/start", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void startGame(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("startGame: " + gameId);

        Game game = gameRepo.findOne(gameId);
        User owner = userRepo.findByToken(userToken);

        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
            creaSer.createLoot(gameId);
            creaSer.createTrains(gameId);
            creaSer.createRoundCards(gameId);
            creaSer.createGameState(gameId);
            creaSer.createActionCards(gameId);
            creaSer.createAmmoCards(gameId);
            creaSer.createCardStack(gameId);
            assSer.assignRoundCards(gameId);
            assSer.assignLoot(gameId);
            assSer.assignActionCards(gameId);
            assSer.assignAmmoCards(gameId);
            assSer.assignCharacters(gameId);
        }
    }

    /* Stop a game and get the results */
    @RequestMapping(value = CONTEXT + "/{gameId}/stop", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Integer> stopGame(@PathVariable Long gameId) {
        logger.debug("stopGame: " + gameId);

        return resultService.summarizeResult(gameId);

    }

    /*
     * Context: /game/{game-id}/player
     * List all players of a game
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/players")
    @ResponseStatus(HttpStatus.OK)
    public List<User> listPlayers(@PathVariable Long gameId) {
        logger.debug("listPlayers");

        Game game = gameRepo.findOne(gameId);
        if (game != null) {
            return game.getPlayers();
        }

        return null;
    }

    /* Add a player to a game */
    @RequestMapping(value = CONTEXT + "/{gameId}/players", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public long addPlayer(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("addPlayer: " + userToken);

        Game game = gameRepo.findOne(gameId);
        User player = userRepo.findByToken(userToken);

        if (game != null && player != null
                && game.getPlayers().size() < GameConstants.MAX_PLAYERS) {
            game.getPlayers().add(player);

            logger.debug("Game: " + game.getName() + " - player added: " + player.getUsername());

            userRepo.save(player);
            gameRepo.save(game);
            return player.getId();

        } else {
            logger.error("Error adding player with token: " + userToken);
        }
        return (long)0;
    }

    /* Get a player of a game */
    @RequestMapping(value = CONTEXT + "/{gameId}/players/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public User getPlayer(@PathVariable Long gameId, @PathVariable Integer playerId) {
        logger.debug("getPlayer: " + gameId);

        Game game = gameRepo.findOne(gameId);

        return game.getPlayers().get(playerId-1);
    }

}