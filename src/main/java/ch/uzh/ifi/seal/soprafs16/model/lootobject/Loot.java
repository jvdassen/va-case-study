package ch.uzh.ifi.seal.soprafs16.model.lootobject;

import java.io.Serializable;

/**
 * Created by Laurenz on 07/04/16.
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

import ch.uzh.ifi.seal.soprafs16.constant.LootType;

@Entity
@Inheritance
public abstract class Loot implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    int value;

    @Column(nullable = false)
    long gameId;

    @Column
    long trainId;

    @Column
    long userId;
    
    @Column
    LootType loottype;

    public Long getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setGameId(long gameId){
        this.gameId = gameId;
    }

    public long getGameId() {
        return gameId;
    }

    public long getTrainId() {
        return trainId;
    }

    public void setTrainId(long trainId) {
        this.trainId = trainId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public void setLootType(LootType lootType){
    	this.loottype = lootType;
    }
    public LootType getLootType(){
    	return this.loottype;
    }
}
