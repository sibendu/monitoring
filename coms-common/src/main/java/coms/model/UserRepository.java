package coms.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query(value = "SELECT u FROM User u")
	List<User> getallusers();
	@Query("FROM User WHERE login_id = :loginid")
	List<User> getUserByLoginid(@Param("loginid")String loginid);
	
}