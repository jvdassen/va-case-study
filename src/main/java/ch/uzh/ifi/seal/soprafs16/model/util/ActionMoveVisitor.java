package ch.uzh.ifi.seal.soprafs16.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;

import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.User;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOClimb;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOInvalid;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOMarshal;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOMove;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOPunch;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTORob;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOShoot;
import ch.uzh.ifi.seal.soprafs16.model.environment.Train;
import ch.uzh.ifi.seal.soprafs16.model.environment.TrainLevel;
import ch.uzh.ifi.seal.soprafs16.model.environment.TrainWagon;
import ch.uzh.ifi.seal.soprafs16.model.game.Move;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.AmmoCard;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.CardStack;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.Loot;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.MoneyBag;
import ch.uzh.ifi.seal.soprafs16.model.repositories.ActionCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.AmmoCardRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CardStackRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.CharacterRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.GameRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.GameStateRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.LootRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.MoveRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.TrainRepository;
import ch.uzh.ifi.seal.soprafs16.model.repositories.UserRepository;
import ch.uzh.ifi.seal.soprafs16.service.AbilityCheck;

public class ActionMoveVisitor implements Visitor {
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
    private CardStackRepository allCards;

    @Autowired
    private CharacterRepository charRepo;

    @Autowired
    private GameStateRepository gameStateRepo;

    @Autowired
    private MoveRepository moveRepo;

    @Autowired
    private AbilityCheck abilityService;
    
    long gameId;
    
    Train completeTrain;
    CardStack cardStack;
    List<User> players;
    List<Move> allResultingMoves;
    
    int marshalPosition;
    TrainWagon trainWagonWithMarshal;
    TrainLevel trainLevelWithMarshal;
    
    long movingUser;
    long victim;
    int movingUserPosition;
    int victimPosition;
    Character movingCharacter;
    List<AmmoCard> ammoCardsOfMovingUser;
    
    public  ActionMoveVisitor(long gameId){
    	gameId = gameId;
        completeTrain = trainRepo.findByGameId(gameId).get(0);
        cardStack = allCards.findByGameId(gameId).get(0);
        players = gameRepo.findOne(gameId).getPlayers();
        allResultingMoves = new ArrayList<Move>();

        marshalPosition = completeTrain.findMarshal();
        trainWagonWithMarshal = completeTrain.getWagons().get(marshalPosition);
        trainLevelWithMarshal = trainWagonWithMarshal.getTrainLevels().get(0);
    }
	
    
    public void setUp(Move move){
        movingUser = move.getUserId();
        victim = ((DTOShoot) move).getVictim().getId();

        movingUserPosition = completeTrain.findUserInTrain(movingUser);
        victimPosition = completeTrain.findUserInTrain(victim);
        
        movingCharacter = charRepo.findFirstByUserId(movingUser);
        ammoCardsOfMovingUser = ammoCardRepo.findByUserId(movingUser);
    }

	public List<Move> visit(DTOClimb moveToApply) {
		setUp(moveToApply);
		
        Character character = completeTrain.getCharacterInTrain(movingUser);
        completeTrain.removeCharacterInTrain(movingUser);
        trainRepo.save(completeTrain);
        completeTrain.moveUser(character, movingUserPosition, moveToApply);
        trainRepo.save(completeTrain);
        for (User user : players) {
            if (movingUser != user.getId()) {
                Move newMove = new DTOMove();
                newMove.setUserId(user.getId());
                allResultingMoves.add(newMove);
            }
        }
        allResultingMoves.add(moveToApply);
        return allResultingMoves;
	}

	@Override
	public List<Move> visit(DTOInvalid moveToApply) {
		setUp(moveToApply);
		
        AmmoCard amc = new AmmoCard();
        if(((DTOInvalid) moveToApply).isWalkedIntoMarshal()) {
            if (cardStack.getAmmoCard() != null) {
                AmmoCard ammoCard = cardStack.getAmmoCard();
                amc = ammoCard;
                cardStack.removeLastAmmoCard();
                amc.setVictim(movingUser);
                allCards.save(cardStack);
                ammoCardRepo.save(ammoCard);
            }

            Character character = completeTrain.getCharacterInTrain(movingUser);
            completeTrain.removeCharacterInTrain(movingUser);
            trainRepo.save(completeTrain);
            completeTrain.addCharacter(character, marshalPosition + 1);
            trainRepo.save(completeTrain);
        }
        for (User us:players) {
            if (movingUser != us.getId()) {
                Move newMove = new DTOInvalid();
                newMove.setUserId(us.getId());
                newMove.setGameId(gameId);
                allResultingMoves.add(newMove);
            } else {
                //amc gets initialized so the null check doesnt work
                if (amc.getId() != null) {
                    ((DTOInvalid) moveToApply).setAmmoCard(amc);
                }
            }
        }
        allResultingMoves.add(moveToApply);
        return allResultingMoves;	}

