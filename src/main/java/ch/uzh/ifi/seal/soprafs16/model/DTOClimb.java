package ch.uzh.ifi.seal.soprafs16.model;

import javax.persistence.Column;
import javax.persistence.Entity;

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