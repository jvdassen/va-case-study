package ch.uzh.ifi.seal.soprafs16.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;

import ch.uzh.ifi.seal.soprafs16.model.game.Move;

@Entity
public class DTOMove extends Move{
    @Column
    private boolean left;
    @Column
    private int distance;

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
