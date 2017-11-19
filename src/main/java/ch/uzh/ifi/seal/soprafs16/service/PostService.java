package ch.uzh.ifi.seal.soprafs16.service;

import ch.uzh.ifi.seal.soprafs16.model.*;
import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Laurenz on 24/04/16.
 */

@Service("postService")
public class PostService {


    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private LootRepository lootRepo;

    @Autowired
    private TrainRepository trainRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ActionCardRepository actionCardRepo;

    @Autowired
    private AmmoCardRepository ammoCardRepo;

    @Autowired
    private CardStackRepository csRepo;

    @Autowired
    private CharacterRepository charRepo;

    @Autowired
    private GameStateRepository gameStateRepo;

    @Autowired
    private MoveRepository moveRepo;

    @Autowired

    private AbilityCheck abilityService;

    /*Adds an actioncard to the stack.*/
    public void addActionCardToStack(long gameId, ActionCard actionCard) {
        List<CardStack> cs = csRepo.findByGameId(gameId);
        // Check if cards should be visible
        RoundCard rc = csRepo.findByGameId(gameId).get(0).getCurrentRoundCard(gameStateRepo.findByGameId(gameId).getRound());
        if (rc.getTurns().get(rc.getCurrentTurn()).equals(ETurn.TUNNEL)) {
            actionCard.setVisible(false);
        }
        cs.get(0).addActionCardToStack(actionCard);
        csRepo.save(cs);
        actionCardRepo.save(actionCard);
    }

    /*Adds a move to the stack*/
    public void addMoveToStack(long gameId, Move move)
    {
        List<Move> moves = applyMove(gameId, move);
        CardStack cs = csRepo.findByGameId(gameId).get(0);
        for ( Move m : moves)
        {
            cs.addMove(m);
            moveRepo.save(m);
        }
        csRepo.save(cs);


    }

