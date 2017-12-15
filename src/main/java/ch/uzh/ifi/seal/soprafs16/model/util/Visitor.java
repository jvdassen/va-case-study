package ch.uzh.ifi.seal.soprafs16.model.util;

import java.util.List;

import ch.uzh.ifi.seal.soprafs16.model.dto.DTOClimb;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOInvalid;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOMarshal;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOMove;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOPunch;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTORob;
import ch.uzh.ifi.seal.soprafs16.model.dto.DTOShoot;
import ch.uzh.ifi.seal.soprafs16.model.game.Move;;

public interface Visitor {
	public List<Move> visit(DTOClimb move);
	public List<Move> visit(DTOInvalid move);
	public List<Move> visit(DTOMarshal move);
	public List<Move> visit(DTOMove move);
	public List<Move> visit(DTOPunch move);
	public List<Move> visit(DTORob move);
	public List<Move> visit(DTOShoot move);
}
