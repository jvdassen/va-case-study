package ch.uzh.ifi.seal.soprafs16.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.seal.soprafs16.model.dto.DTOSpecialEvent;

@Repository("sERepository")
public interface SERepository extends CrudRepository<DTOSpecialEvent, Long>
{
    DTOSpecialEvent findFirstByUserId(long userId);
}
