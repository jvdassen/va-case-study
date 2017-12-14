package ch.uzh.ifi.seal.soprafs16.model.dto;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ch.uzh.ifi.seal.soprafs16.model.game.Move;


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
