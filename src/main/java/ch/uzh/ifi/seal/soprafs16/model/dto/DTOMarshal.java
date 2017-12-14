package ch.uzh.ifi.seal.soprafs16.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ch.uzh.ifi.seal.soprafs16.model.game.Move;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.AmmoCard;


@Entity
public class DTOMarshal extends Move{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
    private boolean left;

    @OneToOne
    private AmmoCard shotAmmoCard;

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public AmmoCard getShotAmmoCard()
    {
        return shotAmmoCard;
    }

    public void setShotAmmoCard(AmmoCard shotAmmoCard)
    {
        this.shotAmmoCard = shotAmmoCard;
    }
}
