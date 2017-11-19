package ch.uzh.ifi.seal.soprafs16.model.repositories;

import ch.uzh.ifi.seal.soprafs16.model.DTOSpecialEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurenz on 16/05/16.
 */
@Repository("sERepository")
public interface SERepository extends CrudRepository<DTOSpecialEvent, Long>
{
    DTOSpecialEvent findFirstByUserId(long userId);
}
