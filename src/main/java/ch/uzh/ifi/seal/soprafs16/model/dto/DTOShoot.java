package ch.uzh.ifi.seal.soprafs16.model.dto;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ch.uzh.ifi.seal.soprafs16.model.User;
import ch.uzh.ifi.seal.soprafs16.model.game.Move;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.AmmoCard;

@Entity
public class DTOShoot extends Move{

    @OneToOne
    private User victim;

    @OneToOne
    private AmmoCard victimAmmoCard;

    @OneToOne
    private AmmoCard marshalAmmoCard;

    public void setVictim(User victim)
    {
        this.victim = victim;
    }

    public AmmoCard getVictimAmmoCard()
    {
        return victimAmmoCard;
    }

    public void setVictimAmmoCard(AmmoCard ammoCard)
    {
        this.victimAmmoCard = ammoCard;
    }

    public User getVictim() {
        return victim;
    }

    public AmmoCard getMarshalAmmoCard()
    {
        return marshalAmmoCard;
    }

    public void setMarshalAmmoCard(AmmoCard marshalAmmoCard)
    {
        this.marshalAmmoCard = marshalAmmoCard;
    }
}
