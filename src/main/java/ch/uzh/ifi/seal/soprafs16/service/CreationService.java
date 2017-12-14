package ch.uzh.ifi.seal.soprafs16.service;


import ch.uzh.ifi.seal.soprafs16.constant.EAction;
import ch.uzh.ifi.seal.soprafs16.constant.ECharacter;
import ch.uzh.ifi.seal.soprafs16.constant.EPhase;
import ch.uzh.ifi.seal.soprafs16.constant.ERound;
import ch.uzh.ifi.seal.soprafs16.constant.ESpecialEvent;
import ch.uzh.ifi.seal.soprafs16.constant.ETurn;
import ch.uzh.ifi.seal.soprafs16.constant.LootType;
import ch.uzh.ifi.seal.soprafs16.model.*;
import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.environment.Train;
import ch.uzh.ifi.seal.soprafs16.model.environment.TrainLevel;
import ch.uzh.ifi.seal.soprafs16.model.environment.TrainWagon;
import ch.uzh.ifi.seal.soprafs16.model.game.Game;
import ch.uzh.ifi.seal.soprafs16.model.game.GameState;
import ch.uzh.ifi.seal.soprafs16.model.game.Phase;
import ch.uzh.ifi.seal.soprafs16.model.game.PlayerSequence;
import ch.uzh.ifi.seal.soprafs16.model.game.Round;
import ch.uzh.ifi.seal.soprafs16.model.game.SpecialAbility;
import ch.uzh.ifi.seal.soprafs16.model.game.SpecialEvent;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.ActionCard;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.AmmoCard;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.CardStack;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.RoundCard;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.Diamond;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.Lockbox;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.MoneyBag;
import ch.uzh.ifi.seal.soprafs16.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("creationService")
public class CreationService {

    @Autowired
    private CharacterRepository charRepo;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private LootRepository lootRepo;

    @Autowired
    private TrainRepository trainRepo;

    @Autowired
    private RoundCardRepository rcRepo;

    @Autowired
    private GameStateRepository gsRepo;

    @Autowired
    private ActionCardRepository actionCardRepo;

    @Autowired
    private AmmoCardRepository ammoCardRepo;

    @Autowired
    private CardStackRepository cardStackRepo;

    @Autowired
    private ShortGameRepository sgRepo;


    /* Creates all characters that the game has. */
    public void createCharacters(long gameId) {
        for (int i=0;i<6;i++)
        {
            SpecialAbility sa = new SpecialAbility();
            sa.setGameId(gameId);
            switch (i)
            {
                case 0: sa.setCharacter(ECharacter.TUCO);
                        break;
                case 1: sa.setCharacter(ECharacter.CHEYENNE);
                        break;
                case 2: sa.setCharacter(ECharacter.BELLE);
                        break;
                case 3: sa.setCharacter(ECharacter.DJANGO);
                        break;
                case 4: sa.setCharacter(ECharacter.DOC);
                        break;
                case 5: sa.setCharacter(ECharacter.GHOST);
                        break;
            }

            Character newChar = new Character();
            newChar.setCharacter(sa.getCharacter());
            newChar.setGameId(gameId);
            newChar.setSpecialAbility(sa);
            charRepo.save(newChar);
        }
    }

