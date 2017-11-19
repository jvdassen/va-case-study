package ch.uzh.ifi.seal.soprafs16.model;

/**
 * @author Laurenz Shi
 */
import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;

import ch.uzh.ifi.seal.soprafs16.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs16.constant.LootType;

@Entity
public class MoneyBag extends Loot{

    public void setValue(int value) {
        if (value >= 250 && value <= 500) {
            this.value = value;
        }
        else
        {
        	
        }
    }

    @Override
    public void setLootType(LootType lootType)
    {
        super.setLootType(lootType);
    }
}
