package ch.uzh.ifi.seal.soprafs16.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

import ch.uzh.ifi.seal.soprafs16.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs16.constant.LootType;

@Entity
public class Diamond extends Loot{

    public Diamond() {
        super.value = 500;
    }

    @Override
    public void setLootType(LootType lootType)
    {
        super.setLootType(lootType);
    }
}
