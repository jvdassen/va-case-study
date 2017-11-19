package ch.uzh.ifi.seal.soprafs16.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Laurenz on 16/05/16.
 */
@Entity
public class ShortGame implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private long gameId;

    @Column
    private boolean shortV = false;


    public long getGameId()
    {
        return gameId;
    }

    public void setGameId(long gameId)
    {
        this.gameId = gameId;
    }

    public boolean isShortV()
    {
        return shortV;
    }

    public void setShortV(boolean shortV)
    {
        this.shortV = shortV;
    }
}
