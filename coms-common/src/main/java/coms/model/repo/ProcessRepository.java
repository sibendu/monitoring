package coms.model.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import coms.model.ProcessDefinition;

public interface ProcessRepository extends CrudRepository<ProcessDefinition, Long> {
	
	public List<ProcessDefinition> findByCodeAndVersion(String code, String version);
}