	@Override
	public List<Move> visit(DTOMarshal moveToApply) {
		setUp(moveToApply);
		
        completeTrain.moveMarshal(((DTOMarshal) moveToApply).isLeft());
        List<AmmoCard> ammoCardsAfterMove = new ArrayList<AmmoCard>();

        List<Character> affectedCharacters = trainLevelWithMarshal.getCharacters();

        for (int i = 0; i < affectedCharacters.size(); i++) {
            if (cardStack.getAmmoCard() != null) {
                AmmoCard ammoCard = cardStack.getAmmoCard();
                cardStack.removeLastAmmoCard();

                ammoCard.setVictim(affectedCharacters.get(i).getUserId());
                ammoCardsAfterMove.add(ammoCard);
                allCards.save(cardStack);
                ammoCardRepo.save(ammoCard);
            }
        }

        completeTrain.removeFleeFromMarshal(affectedCharacters);
        trainRepo.save(completeTrain);
        completeTrain.addFleeFromMarshal(affectedCharacters);
        trainRepo.save(completeTrain);

        for (User us:players) {
            if (movingUser != us.getId()) {
                Move newMove = new DTOMarshal();
                newMove.setUserId(us.getId());
                newMove.setGameId(gameId);
                if (ammoCardsAfterMove.size() > 0) {
                    for (AmmoCard va: ammoCardsAfterMove) {
                        if (va.getVictim() == us.getId()) {
                            ((DTOMarshal) newMove).setShotAmmoCard(va);
                        }
                    }
                }
                allResultingMoves.add(newMove);
            } else {
                if (ammoCardsAfterMove.size() > 0) {
                    for (AmmoCard va : ammoCardsAfterMove) {
                        if (va.getVictim() == us.getId()) {
                            ((DTOMarshal) moveToApply).setShotAmmoCard(va);
                        }
                    }
                }
            }
        }
        allResultingMoves.add(moveToApply);
        return allResultingMoves;	}

	@Override
	public List<Move> visit(DTOMove moveToApply) {
		setUp(moveToApply);
		           
		Character character = completeTrain.getCharacterInTrain(movingUser);		
		completeTrain.removeCharacterInTrain(movingUser);		
		trainRepo.save(completeTrain);		
		completeTrain.moveUser(character, movingUserPosition, moveToApply);		
		trainRepo.save(completeTrain);		
		for (User user : players) {		
			if (movingUser != user.getId()) {
				Move newMove = new DTOMove();		
				newMove.setUserId(user.getId());		
				allResultingMoves.add(newMove);		
			}		
		 }		
		 allResultingMoves.add(moveToApply);		
		 return allResultingMoves;
	}

