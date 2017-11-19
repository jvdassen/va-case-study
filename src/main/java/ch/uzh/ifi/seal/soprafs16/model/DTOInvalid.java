package ch.uzh.ifi.seal.soprafs16.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by Laurenz on 28/04/16.
 */

@Entity
public class DTOInvalid extends Move{

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
