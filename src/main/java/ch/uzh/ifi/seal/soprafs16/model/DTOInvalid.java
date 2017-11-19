package ch.uzh.ifi.seal.soprafs16.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class DTOInvalid extends Move{


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
}
