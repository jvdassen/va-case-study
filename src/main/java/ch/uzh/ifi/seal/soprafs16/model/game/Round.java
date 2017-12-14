package ch.uzh.ifi.seal.soprafs16.model.game;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ch.uzh.ifi.seal.soprafs16.constant.ERound;

@Entity
public class Round implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private long gameId;

    @Column
    private int maxRounds = 5;

    @Enumerated(EnumType.STRING)
    private ERound currentRound;

    @Column
    private int currentTurn;


    public Long getId() {
        return id;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public ERound getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(ERound currentRound) {
        this.currentRound = currentRound;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void updateRound()
    {
        switch (currentRound)
        {
            case ONE:
            {
                currentRound = ERound.TWO;
            }
            break;
            case TWO:
            {
                currentRound=ERound.THREE;
            }
            break;
            case THREE:
            {
                currentRound = ERound.FOUR;
            }
            break;
            case FOUR:
            {
                currentRound = ERound.FIVE;
            }
            break;
        }
    }
}
