package ch.uzh.ifi.seal.soprafs16.model.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ch.uzh.ifi.seal.soprafs16.model.gamecard.AmmoCard;
import ch.uzh.ifi.seal.soprafs16.model.util.Visitor;

@Entity
public class DTOInvalid extends Move implements VisitableMove{


	private static final long serialVersionUID = 1L;

	@Column
    private boolean walkedIntoMarshal;

    @OneToOne
    private AmmoCard ammoCard;

    public boolean isWalkedIntoMarshal() {
        return walkedIntoMarshal;
    }

    public AmmoCard getAmmoCard()
    {
        return ammoCard;
    }

    public void setAmmoCard(AmmoCard ammoCard)
    {
        this.ammoCard = ammoCard;
    }

	public List<Move> accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
