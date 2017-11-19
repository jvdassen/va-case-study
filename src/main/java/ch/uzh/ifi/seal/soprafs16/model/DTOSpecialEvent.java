package ch.uzh.ifi.seal.soprafs16.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by Laurenz on 16/05/16.
 */

@Entity
public class DTOSpecialEvent implements Serializable
{
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private AmmoCard shotAmmoCard;

    @OneToOne
    private Loot takenLoot;

    @OneToOne
    private Loot lostLoot;

    @Column
    private long userId;

    @Column
    private int money;

    public AmmoCard getShotAmmoCard()
    {
        return shotAmmoCard;
    }

    public void setShotAmmoCard(AmmoCard shotAmmoCard)
    {
        this.shotAmmoCard = shotAmmoCard;
    }

    public Loot getTakenLoot()
    {
        return takenLoot;
    }

    public void setTakenLoot(Loot takenLoot)
    {
        this.takenLoot = takenLoot;
    }

    public Loot getLostLoot()
    {
        return lostLoot;
    }

    public void setLostLoot(Loot lostLoot)
    {
        this.lostLoot = lostLoot;
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int money)
    {
        this.money = money;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }
}
