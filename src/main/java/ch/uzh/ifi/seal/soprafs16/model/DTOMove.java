package ch.uzh.ifi.seal.soprafs16.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Laurenz on 28/04/16.
 */

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
