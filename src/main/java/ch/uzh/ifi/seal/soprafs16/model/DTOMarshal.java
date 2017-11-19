package ch.uzh.ifi.seal.soprafs16.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by Laurenz on 28/04/16.
 */

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
