package ch.uzh.ifi.seal.soprafs16.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

import ch.uzh.ifi.seal.soprafs16.model.util.Visitable;
import ch.uzh.ifi.seal.soprafs16.model.util.Visitor;

@Entity
@Inheritance
public class Move implements Serializable, Visitable {
	
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

	@Override
	public List<Move> accept(Visitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}

}
