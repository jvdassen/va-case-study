package ch.uzh.ifi.seal.soprafs16.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.seal.soprafs16.model.game.ShortGame;

@Repository("shortGameRepository")
public interface ShortGameRepository extends CrudRepository<ShortGame, Long>
{
    ShortGame findFirstByGameId(long gameId);
}
