package ch.uzh.ifi.seal.soprafs16.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.seal.soprafs16.model.gamecard.CardStack;

import java.util.List;

@Repository("cardStackRepository")
public interface CardStackRepository extends CrudRepository<CardStack, Long> {
    List<CardStack> findByGameId(long gameId);
}
