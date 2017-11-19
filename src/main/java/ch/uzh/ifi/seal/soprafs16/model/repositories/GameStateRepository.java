package ch.uzh.ifi.seal.soprafs16.model.repositories;

import ch.uzh.ifi.seal.soprafs16.model.GameState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("gameStateRepository")
public interface GameStateRepository extends CrudRepository<GameState, Long> {
    GameState findByGameId(long gameId);
}