package ch.uzh.ifi.seal.soprafs16.model.repositories;

/**
 * Created by Laurenz on 14/04/16.
 */

import ch.uzh.ifi.seal.soprafs16.model.Train;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("trainRepository")
public interface TrainRepository extends CrudRepository<Train, Long> {
    List<Train> findByGameId(long gameId);
}
