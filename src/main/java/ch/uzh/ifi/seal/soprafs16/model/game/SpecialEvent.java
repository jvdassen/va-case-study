package ch.uzh.ifi.seal.soprafs16.model.game;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ch.uzh.ifi.seal.soprafs16.constant.ESpecialEvent;


@Entity
public class SpecialEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private long gameId;

    @Enumerated(EnumType.STRING)
    private ESpecialEvent specialEvent;

    // public void startSpecialEvent()
    // {}

    public Long getId() {
        return Id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public ESpecialEvent getSpecialEvent() {
        return specialEvent;
    }

    public void setSpecialEvent(ESpecialEvent specialEvent) {
        this.specialEvent = specialEvent;
    }
}