    /* Creates the trainlevels the wagons and then puts the wagons together to a train.
    Gets done according to number of players.
     */
    public void createTrains(long gameId)
    {
        Game game = gameRepo.findById(gameId);
        ArrayList<TrainWagon> wagons = new ArrayList<TrainWagon>();
        switch (game.getPlayers().size())
        {
            case 2:  for(int i=0; i<4; i++)
                     {
                         TrainLevel tL = new TrainLevel();
                         List<TrainLevel> tLs = new ArrayList<TrainLevel>();

                         tL.setGameId(gameId);
                         tL.setBaseLevel(true);
                         TrainLevel tLT = new TrainLevel();
                         tLT.setGameId(gameId);
                         tLT.setBaseLevel(false);
                         tLs.add(tL);
                         tLs.add(tLT);
                         TrainWagon tW = new TrainWagon();
                         tW.setGameId(gameId);
                         tW.setTrainLevels(tLs);
                         if (i==0)
                         {
                             tW.setLocomotive(true);
                             tL.setMarshal(true);
                         }
                         else
                         {
                             tW.setLocomotive(false);
                             tL.setMarshal(false);
                         }
                         wagons.add(tW);
                     }
                    break;
            case 3: for(int i=0; i<4; i++)
                     {
                         TrainLevel tL = new TrainLevel();
                         List<TrainLevel> tLs = new ArrayList<TrainLevel>();
                         tL.setGameId(gameId);
                         tL.setBaseLevel(true);
                         TrainLevel tLT = new TrainLevel();
                         tLT.setGameId(gameId);
                         tLT.setBaseLevel(false);
                         tLs.add(tL);
                         tLs.add(tLT);
                         TrainWagon tW = new TrainWagon();
                         tW.setGameId(gameId);
                         tW.setTrainLevels(tLs);
                         if (i==0)
                         {
                             tW.setLocomotive(true);
                             tL.setMarshal(true);
                         }
                         else
                         {
                             tW.setLocomotive(false);
                             tL.setMarshal(false);
                         }
                         wagons.add(tW);
                     }
                         break;
            case 4: for(int i=0; i<5; i++)
                      {
                          TrainLevel tL = new TrainLevel();
                          List<TrainLevel> tLs = new ArrayList<TrainLevel>();
                          tL.setGameId(gameId);
                          tL.setBaseLevel(true);
                          TrainLevel tLT = new TrainLevel();
                          tLT.setGameId(gameId);
                          tLT.setBaseLevel(false);
                          tLs.add(tL);
                          tLs.add(tLT);
                          TrainWagon tW = new TrainWagon();
                          tW.setGameId(gameId);
                          tW.setTrainLevels(tLs);
                          if (i==0)
                          {
                              tW.setLocomotive(true);
                              tL.setMarshal(true);
                          }
                          else
                          {
                              tW.setLocomotive(false);
                              tL.setMarshal(false);
                          }
                          wagons.add(tW);
                      }
                          break;
        }
        Train train = new Train();
        train.setGameId(gameId);
        train.setWagons(wagons);
        trainRepo.save(train);

    }

    /*Creates the loot according to the number of players*/
    public void createLoot(long gameId)
    {
        for (int i=0;i<2;i++) {
            Lockbox lB = new Lockbox();
            lB.setGameId(gameId);
            lB.setLootType(LootType.LOCKBOX);
            lootRepo.save(lB);
        }
        for (int i=0;i<6;i++)
        {
            Diamond dia = new Diamond();
            dia.setGameId(gameId);
            dia.setLootType(LootType.DIAMOND);
            lootRepo.save(dia);
        }

        for (int i=0;i<8;i++)
        {
            MoneyBag mB = new MoneyBag();
            mB.setValue(250);
            mB.setGameId(gameId);
            mB.setLootType(LootType.MONEYBAG);
            lootRepo.save(mB);
        }
        for(int i=0;i<2;i++)
        {
            MoneyBag mB = new MoneyBag();
            mB.setValue(300);
            mB.setGameId(gameId);
            mB.setLootType(LootType.MONEYBAG);
            lootRepo.save(mB);
        }
        for (int i=0;i<2;i++)
        {
            MoneyBag mB = new MoneyBag();
            mB.setValue(350);
            mB.setGameId(gameId);
            mB.setLootType(LootType.MONEYBAG);
            lootRepo.save(mB);
        }
        for (int i=0;i<2;i++)
        {
            MoneyBag mB = new MoneyBag();
            mB.setValue(400);
            mB.setGameId(gameId);
            mB.setLootType(LootType.MONEYBAG);
            lootRepo.save(mB);
        }
        for (int i=0;i<2;i++)
        {
            MoneyBag mB = new MoneyBag();
            mB.setValue(450);
            mB.setGameId(gameId);
            mB.setLootType(LootType.MONEYBAG);
            lootRepo.save(mB);
        }
        for (int i=0;i<2;i++)
        {
            MoneyBag mB = new MoneyBag();
            mB.setValue(500);
            mB.setGameId(gameId);
            mB.setLootType(LootType.MONEYBAG);
            lootRepo.save(mB);
        }
    }

