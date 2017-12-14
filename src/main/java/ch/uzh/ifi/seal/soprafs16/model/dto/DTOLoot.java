package ch.uzh.ifi.seal.soprafs16.model.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ch.uzh.ifi.seal.soprafs16.constant.LootType;


@Entity
public class DTOLoot implements Serializable
{
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int value;

    @Enumerated(EnumType.STRING)
    private LootType lootType;

    public LootType getLootType()
    {
        return lootType;
    }

    public void setLootType(LootType lootType)
    {
        this.lootType = lootType;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }
}
