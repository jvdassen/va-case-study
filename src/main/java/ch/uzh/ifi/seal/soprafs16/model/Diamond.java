package ch.uzh.ifi.seal.soprafs16.model;

import javax.persistence.Entity;

import ch.uzh.ifi.seal.soprafs16.constant.LootType;

@Entity
public class Diamond extends Loot{

	private static final long serialVersionUID = 1L;

	public Diamond() {
        super.value = 500;
    }

    @Override
    public void setLootType(LootType lootType)
    {
        super.setLootType(lootType);
    }
}
