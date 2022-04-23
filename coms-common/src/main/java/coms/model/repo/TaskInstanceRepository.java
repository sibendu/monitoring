package coms.model.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import coms.model.TaskInstance;

public interface TaskInstanceRepository extends CrudRepository<TaskInstance, Long> {

	TaskInstance findById(long id);
	
	List<TaskInstance> findByAssignedUser(String user);

	List<TaskInstance> findByAssignedGroup(String group);

	List<TaskInstance> findByAssignedUserAndStatus(String user, String status);

}
