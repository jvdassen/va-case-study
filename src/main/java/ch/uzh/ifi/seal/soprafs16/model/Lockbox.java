package ch.uzh.ifi.seal.soprafs16.model;

/*
 * @author Laurenz Shi
 */

import javax.persistence.*;

import java.io.Serializable;


import javax.persistence.Entity;

import ch.uzh.ifi.seal.soprafs16.constant.LootType;

@Entity
public class Lockbox extends Loot{


    public Lockbox() {
        super.value = 1000;
    }

    public void setGameId(long gameId) {
        super.gameId = gameId;
    }

    @Override
    public void setLootType(LootType lootType)
    {
        super.setLootType(lootType);
    }


}
