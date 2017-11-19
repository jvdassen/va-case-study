package ch.uzh.ifi.seal.soprafs16.model.repositories;

import ch.uzh.ifi.seal.soprafs16.model.ShortGame;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("shortGameRepository")
public interface ShortGameRepository extends CrudRepository<ShortGame, Long>
{
    ShortGame findFirstByGameId(long gameId);
}
