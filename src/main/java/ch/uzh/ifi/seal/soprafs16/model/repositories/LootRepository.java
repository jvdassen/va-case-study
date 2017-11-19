package ch.uzh.ifi.seal.soprafs16.model.repositories;

import ch.uzh.ifi.seal.soprafs16.model.Loot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("lootRepository")
public interface LootRepository extends CrudRepository<Loot, Long> {
    List<Loot> findByGameId(long gameId);
    List<Loot> findByUserId(long userId);
}
