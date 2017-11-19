package ch.uzh.ifi.seal.soprafs16.model;

/**
 * Created by Laurenz on 13/04/16.
 */

import ch.uzh.ifi.seal.soprafs16.model.repositories.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

import java.io.Serializable;

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
        if (train.findUserInTrain(move.getUserId())<userPosition) {
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
