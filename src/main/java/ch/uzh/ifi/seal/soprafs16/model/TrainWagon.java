package ch.uzh.ifi.seal.soprafs16.model;

/**
 * @author Laurenz Shi
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TrainWagon implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private boolean locomotive = false;

    @Column(nullable = false)
    private long gameId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TrainLevel> trainLevels = new ArrayList<TrainLevel>();

    public Long getId() {
        return id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public boolean isLocomotive() {
        return locomotive;
    }

    public void setLocomotive(boolean locomotive) {
        this.locomotive = locomotive;
    }

    public List<TrainLevel> getTrainLevels() {
        return trainLevels;
    }

    public void setTrainLevels(List<TrainLevel> trainLevels) {
        this.trainLevels = trainLevels;
    }
}
