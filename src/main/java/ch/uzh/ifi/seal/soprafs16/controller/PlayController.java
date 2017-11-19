package ch.uzh.ifi.seal.soprafs16.controller;

import ch.uzh.ifi.seal.soprafs16.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs16.model.*;
import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.repositories.*;
import ch.uzh.ifi.seal.soprafs16.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Laurenz on 07/04/16.
 */

@RestController
public class PlayController  extends GenericService {
//
//    Logger logger  = LoggerFactory.getLogger(PlayController.class);
//
//    private final String   CONTEXT = "/play";
//
//    @Autowired
//    private CharacterRepository charRepo;
//
//    @Autowired
//    private CreationService creaSer;
//
//    @Autowired
//    private CardStackRepository csRepo;
//
//    @Autowired
//    private AssigningService assSer;
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Autowired
//    private GameRepository gameRepo;
//
//    @Autowired
//    private AbilityService abilityService;
//
//    @Autowired
//    private ActionCardRepository acRepo;
//
//    @Autowired
//    private PostService postService;
//
//    @Autowired
//    private UpdateService updateService;
//
//    @Autowired
//    private ShortGameRepository sgRepo;
//
//    private boolean first = true;
//
//
//    //Start Game dummy
//    @RequestMapping(method = RequestMethod.GET, value = CONTEXT)
//    @ResponseStatus(HttpStatus.OK)
//    public boolean playGame() {
//        logger.debug("playGame");
//
//        if (first)
//        {
//            for (int i = 0; i < 4; i++)
//            {
//                User user = new User();
//                user.setName("dummy" + Integer.toString(i));
//                user.setUsername("dummy" + Integer.toString(i));
//                user.setStatus(UserStatus.OFFLINE);
//                user.setToken(UUID.randomUUID().toString());
//                user = userRepo.save(user);
//            }
//            Game game = new Game();
//            User owner = userRepo.findOne((long) 1);
//            game.setName(owner.getName());
//            game.setOwner(owner.getUsername());
//            gameRepo.save(game);
//            long dummyGameId = 1;
//            for (int i = 0; i < 2; i++)
//            {
//                User player = userRepo.findOne((long) (i + 1));
//                game = gameRepo.findOne((long) 1);
//                if (i == 0)
//                {
//                    game.setPlayers(new ArrayList<User>());
//                }
//                game.getPlayers().add(player);
//                userRepo.save(player);
//                gameRepo.save(game);
//            }
//            creaSer.createCharacters(dummyGameId);
//            for (int i = 0; i < 2; i++)
//            {
//                User player = userRepo.findOne((long) (i + 1));
//                Character assChar;
//                if (i == 3)
//                {
//                    assChar = charRepo.findOne((long) 6);
//                } else
//                {
//                    assChar = charRepo.findOne((long) (i + 1));
//                }
//                assChar.setUserId(player.getId());
//                charRepo.save(assChar);
//            }
//            ShortGame sg = new ShortGame();
//            sg.setShortV(true);
//            sg.setGameId(game.getId());
//            sgRepo.save(sg);
//
//            creaSer.createLoot(dummyGameId);
//            creaSer.createTrains(dummyGameId);
//            creaSer.createRoundCards(dummyGameId);
//            creaSer.createGameState(dummyGameId);
//            creaSer.createActionCards(dummyGameId);
//            creaSer.createAmmoCards(dummyGameId);
//            creaSer.createCardStack(dummyGameId);
//            assSer.assignRoundCards(dummyGameId);
//            assSer.assignLoot(dummyGameId);
//            assSer.assignActionCards(dummyGameId);
//            assSer.assignAmmoCards(dummyGameId);
//            assSer.assignCharacters(dummyGameId);
//
//
//           /* for (int i=0; i<20; i++)
//            {
//                long userId = (long) i % 5;
//                if (i != 4 && i!=3)
//                {
//                        List<ActionCard> ac = new ArrayList<ActionCard>();
//                        ac = acRepo.findByGameId(dummyGameId);
//                        ActionCard actionCard = ac.get(i);
//
//                        SpecialAbility sa = charRepo.findFirstByUserId(userId).getSpecialAbility();
//                        sa.doAbility(actionCard);
//                        acRepo.save(actionCard);
//
//                        postService.addActionCardToStack(dummyGameId, actionCard);
//
//
//                    updateService.updateSequence(dummyGameId);
//                }
//                else
//                {
//                    updateService.updateSequence(dummyGameId);
//                }
//            }*/
//            for (int i = 0; i < 20; i++)
//            {
//                if (i == 0 || i == 11)
//                {
//                    List<ActionCard> ac = new ArrayList<ActionCard>();
//                    ac = acRepo.findByGameId(dummyGameId);
//                    ActionCard actionCard = ac.get(i);
//                    if (i==0)
//                    {
//                        SpecialAbility sa = charRepo.findFirstByUserId((long) 1).getSpecialAbility();
//                        sa.doAbility(actionCard);
//                    }
//                    else{
//                        SpecialAbility sa = charRepo.findFirstByUserId((long) 2).getSpecialAbility();
//                        sa.doAbility(actionCard);
//                    }
//                    acRepo.save(actionCard);
//
//                    postService.addActionCardToStack(dummyGameId, actionCard);
//
//
//                    updateService.updateSequence(dummyGameId);
//
//                } else
//                {
//                    updateService.updateSequence(dummyGameId);
//                }
//
//            }
//            first = false;
//            return true;
//        }
//
//        else {
//            return false;
//        }
//    }
}
