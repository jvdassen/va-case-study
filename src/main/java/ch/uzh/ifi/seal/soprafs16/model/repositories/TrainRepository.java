package ch.uzh.ifi.seal.soprafs16.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.seal.soprafs16.model.environment.Train;

import java.util.List;

@Repository("trainRepository")
public interface TrainRepository extends CrudRepository<Train, Long> {
    List<Train> findByGameId(long gameId);
}
