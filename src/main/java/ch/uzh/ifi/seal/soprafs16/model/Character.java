package ch.uzh.ifi.seal.soprafs16.model;

import java.io.Serializable;

/**
 * Created by Laurenz on 13/04/16.
 */
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Character implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private long gameId;

    @Column
    private long userId;

    @Enumerated(EnumType.STRING)
    private ECharacter character;

    @OneToOne(cascade = {CascadeType.ALL})
    private SpecialAbility specialAbility;

    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public SpecialAbility getSpecialAbility() {
        return specialAbility;
    }

    public void setSpecialAbility(SpecialAbility specialAbility) {
        this.specialAbility = specialAbility;
    }
}
