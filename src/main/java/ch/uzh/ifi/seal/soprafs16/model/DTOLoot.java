package ch.uzh.ifi.seal.soprafs16.model;

import ch.uzh.ifi.seal.soprafs16.constant.LootType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Laurenz on 13/05/16.
 */

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
