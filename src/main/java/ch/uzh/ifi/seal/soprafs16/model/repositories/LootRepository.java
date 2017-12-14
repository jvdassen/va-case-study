package ch.uzh.ifi.seal.soprafs16.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.seal.soprafs16.model.lootobject.Loot;

import java.util.List;

@Repository("lootRepository")
public interface LootRepository extends CrudRepository<Loot, Long> {
    List<Loot> findByGameId(long gameId);
    List<Loot> findByUserId(long userId);
}
