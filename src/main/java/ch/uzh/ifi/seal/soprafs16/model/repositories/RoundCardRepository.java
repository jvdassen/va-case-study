package ch.uzh.ifi.seal.soprafs16.model.repositories;

import ch.uzh.ifi.seal.soprafs16.model.RoundCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("roundCardRepository")
public interface RoundCardRepository extends CrudRepository<RoundCard, Long> {
    List<RoundCard> findByGameId(Long gameId);
}
