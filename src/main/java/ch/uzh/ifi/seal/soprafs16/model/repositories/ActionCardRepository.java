package ch.uzh.ifi.seal.soprafs16.model.repositories;

/**
 * Created by Laurenz on 24/04/16.
 */
import ch.uzh.ifi.seal.soprafs16.model.ActionCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("actionCardRepository")
public interface ActionCardRepository extends CrudRepository<ActionCard, Long> {
    List<ActionCard> findByGameId(long gameId);
    List<ActionCard> findByCardNumAndUserId(int cardNum, long userId);
    List<ActionCard> findByCardNum(int cardNum);
}
