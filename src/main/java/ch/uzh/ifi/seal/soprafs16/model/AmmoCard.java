package ch.uzh.ifi.seal.soprafs16.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class AmmoCard extends Card {

    @Column
    private long userId;

    @Column
    private boolean neutral = false;

    @Column
    private Long owner;

    @Column
    private Long victim;

    @Column
    private int bulletRound;

    @Enumerated(EnumType.STRING)
    private ECharacter character;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isNeutral() {
        return neutral;
    }

    public void setNeutral(boolean neutral) {
        this.neutral = neutral;
    }

    public Long getVictim() {
        return victim;
    }

    public void setVictim(Long victim) {
        this.victim = victim;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public int getBulletRound() {
        return bulletRound;
    }

    public void setBulletRound(int bulletRound) {
        this.bulletRound = bulletRound;
    }

    public ECharacter getCharacter()
    {
        return character;
    }

    public void setCharacter(ECharacter character)
    {
        this.character = character;
    }
}