    /*Create the roundcards. */
    public void createRoundCards(long gameId)
    {
       // Game game = gameRepo.findById(gameId);
        //First create the 3 trainstation cards
        for (int i=0;i<3;i++)
        {
            RoundCard rc = new RoundCard();
            rc.setGameId(gameId);
            rc.setMaxTurns(4);
            rc.setTrainStation(true);
            Map<Integer, ETurn> turns = new HashMap<Integer, ETurn>();
            if (sgRepo.findFirstByGameId(gameId).isShortV())
            {
                turns.put(1, ETurn.NORMAL);
                turns.put(2, ETurn.INVERSED);
                turns.put(3, ETurn.TUNNEL);
                turns.put(4, ETurn.DOUBLE_TURN);
            }
            else
            {
                turns.put(1, ETurn.NORMAL);
                turns.put(2, ETurn.NORMAL);
                turns.put(3, ETurn.TUNNEL);
                turns.put(4, ETurn.NORMAL);
            }
            rc.setTurns(turns);
            rc.setCurrentTurn(1);
            SpecialEvent sE = new SpecialEvent();
            switch (i) {
                case 0: {
                    sE.setSpecialEvent(ESpecialEvent.HOSTAGE);
                    rc.setSpecialEvent(sE);
                    rc.setCardNum(1);
                    break;
                }
                case 1: {
                    sE.setSpecialEvent(ESpecialEvent.REVENGE_MARSHAL);
                    rc.setSpecialEvent(sE);
                    rc.setCardNum(2);
                    break;
                }
                case 2: {
                    sE.setSpecialEvent(ESpecialEvent.POCKET_PICKING);
                    rc.setSpecialEvent(sE);
                    rc.setCardNum(3);
                    break;
                }
            }
            rcRepo.save(rc);
        }
        for (int i=0; i<7;i++)
        {
            RoundCard rc = new RoundCard();
            rc.setGameId(gameId);
            rc.setTrainStation(false);
            rc.setCurrentTurn(1);
            Map<Integer, ETurn> turns = new HashMap<Integer, ETurn>();
            SpecialEvent sE = new SpecialEvent();
            switch (i) {
                case 0: {
                    rc.setMaxTurns(4);
                    turns.put(1, ETurn.NORMAL);
                    turns.put(2, ETurn.NORMAL);
                    turns.put(3, ETurn.TUNNEL);
                    turns.put(4, ETurn.INVERSED);
                    rc.setTurns(turns);
                    sE.setSpecialEvent(ESpecialEvent.ANGRY_MARSHAL);
                    rc.setSpecialEvent(sE);
                    rc.setCardNum(4);
                    break;
                }
                case 1:{
                    rc.setMaxTurns(4);
                    turns.put(1, ETurn.NORMAL);
                    turns.put(2, ETurn.TUNNEL);
                    turns.put(3, ETurn.NORMAL);
                    turns.put(4, ETurn.NORMAL);
                    rc.setTurns(turns);
                    sE.setSpecialEvent(ESpecialEvent.CRANE);
                    rc.setSpecialEvent(sE);
                    rc.setCardNum(5);
                    break;
                }
                case 2:{
                    rc.setMaxTurns(4);
                    turns.put(1, ETurn.NORMAL);
                    turns.put(2, ETurn.NORMAL);
                    turns.put(3, ETurn.NORMAL);
                    turns.put(4, ETurn.NORMAL);
                    rc.setTurns(turns);
                    sE.setSpecialEvent(ESpecialEvent.BRAKE);
                    rc.setSpecialEvent(sE);
                    rc.setCardNum(6);
                    break;
                }
                case 3:{
                    rc.setMaxTurns(4);
                    turns.put(1, ETurn.NORMAL);
                    turns.put(2, ETurn.TUNNEL);
                    turns.put(3, ETurn.DOUBLE_TURN);
                    turns.put(4, ETurn.NORMAL);
                    rc.setTurns(turns);
                    sE.setSpecialEvent(ESpecialEvent.TAKEITALL);
                    rc.setSpecialEvent(sE);
                    rc.setCardNum(7);
                    break;
                }
                case 4:{
                    rc.setMaxTurns(5);
                    turns.put(1, ETurn.NORMAL);
                    turns.put(2, ETurn.NORMAL);
                    turns.put(3, ETurn.TUNNEL);
                    turns.put(4, ETurn.NORMAL);
                    turns.put(5, ETurn.NORMAL);
                    rc.setTurns(turns);
                    sE.setSpecialEvent(ESpecialEvent.PASSENGERS_RESSISTANCE);
                    rc.setSpecialEvent(sE);
                    rc.setCardNum(8);
                    break;
                }
                case 5:{
                    rc.setMaxTurns(3);
                    turns.put(1, ETurn.NORMAL);
                    turns.put(2, ETurn.DOUBLE_TURN);
                    turns.put(3, ETurn.NORMAL);
                    rc.setTurns(turns);
                    sE.setSpecialEvent(ESpecialEvent.NOTHING);
                    rc.setSpecialEvent(sE);
                    rc.setCardNum(9);
                    break;
                }
                case 6:{
                    rc.setMaxTurns(5);
                    turns.put(1, ETurn.NORMAL);
                    turns.put(2, ETurn.TUNNEL);
                    turns.put(3, ETurn.NORMAL);
                    turns.put(4, ETurn.TUNNEL);
                    turns.put(5, ETurn.NORMAL);
                    rc.setTurns(turns);
                    sE.setSpecialEvent(ESpecialEvent.NOTHING);
                    rc.setSpecialEvent(sE);
                    rc.setCardNum(10);
                    break;
                }
            }
            rcRepo.save(rc);
        }
    }

