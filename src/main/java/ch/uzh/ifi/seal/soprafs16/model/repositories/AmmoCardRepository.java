package ch.uzh.ifi.seal.soprafs16.model.repositories;

/**
 * Created by Laurenz on 07/04/16.
 */

import ch.uzh.ifi.seal.soprafs16.model.AmmoCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ammoCardRepository")
public interface AmmoCardRepository extends CrudRepository<AmmoCard, Long> {
    List<AmmoCard> findByGameId(Long gameId);
    List<AmmoCard> findByUserId(long userId);
}
