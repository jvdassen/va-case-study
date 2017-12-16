package ch.uzh.ifi.seal.soprafs16.model.util;

import java.util.List;

import ch.uzh.ifi.seal.soprafs16.model.dto.Move;

public interface Visitable {
	public List<Move> accept(Visitor visitor);
}
