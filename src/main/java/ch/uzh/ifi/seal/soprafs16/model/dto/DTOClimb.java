package ch.uzh.ifi.seal.soprafs16.model.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import ch.uzh.ifi.seal.soprafs16.model.util.Visitor;

@Entity
public class DTOClimb extends Move implements VisitableMove {

    @Column
    private boolean base;

    public boolean isBase() {
        return base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

	public List<Move> accept(Visitor visitor) {
		return visitor.visit(this);
	}

}