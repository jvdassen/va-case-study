package ch.uzh.ifi.seal.soprafs16.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class DTOPunch extends Move{

    @OneToOne
    private User victim;

    //This name has to stay like this, for some reason several other names didn't work
    @OneToOne
    private DTOLoot dtoloot;

    @OneToOne
    private Loot stolenLoot;

    @OneToOne
    private Loot cheyenneLoot;

    @Column
    private boolean left;

    @Column
    private boolean marshal;

    @OneToOne
    private AmmoCard shotAmmoCard;

    public boolean isLeft()
    {
        return left;
    }

    public boolean isMarshal()
    {
        return marshal;
    }


    public AmmoCard getShotAmmoCard()
    {
        return shotAmmoCard;
    }

    public void setShotAmmoCard(AmmoCard shotAmmoCard)
    {
        this.shotAmmoCard = shotAmmoCard;
    }

    public User getVictim() {
        return victim;
    }

    public void setVictim(User victim)
    {
        this.victim = victim;
    }

    public DTOLoot getDTOLoot()
    {
        return dtoloot;
    }

    public void setDTOLoot(DTOLoot dtoloot)
    {
        this.dtoloot = dtoloot;
    }

    public Loot getCheyenneLoot()
    {
        return cheyenneLoot;
    }

    public void setCheyenneLoot(Loot cheyenneLoot)
    {
        this.cheyenneLoot = cheyenneLoot;
    }

    public Loot getStolenLoot()
    {
        return stolenLoot;
    }

    public void setStolenLoot(Loot stolenLoot)
    {
        this.stolenLoot = stolenLoot;
    }
}
