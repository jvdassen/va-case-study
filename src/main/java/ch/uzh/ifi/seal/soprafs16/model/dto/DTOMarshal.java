package ch.uzh.ifi.seal.soprafs16.model.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ch.uzh.ifi.seal.soprafs16.model.gamecard.AmmoCard;
import ch.uzh.ifi.seal.soprafs16.model.util.Visitor;


@Entity
public class DTOMarshal extends Move implements VisitableMove{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
    private boolean isLeft;

    @OneToOne
    private AmmoCard shotAmmoCard;

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        this.isLeft = left;
    }

    public AmmoCard getShotAmmoCard()
    {
        return shotAmmoCard;
    }

    public void setShotAmmoCard(AmmoCard shotAmmoCard)
    {
        this.shotAmmoCard = shotAmmoCard;
    }

	public List<Move> accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
