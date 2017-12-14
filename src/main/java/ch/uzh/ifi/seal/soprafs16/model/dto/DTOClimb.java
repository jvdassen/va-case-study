package ch.uzh.ifi.seal.soprafs16.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;

import ch.uzh.ifi.seal.soprafs16.model.game.Move;

@Entity
public class DTOClimb extends Move{

    @Column
    private boolean base;

    public boolean isBase() {
        return base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }
}