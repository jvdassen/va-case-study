package ch.uzh.ifi.seal.soprafs16.model.dto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ch.uzh.ifi.seal.soprafs16.model.User;
import ch.uzh.ifi.seal.soprafs16.model.gamecard.AmmoCard;
import ch.uzh.ifi.seal.soprafs16.model.util.Visitor;

@Entity
public class DTOShoot extends Move implements VisitableMove{

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

	public List<Move> accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
