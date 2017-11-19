package ch.uzh.ifi.seal.soprafs16.model;

/**
 * Created by Laurenz on 16/04/16.
 */

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Phase implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private long gameId;

    @Enumerated(EnumType.STRING)
    private EPhase phase;

    public Long getId() {
        return Id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public EPhase getPhase() {
        return phase;
    }

    public void setPhase(EPhase phase) {
        this.phase = phase;
    }

    public void updatePhase()
    {
        switch (phase)
        {
            case PLANNING:
                {
                    phase = EPhase.ACTION;
                    break;
                }
            case ACTION:
                {
                    phase = EPhase.SPECIAL_EVENT;
                    break;
                }
            case SPECIAL_EVENT:
                {
                    phase = EPhase.PLANNING;
                    break;
                }
        }
    }
}
