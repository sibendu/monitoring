package coms.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends CrudRepository<Event, Long> {
	
	Event findById(long id);
	
	@Query(value = "SELECT * FROM event e where e.code = :code and e.process_id = :processId", nativeQuery=true)
	public List<Event> find(@Param("code") String code,@Param("processId") Long processId);
}
