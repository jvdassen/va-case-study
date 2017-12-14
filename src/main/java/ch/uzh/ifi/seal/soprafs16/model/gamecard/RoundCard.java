package ch.uzh.ifi.seal.soprafs16.model.gamecard;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ch.uzh.ifi.seal.soprafs16.constant.ETurn;
import ch.uzh.ifi.seal.soprafs16.model.game.SpecialEvent;

@Entity
public class RoundCard extends Card {

    @Column
    private boolean trainStation;

    /* This describes how many turns one round has.*/
    @Column
    private int maxTurns;

    @Column
    private int currentTurn;

    @Column
    private int cardNum;

    /* This describes which turn, index starts with 1, has which special rule. */
    @ElementCollection
    private Map<Integer, ETurn> turns = new HashMap<Integer, ETurn>();

    @OneToOne(cascade = CascadeType.ALL)
    private SpecialEvent specialEvent;

    public boolean isTrainStation() {
        return trainStation;
    }

    public void setTrainStation(boolean trainStation) {
        this.trainStation = trainStation;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Map<Integer, ETurn> getTurns() {
        return turns;
    }

    public void setTurns(Map<Integer, ETurn> turns) {
        this.turns = turns;
    }

    public SpecialEvent getSpecialEvent() {
        return specialEvent;
    }

    public void setSpecialEvent(SpecialEvent specialEvent) {
        this.specialEvent = specialEvent;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }
}