    /* Create game state. */
    public void createGameState(long gameId)
    {
        Game game = gameRepo.findById(gameId);

        Phase phase = new Phase();
        phase.setGameId(gameId);
        phase.setPhase(EPhase.PLANNING);

        PlayerSequence playerSequence = new PlayerSequence();
        playerSequence.setGameId(gameId);
        playerSequence.setDoubleRound(false);

        List<Boolean> hasPlayed = new ArrayList<Boolean>();
        for (int i=0;i<game.getPlayers().size();i++)
        {
            hasPlayed.add(false);
        }
        playerSequence.setHasPlayed(hasPlayed);
        List<User> players = new ArrayList<User>();
        for (int i=0; i<game.getPlayers().size(); i++)
        {
            players.add(game.getPlayers().get(i));
        }
        playerSequence.setSequence(players);
        playerSequence.setInversed(false);

        Round round = new Round();
        round.setGameId(gameId);
        round.setCurrentRound(ERound.ONE);
        round.setCurrentTurn(1);

        GameState gameState = new GameState();
        gameState.setGameId(gameId);
        gameState.setPhase(phase);
        gameState.setPlayerSequence(playerSequence);
        gameState.setRound(round);
        gameState.setPlayerTurn(playerSequence.getSequence().get(0));
        gameState.setSecondMove(false);

        gsRepo.save(gameState);
    }


    /* Create ammocards, this also gets done according to the number of players.*/
    public void createAmmoCards(long gameId)
    {
        for (int i=0; i< gameRepo.findOne(gameId).getPlayers().size();i++)
        {
            for(int j=0;j<6;j++) {
                AmmoCard ammoCard = new AmmoCard();
                ammoCard.setGameId(gameId);
                ammoCard.setBulletRound(j+1);
                ammoCard.setNeutral(false);
                ammoCard.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                ammoCard.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                //ammoCard.setOwner(gameRepo.findOne(gameId).getPlayers().get(i).getId());
                ammoCardRepo.save(ammoCard);
            }
        }

        // Neutral cards
        for (int i=0; i<13;i++)
        {
            AmmoCard ammoCard = new AmmoCard();
            ammoCard.setGameId(gameId);
            ammoCard.setNeutral(true);
            ammoCardRepo.save(ammoCard);
        }
    }

    /* Create action cards, this gets done according to number of players in the game.*/
    public void createActionCards(long gameId)
    {
        for (int i=0; i< gameRepo.findOne(gameId).getPlayers().size();i++)
        {
            for(int j=0; j<5; j++) {
                ActionCard ac1 = new ActionCard();
                ActionCard ac2 = new ActionCard();
                ac1.setGameId(gameId);
                ac1.setVisible(true);
                ac2.setGameId(gameId);
                ac2.setVisible(true);
                switch (j){
                    case 0: {
                        ac1.seteAction(EAction.CLIMB);
                        ac2.seteAction(EAction.CLIMB);
                        ac1.setCardNum(1);
                        ac2.setCardNum(1);
                        ac1.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                        ac2.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                        break;
                    }
                    case 1: {
                        ac1.seteAction(EAction.MOVE);
                        ac2.seteAction(EAction.MOVE);
                        ac1.setCardNum(2);
                        ac2.setCardNum(2);
                        ac1.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                        ac2.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                        break;
                    }
                    case 2:{
                        ac1.seteAction(EAction.MARSHAL);
                        ac2.seteAction(EAction.PUNCH);
                        ac1.setCardNum(3);
                        ac2.setCardNum(4);
                        ac1.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                        ac2.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                        break;
                    }
                    case 3:{
                        ac1.seteAction(EAction.ROB);
                        ac2.seteAction(EAction.ROB);
                        ac1.setCardNum(5);
                        ac2.setCardNum(5);
                        ac1.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                        ac2.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                        break;
                    }
                    case 4:{
                        ac1.seteAction(EAction.SHOOT);
                        ac2.seteAction(EAction.SHOOT);
                        ac1.setCardNum(6);
                        ac2.setCardNum(6);
                        ac1.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                        ac2.setCharacter(charRepo.findFirstByUserId(gameRepo.findOne(gameId).getPlayers().get(i).getId()).getCharacter());
                        break;
                    }

                }
                actionCardRepo.save(ac1);
                actionCardRepo.save(ac2);
            }
        }
    }

    /* Create the cardstack. */
    public void createCardStack(long gameId){
        CardStack cs = new CardStack();
        cs.setGameId(gameId);
        cardStackRepo.save(cs);
    }
}
