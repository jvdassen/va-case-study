package ch.uzh.ifi.seal.soprafs16.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Inheritance
public class Move implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
    @Column
	private long gameId;

	@Column
	private long userId;

	public Long getId() {
		return id;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}
}
