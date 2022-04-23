package coms.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import coms.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	public List<Role> findByName(String name);
}