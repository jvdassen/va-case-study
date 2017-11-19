package ch.uzh.ifi.seal.soprafs16.model.repositories;

import ch.uzh.ifi.seal.soprafs16.model.ActionCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("actionCardRepository")
public interface ActionCardRepository extends CrudRepository<ActionCard, Long> {
    List<ActionCard> findByGameId(Long gameId);
    List<ActionCard> findByCardNumAndUserId(int cardNum, long userId);
    List<ActionCard> findByCardNum(int cardNum);
}
