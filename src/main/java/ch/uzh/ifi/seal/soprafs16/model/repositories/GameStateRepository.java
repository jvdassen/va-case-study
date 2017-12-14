package ch.uzh.ifi.seal.soprafs16.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.seal.soprafs16.model.game.GameState;

@Repository("gameStateRepository")
public interface GameStateRepository extends CrudRepository<GameState, Long> {
    GameState findByGameId(long gameId);
}