	@Override
	public List<Move> visit(DTOPunch moveToApply) {
		setUp(moveToApply);
        boolean foundNothing = true;
        if (abilityService.hasSpecialAbility(gameId, movingUser, moveToApply)) {
            List<Loot> loots = lootRepo.findByUserId((( (DTOPunch) moveToApply).getVictim().getId()));
            List<Loot> lootr = new ArrayList<Loot>();
            for (Loot loot : loots) {
                if (loot instanceof MoneyBag) {
                    lootr.add(loot);
                }
            }
            if (lootr.size() > 0) {
                Loot loot = lootr.get(ThreadLocalRandom.current().nextInt(0, lootr.size()));
                movingCharacter.getSpecialAbility().doAbility(moveToApply, loot);
                lootRepo.save(loot);
                foundNothing = false;

                //Move the character after the punch
                boolean hitMarshal = completeTrain.walkedIntoMarshal(movingUserPosition, ((DTOPunch) moveToApply).isLeft());
                Character character = completeTrain.getCharacterInTrain(((DTOPunch)moveToApply).getVictim().getId());
                completeTrain.removeCharacterInTrain(((DTOPunch)moveToApply).getVictim().getId());
                trainRepo.save(completeTrain);
                completeTrain.moveUser(character, movingUserPosition, ((DTOPunch) moveToApply).isLeft());
                trainRepo.save(completeTrain);
                AmmoCard ammoCard = new AmmoCard();
                if(hitMarshal) {
                    if (cardStack.getAmmoCard() != null) {
                        ammoCard = cardStack.getAmmoCard();
                        cardStack.removeLastAmmoCard();
                        allCards.save(cardStack);
                        ammoCardRepo.save(ammoCard);
                    }
                }

                for (User otherUser : players) {
                    if ( movingUser != otherUser.getId()) {
                        if (((DTOPunch) moveToApply).getVictim().getId() != otherUser.getId()) {
                            Move newMove = new DTOPunch();
                            newMove.setUserId(otherUser.getId());
                            newMove.setGameId(gameId);
                            ((DTOPunch)newMove).setVictim(((DTOPunch) moveToApply).getVictim());
                            ((DTOPunch)newMove).setDTOLoot(null);
                            allResultingMoves.add(newMove);
                        } else {
                            Move newMove = new DTOPunch();
                            newMove.setUserId(otherUser.getId());
                            newMove.setGameId(gameId);
                            ((DTOPunch)newMove).setVictim(((DTOPunch) moveToApply).getVictim());
                            ((DTOPunch)newMove).setDTOLoot(null);
                            if (loot != null) {
                                ((DTOPunch) newMove).setStolenLoot(loot);
                            }
                            if (hitMarshal) {
                                ((DTOPunch) moveToApply).setShotAmmoCard(ammoCard);
                            }
                            allResultingMoves.add(newMove);
                        }
                    } else {
                        ((DTOPunch) moveToApply).setDTOLoot(null);
                        if (loot != null) {
                            ((DTOPunch) moveToApply).setCheyenneLoot(loot);
                        }
                    }
                }
                allResultingMoves.add(moveToApply);
                return allResultingMoves;
            }
        }
        if (foundNothing) {
            List<Loot> loots = lootRepo.findByUserId((((DTOPunch) moveToApply).getVictim().getId()));
            List<Loot> resultingLoot = new ArrayList<Loot>();
            for (Loot loot : loots) {
            	boolean matchingLootValue = loot.getValue() == ((DTOPunch) moveToApply).getDTOLoot().getValue();
            	boolean matchingLootType = loot.getLootType() == ((DTOPunch) moveToApply).getDTOLoot().getLootType();

                if (matchingLootValue && matchingLootType)
                    resultingLoot.add(loot);
                    loot.setUserId(0);

                    int targetUserPosition = movingUserPosition/2;
                    TrainWagon targetTrainWagon = completeTrain.getWagons().get(targetUserPosition);
                    TrainLevel targetTrainLevel = targetTrainWagon.getTrainLevels().get(targetUserPosition);

                    targetTrainLevel.addLoot(loot);

                    lootRepo.save(loot);
                    trainRepo.save(completeTrain);
                    allCards.save(cardStack);
                    break;
            }
            //Move the character after the punch
            boolean hitMarshal = completeTrain.walkedIntoMarshal(movingUserPosition, ((DTOPunch) moveToApply).isLeft());
            Character character = completeTrain.getCharacterInTrain(((DTOPunch)moveToApply).getVictim().getId());
            completeTrain.removeCharacterInTrain(((DTOPunch)moveToApply).getVictim().getId());
            trainRepo.save(completeTrain);
            completeTrain.moveUser(character, movingUserPosition, ((DTOPunch) moveToApply).isLeft());
            trainRepo.save(completeTrain);
            AmmoCard ammoCard = new AmmoCard();
            if(hitMarshal) {
                if (cardStack.getAmmoCard() != null) {
                    ammoCard = cardStack.getAmmoCard();
                    cardStack.removeLastAmmoCard();
                    allCards.save(cardStack);
                    ammoCardRepo.save(ammoCard);
                }
            }
            for (User us:players) {
                if (movingUser != us.getId()) {
                    if (((DTOPunch)moveToApply).getVictim().getId() != us.getId()) {
                        Move newMove = new DTOPunch();
                        newMove.setUserId(us.getId());
                        newMove.setGameId(gameId);
                        ((DTOPunch)newMove).setVictim(((DTOPunch) moveToApply).getVictim());
                        ((DTOPunch)newMove).setDTOLoot(null);
                        ((DTOPunch) newMove).setVictim(userRepo.findOne(((DTOPunch) moveToApply).getVictim().getId()));
                        allResultingMoves.add(newMove);
                    } else {
                        Move newMove = new DTOPunch();
                        newMove.setUserId(us.getId());
                        newMove.setGameId(gameId);
                        ((DTOPunch)newMove).setVictim(((DTOPunch) moveToApply).getVictim());
                        ((DTOPunch)newMove).setDTOLoot(null);
                        if (resultingLoot.size() > 0) {
                            ((DTOPunch) newMove).setStolenLoot(resultingLoot.get(0));
                        }
                        if (hitMarshal) {
                            ((DTOPunch) moveToApply).setShotAmmoCard(ammoCard);
                        }
                        allResultingMoves.add(newMove);
                    }
                } else {
                    ((DTOPunch)moveToApply).setDTOLoot(null);
                }
            }
            allResultingMoves.add(moveToApply);
            return allResultingMoves;
        }
		return null;
	}

