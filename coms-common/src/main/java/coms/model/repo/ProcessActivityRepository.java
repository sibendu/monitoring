package coms.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import coms.model.ProcessActivity;

public interface ProcessActivityRepository extends CrudRepository<ProcessActivity, Long> {

	ProcessActivity findById(long id);
	
	@Query(value = "SELECT * FROM process_activity pa where pa.instance_id = :instance_id", nativeQuery=true)
	public List<ProcessActivity> findByProcessId(@Param("instance_id") Long id);
	
	@Query(value = "SELECT * FROM process_activity pa where pa.instance_id = :instance_id and event = :event", nativeQuery=true)
	public List<ProcessActivity> findByActivityByEvent(@Param("instance_id") Long id, @Param("event") String event);
	
//	@Query(value = "select * from process_actvity where EMAIL_ADDRESS = ?1", nativeQuery = true)
//	List<ProcessActivity> findByEvents(String events);
}
