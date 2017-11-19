package ch.uzh.ifi.seal.soprafs16.model;

/**
 * @author Laurenz Shi
 * Class to inform client about the standings right now. With the individual information of the own standings.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import ch.uzh.ifi.seal.soprafs16.constant.GameStatus;

@Entity
public class GameState implements  Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private long gameId;

    @OneToOne(cascade = CascadeType.ALL)
    private Round round;

    //@Column(nullable = false)
    //private RoundCard rc;

    @OneToOne(cascade = CascadeType.ALL)
    private Phase phase;

    @OneToOne
    private User playerTurn;

    @OneToOne(cascade = CascadeType.ALL)
    private PlayerSequence playerSequence;

    //Add a boolean to notify the clients that the same player has two turns
    @Column
    private boolean secondMove;

    public boolean isSecondMove()
    {
        return secondMove;
    }

    public void setSecondMove(boolean secondMove)
    {
        this.secondMove = secondMove;
    }

    public Long getId() {
        return id;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public PlayerSequence getPlayerSequence() {
        return playerSequence;
    }

    public void setPlayerSequence(PlayerSequence playerSequence) {
        this.playerSequence = playerSequence;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public User getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(User playerTurn) {
        this.playerTurn = playerTurn;
    }
}
