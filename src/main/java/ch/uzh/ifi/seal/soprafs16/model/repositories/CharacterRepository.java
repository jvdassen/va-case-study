package ch.uzh.ifi.seal.soprafs16.model.repositories;

/**
 * Created by Laurenz on 13/04/16.
 */

import ch.uzh.ifi.seal.soprafs16.model.Character;
import ch.uzh.ifi.seal.soprafs16.model.ECharacter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("characterRepository")
public interface CharacterRepository extends CrudRepository<Character, Long> {
    Character findFirstByUserId(long userId);
    Character findByCharacterAndGameId(ECharacter character, long gameId);
}