	@Override
	public List<Move> visit(DTORob moveToApply) {
		setUp(moveToApply);
        for (Loot loot : completeTrain.getWagons().get(movingUserPosition/2).getTrainLevels().get(movingUserPosition%2).getLoot()) {


        	boolean lootValuesMatching = loot.getValue() == ((DTORob) moveToApply).getDTOLoot().getValue();
        	boolean lootTypesMatching = loot.getLootType().equals(((DTORob) moveToApply).getDTOLoot().getLootType());

        	if (lootValuesMatching && lootTypesMatching) {

                completeTrain.getWagons().get(movingUserPosition/2).getTrainLevels().get(movingUserPosition%2).removeLoot(loot);
                loot.setUserId(movingUser);
                new MoneyBag();
                lootRepo.save(loot);
                trainRepo.save(completeTrain);
                break;
            }
        }
        for (User us:players) {
            if (movingUser != us.getId()) {
                Move newMove = new DTORob();
                newMove.setUserId(us.getId());
                newMove.setGameId(gameId);
                allResultingMoves.add(newMove);
            } else {
                ((DTORob)moveToApply).setDTOLoot(null);
            }
        }
        allResultingMoves.add(moveToApply);
        return allResultingMoves;	}

	@Override
	public List<Move> visit(DTOShoot moveToApply) {
		setUp(moveToApply);
        AmmoCard marshalAmmoCard = new AmmoCard();
        boolean walkedIntoMarshal = false;
        if (abilityService.hasSpecialAbility(gameId, movingUser, moveToApply)) {
            boolean goesLeft = false;
            if (movingUser > victim) {
                if(completeTrain.walkedIntoMarshal(movingUserPosition, true)) {
                    walkedIntoMarshal = true;
                    goesLeft = true;
                }
            } else {
                if(completeTrain.walkedIntoMarshal(victimPosition, false)) {
                    walkedIntoMarshal = true;
                    goesLeft = false;
                }
            }
            // If django shot the person into the wagon with the marshal
            if (walkedIntoMarshal) {
                Character character = completeTrain.getCharacterInTrain(((DTOShoot) moveToApply).getVictim().getId());
                completeTrain.removeCharacterInTrain(((DTOShoot) moveToApply).getVictim().getId());
                trainRepo.save(completeTrain);
                completeTrain.moveUser(character, victimPosition, goesLeft);
                trainRepo.save(completeTrain);
                if (cardStack.getAmmoCard() != null) {
                    marshalAmmoCard = cardStack.getAmmoCard();
                    cardStack.removeLastAmmoCard();
                    allCards.save(cardStack);
                    ammoCardRepo.save(marshalAmmoCard);
                }
            } else {
                Character character = completeTrain.getCharacterInTrain(victim);
                completeTrain.removeCharacterInTrain(((DTOShoot) moveToApply).getVictim().getId());
                trainRepo.save(completeTrain);
                movingCharacter.getSpecialAbility().doAbility(character, movingUserPosition, moveToApply, completeTrain);
                trainRepo.save(completeTrain);
            }
        }
        AmmoCard ammoCard = new AmmoCard();
        for (AmmoCard amc: ammoCardsOfMovingUser)
        {
            int counter = 0;
            if (amc.getVictim() == 0) {

                for (int i = 0; i < ammoCardsOfMovingUser.size();i++) {
                	
                	boolean isBulletRound = ammoCardsOfMovingUser.get(i).getBulletRound() > ammoCard.getBulletRound();
                	boolean ammoCardActionIsDefined = ammoCardsOfMovingUser.get(i).getVictim() == 0;
                	
                    if (isBulletRound && ammoCardActionIsDefined) {
                        break;
                    } else {
                        counter++;
                    }
                }
                if (counter == 6) {
                    break;
                }
            }
        }
        ammoCard.setVictim(((DTOShoot) moveToApply).getVictim().getId());
        ammoCardRepo.save(ammoCard);
        for (User us:players) {
            if (movingUser != us.getId()) {
                if (( (DTOShoot) moveToApply).getVictim().getId() != us.getId()) {
                    Move newMove = new DTOShoot();
                    newMove.setUserId(us.getId());
                    newMove.setGameId(gameId);
                    ((DTOShoot)newMove).setVictim(((DTOShoot) moveToApply).getVictim());
                    ((DTOShoot)newMove).setVictim(userRepo.findOne(( (DTOShoot) moveToApply).getVictim().getId()));
                    allResultingMoves.add(newMove);
                } else {
                    Move newMove = new DTOShoot();
                    newMove.setUserId(us.getId());
                    newMove.setGameId(gameId);
                    ((DTOShoot)newMove).setVictim(((DTOShoot) moveToApply).getVictim());
                    if (ammoCard.getId() != null) {
                        ((DTOShoot) newMove).setVictimAmmoCard(ammoCard);
                    }
                    if (walkedIntoMarshal) {
                        ((DTOShoot)moveToApply).setMarshalAmmoCard(marshalAmmoCard);
                    }
                    allResultingMoves.add(newMove);
                }
            }
        }
        allResultingMoves.add(moveToApply);
        return allResultingMoves;
	}
}
