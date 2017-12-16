package ch.uzh.ifi.seal.soprafs16.model.game;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ch.uzh.ifi.seal.soprafs16.constant.ECharacter;
import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.dto.Move;
import ch.uzh.ifi.seal.soprafs16.model.environment.Train;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.ActionCard;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.Loot;

@Entity
public class SpecialAbility implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ECharacter character;

    @Column(nullable = false)
    private long gameId;

    public Long getId() {
        return id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public ECharacter getCharacter() {
        return character;
    }

    public void setCharacter(ECharacter character) {
        this.character = character;
    }

    /* GHOST */
    public void doAbility(ActionCard actionCard) {
            actionCard.setVisible(false);
    }

    /*DJANGO */
    public void doAbility(Character character, int userPosition, Move move, Train train) {
    	int somePosition = train.findUserInTrain(move.getUserId());
    	
        if (somePosition < userPosition) {
            train.getWagons().get((userPosition+2)/2).getTrainLevels().get(userPosition%2).addCharacter(character);
        }
        else
        {
            train.getWagons().get((userPosition-2)/2).getTrainLevels().get(userPosition%2).addCharacter(character);
        }
    }

    /* CHEYENNE */
    public void doAbility(Move move, Loot loot) {
        loot.setUserId(move.getUserId());
    }
}
