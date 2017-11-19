package ch.uzh.ifi.seal.soprafs16.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by Laurenz on 28/04/16.
 */

@Entity
public class DTORob extends Move{

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
}
