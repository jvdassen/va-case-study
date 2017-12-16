package ch.uzh.ifi.seal.soprafs16.model.dto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ch.uzh.ifi.seal.soprafs16.model.util.Visitor;


@Entity
public class DTORob extends Move implements VisitableMove {

    //Name should stay like this, several other words didn't work
    @OneToOne
    private DTOLoot dtoloot;

    public DTOLoot getDTOLoot() {
        return dtoloot;
    }

    public void setDTOLoot(DTOLoot dtoloot)
    {
        this.dtoloot = dtoloot;
    }

	public List<Move> accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
