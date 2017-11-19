package ch.uzh.ifi.seal.soprafs16.model.repositories;

import ch.uzh.ifi.seal.soprafs16.model.DTOSpecialEvent;
import ch.uzh.ifi.seal.soprafs16.model.ShortGame;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurenz on 16/05/16.
 */
@Repository("shortGameRepository")
public interface ShortGameRepository extends CrudRepository<ShortGame, Long>
{
    ShortGame findFirstByGameId(long gameId);
}
