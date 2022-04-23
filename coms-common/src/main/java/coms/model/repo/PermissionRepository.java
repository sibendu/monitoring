package coms.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import coms.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{
	public List<Permission> findByName(String name);
}