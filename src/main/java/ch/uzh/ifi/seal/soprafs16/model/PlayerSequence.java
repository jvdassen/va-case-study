package ch.uzh.ifi.seal.soprafs16.model;

/**
 * Created by Laurenz on 16/04/16.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

@Entity
public class PlayerSequence implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private long gameId;

    @OneToMany
    private List<User> sequence = new ArrayList<User>();

    @ElementCollection
    private List<Boolean> hasPlayed = new ArrayList<Boolean>();

    @Column
    private boolean inversed;

    @Column
    private boolean doubleRound;

    @Column
    private boolean firstPlayed;

    public Long getId() {
        return Id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public List<User> getSequence() {
        return sequence;
    }

    public void setSequence(List<User> sequence) {
        this.sequence = sequence;
    }

    public List<Boolean> getHasPlayed() {
        return hasPlayed;
    }

    public void setHasPlayed(List<Boolean> hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    public boolean isInversed() {
        return inversed;
    }

    public void setInversed(boolean inversed) {
        this.inversed = inversed;
    }

    public boolean isDoubleRound() {
        return doubleRound;
    }

    public void setDoubleRound(boolean doubleRound) {
        this.doubleRound = doubleRound;
    }

    public boolean isFirstPlayed() {
        return firstPlayed;
    }

    public void setFirstPlayed(boolean firstPlayed) {
        this.firstPlayed = firstPlayed;
    }

    public void resetHasPlayed()
    {
        for (int i=0;i<hasPlayed.size();i++)
        {
            hasPlayed.set(i, false);
        }
    }

    public void setCurrentHasPlayed()
    {
        for(int i=0; i<hasPlayed.size();i++)
        {
            if (!inversed && !doubleRound) {
                if (!hasPlayed.get(i)) {
                    hasPlayed.set(i, true);
                    break;
                }
            }
            else if (inversed && !doubleRound)
            {
                if (hasPlayed.get(i) || i==hasPlayed.size()-1)
                {
                    if (i==hasPlayed.size()-1 && !hasPlayed.get(i))
                    {
                        hasPlayed.set(i,true);
                        break;
                    }
                    hasPlayed.set(i-1, true);
                    break;
                }
            }
            else
            {
                if (!hasPlayed.get(i) && !firstPlayed)
                {
                    firstPlayed = true;
                    break;
                }
                else if (!hasPlayed.get(i) && firstPlayed)
                {
                    hasPlayed.set(i, true);
                    firstPlayed = false;
                    break;
                }
            }
        }
    }

    public boolean checkIfAllPlayed()
    {
        for (boolean i : hasPlayed)
        {
            if (!i)
            {
                return false;
            }
        }
        return true;
    }

    public User getCurrentPlayer()
    {
        for (int i=0;i<hasPlayed.size();i++)
        {
            if (!inversed) {
                if (!hasPlayed.get(i)) {
                    return sequence.get(i);
                }
            }
            else
            {
                if(hasPlayed.get(i) || i==hasPlayed.size()-1)
                {
                    if (i==hasPlayed.size()-1 && !hasPlayed.get(i))
                    {
                        return sequence.get(i);
                    }
                    if (i==0)
                    {
                        return sequence.get(0);
                    }
                    return sequence.get(i-1);
                }
            }
        }
        return null;
    }

    public void resetBools()
    {
        firstPlayed = false;
        inversed = false;
        doubleRound = false;
    }
}
