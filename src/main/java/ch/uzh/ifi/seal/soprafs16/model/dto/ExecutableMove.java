package ch.uzh.ifi.seal.soprafs16.model.dto;

import java.util.List;

import ch.uzh.ifi.seal.soprafs16.model.util.Visitor;

public interface MoveInterface {
	public List<Move> accept(Visitor visitor);
}