    /* Apply the consequences of the move onto the game
        @param move: The move the client has posted.
    .*/
    public List<Move> applyMove(long gameId, Move move)
    {
        Train train = trainRepo.findByGameId(gameId).get(0);
        CardStack cs = csRepo.findByGameId(gameId).get(0);
        List<User> players = gameRepo.findOne(gameId).getPlayers();
        List<Move> moves = new ArrayList<Move>();

        if (move instanceof DTOMove)
        {
            int pos = train.findUserInTrain(move.getUserId());
            Character character = train.getCharacterInTrain(move.getUserId());
            train.removeCharacterInTrain(move.getUserId());
            trainRepo.save(train);
            train.moveUser(character, pos, move);
            trainRepo.save(train);
            for (User us:players)
            {
                if (move.getUserId()!=us.getId())
                {
                    Move newMove = new DTOMove();
                    newMove.setUserId(us.getId());
                    moves.add(newMove);
                }
                else
                {
                }
            }
            moves.add(move);
            return moves;

        }
        else if (move instanceof  DTOClimb)
        {
            int pos = train.findUserInTrain(move.getUserId());
            train.removeCharacterInTrain(move.getUserId());
            trainRepo.save(train);
            train.climbCharacter(charRepo.findFirstByUserId(move.getUserId()), pos);
            trainRepo.save(train);
            for (User us:players)
            {
                if (move.getUserId()!=us.getId())
                {
                    Move newMove = new DTOClimb();
                    newMove.setUserId(us.getId());
                    moves.add(newMove);
                }
                else
                {
                    move.setGameId(gameId);
                }
            }
            moves.add(move);
            return moves;
        }
        else if (move instanceof DTOShoot)
        {
            AmmoCard marshalAmmoCard = new AmmoCard();
            boolean walkedIntoMarshal = false;
            if (abilityService.hasSpecialAbility(gameId,move.getUserId(), move))
            {
                boolean left = false;
                if (train.findUserInTrain(move.getUserId())>train.findUserInTrain(((DTOShoot) move).getVictim().getId()))
                {
                    if(train.walkedIntoMarshal(train.findUserInTrain(((DTOShoot) move).getVictim().getId()), true))
                    {
                        walkedIntoMarshal = true;
                        left=true;
                    }
                }
                else
                {
                    if(train.walkedIntoMarshal(train.findUserInTrain(((DTOShoot) move).getVictim().getId()), false))
                    {
                        walkedIntoMarshal = true;
                        left = false;
                    }
                }
                // If django shot the person into the wagon with the marshal
                if (walkedIntoMarshal)
                {
                    int userPosition = train.findUserInTrain(((DTOShoot)move).getVictim().getId());
                    Character character = train.getCharacterInTrain(((DTOShoot)move).getVictim().getId());
                    train.removeCharacterInTrain(((DTOShoot)move).getVictim().getId());
                    trainRepo.save(train);
                    train.moveUser(character, userPosition, left);
                    trainRepo.save(train);
                    if (cs.getAmmoCard() != null)
                    {
                        marshalAmmoCard = cs.getAmmoCard();
                        cs.removeLastAmmoCard();
                        //marshalAmmoCard.setUserId(((DTOShoot) move).getVictim().getId());
                        csRepo.save(cs);
                        ammoCardRepo.save(marshalAmmoCard);
                    }
                }
                else
                {
                    int pos = train.findUserInTrain(((DTOShoot) move).getVictim().getId());
                    Character character = train.getCharacterInTrain(((DTOShoot) move).getVictim().getId());
                    train.removeCharacterInTrain(((DTOShoot) move).getVictim().getId());
                    trainRepo.save(train);
                    charRepo.findFirstByUserId(move.getUserId()).getSpecialAbility().doAbility(character, pos, move, train);
                    trainRepo.save(train);
                }
            }
            List<AmmoCard> ammoCards = ammoCardRepo.findByUserId(move.getUserId());
            AmmoCard ammoCard = new AmmoCard();
            for (AmmoCard amc: ammoCards)
            {
                int counter = 0;
                ammoCard = amc;
                if (amc.getVictim() == 0) {

                    for (int i=0; i<ammoCards.size();i++) {
                        if (ammoCards.get(i).getBulletRound()>amc.getBulletRound() && ammoCards.get(i).getVictim()==0)
                        {
                            break;
                        }
                        else
                        {
                            counter++;
                        }
                    }
                    if (counter == 6)
                    {
                        break;
                    }
                }
            }
            ammoCard.setVictim(((DTOShoot)move).getVictim().getId());
            ammoCardRepo.save(ammoCard);
            for (User us:players)
            {
                if (move.getUserId()!=us.getId())
                {
                    if (((DTOShoot)move).getVictim().getId()!=us.getId())
                    {
                        Move newMove = new DTOShoot();
                        newMove.setUserId(us.getId());
                        newMove.setGameId(gameId);
                        ((DTOShoot)newMove).setVictim(((DTOShoot) move).getVictim());
                        ((DTOShoot)newMove).setVictim(userRepo.findOne(((DTOShoot) move).getVictim().getId()));
                        moves.add(newMove);
                    }
                    else
                    {
                        Move newMove = new DTOShoot();
                        newMove.setUserId(us.getId());
                        newMove.setGameId(gameId);
                        ((DTOShoot)newMove).setVictim(((DTOShoot) move).getVictim());
                        if (ammoCard.getId() != null)
                        {
                            ((DTOShoot) newMove).setVictimAmmoCard(ammoCard);
                        }
                        if (walkedIntoMarshal)
                        {
                            ((DTOShoot)move).setMarshalAmmoCard(marshalAmmoCard);
                        }
                        moves.add(newMove);
                    }
                }
            }
            moves.add(move);
            return moves;
        }
        else if (move instanceof DTOPunch)
        {
            boolean foundNothing = true;
            if (abilityService.hasSpecialAbility(gameId,move.getUserId(), move))
            {
                List<Loot> loots = lootRepo.findByUserId((((DTOPunch) move).getVictim().getId()));
                List<Loot> lootr = new ArrayList<Loot>();
                for (Loot loot : loots)
                {
                    if (loot instanceof MoneyBag)
                    {
                        lootr.add(loot);
                    }
                }
                if (lootr.size() > 0)
                {
                    Loot loot = lootr.get(ThreadLocalRandom.current().nextInt(0, lootr.size()));
                    charRepo.findFirstByUserId(move.getUserId()).getSpecialAbility().doAbility(move, loot);
                    lootRepo.save(loot);
                    foundNothing = false;

                    //Move the character after the punch
                    int userPosition = train.findUserInTrain(((DTOPunch)move).getVictim().getId());
                    boolean hitMarshal = train.walkedIntoMarshal(userPosition, ((DTOPunch) move).isLeft());
                    Character character = train.getCharacterInTrain(((DTOPunch)move).getVictim().getId());
                    train.removeCharacterInTrain(((DTOPunch)move).getVictim().getId());
                    trainRepo.save(train);
                    train.moveUser(character, userPosition, ((DTOPunch) move).isLeft());
                    trainRepo.save(train);
                    AmmoCard ammoCard = new AmmoCard();
                    if(hitMarshal)
                    {
                        if (cs.getAmmoCard() != null)
                        {
                            ammoCard = cs.getAmmoCard();
                            cs.removeLastAmmoCard();
                            //ammoCard.setUserId(((DTOPunch) move).getVictim().getId());
                            csRepo.save(cs);
                            ammoCardRepo.save(ammoCard);
                        }
                    }

                    for (User us : players)
                    {
                        if ( move.getUserId() != us.getId())
                        {
                            if (((DTOPunch) move).getVictim().getId() != us.getId())
                            {
                                Move newMove = new DTOPunch();
                                newMove.setUserId(us.getId());
                                newMove.setGameId(gameId);
                                ((DTOPunch)newMove).setVictim(((DTOPunch) move).getVictim());
                                ((DTOPunch)newMove).setDTOLoot(null);
                                moves.add(newMove);
                            } else
                            {
                                Move newMove = new DTOPunch();
                                newMove.setUserId(us.getId());
                                newMove.setGameId(gameId);
                                ((DTOPunch)newMove).setVictim(((DTOPunch) move).getVictim());
                                ((DTOPunch)newMove).setDTOLoot(null);
                                if (loot != null)
                                {
                                    ((DTOPunch) newMove).setStolenLoot(loot);
                                }
                                if (hitMarshal)
                                {
                                    ((DTOPunch) move).setShotAmmoCard(ammoCard);
                                }
                                moves.add(newMove);
                            }
                        } else
                        {
                            ((DTOPunch)move).setDTOLoot(null);
                            if (loot != null)
                            {
                                ((DTOPunch) move).setCheyenneLoot(loot);
                            }
                        }
                    }
                    moves.add(move);
                    return moves;
                }
            }
            if (foundNothing) {
                List<Loot> loots = lootRepo.findByUserId((((DTOPunch) move).getVictim().getId()));
                List<Loot> lootr = new ArrayList<Loot>();
                for (Loot loot : loots)
                {
                    if (loot.getValue() == ((DTOPunch) move).getDTOLoot().getValue() && loot.getLootType() == ((DTOPunch) move).getDTOLoot().getLootType() )
                        lootr.add(loot);
                        loot.setUserId(0);
                        train.getWagons().get(train.findUserInTrain(move.getUserId())/2).getTrainLevels().get(train.findUserInTrain(move.getUserId())%2).addLoot(loot);
                        lootRepo.save(loot);
                        trainRepo.save(train);
                        csRepo.save(cs);
                        break;
                }
                //Move the character after the punch
                int userPosition = train.findUserInTrain(((DTOPunch)move).getVictim().getId());
                boolean hitMarshal = train.walkedIntoMarshal(userPosition, ((DTOPunch) move).isLeft());
                Character character = train.getCharacterInTrain(((DTOPunch)move).getVictim().getId());
                train.removeCharacterInTrain(((DTOPunch)move).getVictim().getId());
                trainRepo.save(train);
                train.moveUser(character, userPosition, ((DTOPunch) move).isLeft());
                trainRepo.save(train);
                AmmoCard ammoCard = new AmmoCard();
                if(hitMarshal)
                {
                    if (cs.getAmmoCard() != null)
                    {
                        ammoCard = cs.getAmmoCard();
                        cs.removeLastAmmoCard();
                        //ammoCard.setUserId(((DTOPunch) move).getVictim().getId());
                        csRepo.save(cs);
                        ammoCardRepo.save(ammoCard);
                    }
                }
                for (User us:players)
                {
                    if (move.getUserId()!=us.getId())
                    {
                        if (((DTOPunch)move).getVictim().getId()!=us.getId())
                        {
                            Move newMove = new DTOPunch();
                            newMove.setUserId(us.getId());
                            newMove.setGameId(gameId);
                            ((DTOPunch)newMove).setVictim(((DTOPunch) move).getVictim());
                            ((DTOPunch)newMove).setDTOLoot(null);
                            ((DTOPunch) newMove).setVictim(userRepo.findOne(((DTOPunch) move).getVictim().getId()));
                            moves.add(newMove);
                        }
                        else
                        {
                            Move newMove = new DTOPunch();
                            newMove.setUserId(us.getId());
                            newMove.setGameId(gameId);
                            ((DTOPunch)newMove).setVictim(((DTOPunch) move).getVictim());
                            ((DTOPunch)newMove).setDTOLoot(null);
                            if (lootr.size()>0)
                            {
                                ((DTOPunch) newMove).setStolenLoot(lootr.get(0));
                            }
                            if (hitMarshal)
                            {
                                ((DTOPunch) move).setShotAmmoCard(ammoCard);
                            }
                            moves.add(newMove);
                        }
                    }
                    else
                    {
                        ((DTOPunch)move).setDTOLoot(null);
                    }
                }
                moves.add(move);
                return moves;
            }

        }

        else if (move instanceof DTORob)
        {
            int userPosition= train.findUserInTrain(move.getUserId());
            for (Loot loot : train.getWagons().get(userPosition/2).getTrainLevels().get(userPosition%2).getLoot())
            {
             if (loot.getValue()==((DTORob) move).getDTOLoot().getValue() && loot.getLootType().equals(((DTORob) move).getDTOLoot().getLootType()))
             {
                 train.getWagons().get(userPosition/2).getTrainLevels().get(userPosition%2).removeLoot(loot);
                 loot.setUserId(move.getUserId());
                 new MoneyBag();
                 lootRepo.save(loot);
                 trainRepo.save(train);
                 break;
             }
            }
            for (User us:players)
            {
                if (move.getUserId()!=us.getId())
                {
                    Move newMove = new DTORob();
                    newMove.setUserId(us.getId());
                    newMove.setGameId(gameId);
                    moves.add(newMove);
                }
                else
                {
                    ((DTORob)move).setDTOLoot(null);
                }
            }
            moves.add(move);
            return moves;
        }
        else if (move instanceof DTOMarshal)
        {
            train.moveMarshal(((DTOMarshal) move).isLeft());
            int marshalPosition = train.findMarshal();
            List<AmmoCard> amc = new ArrayList<AmmoCard>();
            for (int i=0;i<train.getWagons().get(marshalPosition/2).getTrainLevels().get(0).getCharacters().size();i++)
            {
                if (cs.getAmmoCard() != null)
                {
                    AmmoCard ammoCard = cs.getAmmoCard();
                    cs.removeLastAmmoCard();
                    ammoCard.setVictim(train.getWagons().get(marshalPosition/2).getTrainLevels().get(0).getCharacters().get(i).getUserId());
                    amc.add(ammoCard);
                    csRepo.save(cs);
                    ammoCardRepo.save(ammoCard);
                }
            }
            List<Character> characters = new ArrayList<Character>();
            for (Character chr : train.getWagons().get(train.findMarshal()/2).getTrainLevels().get(0).getCharacters())
            {
                characters.add(chr);
            }
            train.removeFleeFromMarshal(characters);
            trainRepo.save(train);
            train.addFleeFromMarshal(characters);
            trainRepo.save(train);

            for (User us:players)
            {
                if (move.getUserId()!=us.getId())
                {
                    Move newMove = new DTOMarshal();
                    newMove.setUserId(us.getId());
                    newMove.setGameId(gameId);
                    if (amc.size()>0)
                    {
                        for (AmmoCard va: amc)
                        {
                            if (va.getVictim() == us.getId())
                            {
                                ((DTOMarshal) newMove).setShotAmmoCard(va);
                            }
                        }
                    }
                    moves.add(newMove);
                }
                else
                {
                    if (amc.size()>0)
                    {
                        for (AmmoCard va : amc)
                        {
                            if (va.getVictim() == us.getId())
                            {
                                ((DTOMarshal) move).setShotAmmoCard(va);
                            }
                        }
                    }
                }
            }
            moves.add(move);
            return moves;
        }
        else if (move instanceof DTOInvalid)
        {
            AmmoCard amc = new AmmoCard();
            if(((DTOInvalid) move).isWalkedIntoMarshal())
            {
                if (cs.getAmmoCard() != null)
                {
                    AmmoCard ammoCard = cs.getAmmoCard();
                    amc = ammoCard;
                    cs.removeLastAmmoCard();
                    //ammoCard.setUserId(move.getUserId());
                    amc.setVictim(move.getUserId());
                    csRepo.save(cs);
                    ammoCardRepo.save(ammoCard);
                }
                train.findUserInTrain(move.getUserId());
                int marshalPos = train.findMarshal();
                Character character = train.getCharacterInTrain(move.getUserId());
                train.removeCharacterInTrain(move.getUserId());
                trainRepo.save(train);
                train.addCharacter(character, marshalPos + 1);
                trainRepo.save(train);
            }
            for (User us:players)
            {
                if (move.getUserId()!=us.getId())
                {
                    Move newMove = new DTOInvalid();
                    newMove.setUserId(us.getId());
                    newMove.setGameId(gameId);
                    moves.add(newMove);
                }
                else
                {
                    //amc gets initialized so the null check doesnt work
                    if (amc.getId() != null)
                    {
                        ((DTOInvalid) move).setAmmoCard(amc);
                    }
                }
            }
            moves.add(move);
            return moves;
        }

        return null;
    }


}
