package ch.uzh.ifi.seal.soprafs16.model.repositories;

/**
 * Created by Laurenz on 24/04/16.
 */
import ch.uzh.ifi.seal.soprafs16.model.CardStack;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("cardStackRepository")
public interface CardStackRepository extends CrudRepository<CardStack, Long> {
    List<CardStack> findByGameId(long gameId);
}
