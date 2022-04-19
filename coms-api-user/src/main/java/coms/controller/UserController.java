package coms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coms.model.Group;
import coms.model.GroupRepository;
import coms.model.Role;
import coms.model.RoleRepository;
import coms.model.User;
import coms.model.UserRepository;
import coms.model.dto.UserDto;

@RestController
public class UserController {
	
	@Autowired
	public UserRepository userRepo;
	
	@Autowired
	public GroupRepository groupRepo;
	
	@Autowired
	public RoleRepository roleRepo;
	
	@GetMapping("/user")
	public List<User> getAllUsers (){
		return userRepo.findAll();
	}
	
	@GetMapping("/user/{username}")
	public User getUser(@PathVariable String username) {
		List<User> users = userRepo.findByUsername(username);
		if(users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}
	
	@PostMapping("/user")
    public User saveUser(@RequestBody User user) {
		return userRepo.save(user);
    }
	
	@GetMapping("/group")
	public List<Group> getAllGroups() {
		return groupRepo.findAll();
	}
	@GetMapping("/group/{name}")
	public Group getGroup(@PathVariable String name) {
		List<Group> groups = groupRepo.findByName(name);
		if(groups != null && groups.size() > 0) {
			return groups.get(0);
		}
		return null;
	}
	
	@PostMapping("/group")
    public Group saveGroup(@RequestBody Group group) {
		return groupRepo.save(group);
    }
	
	@GetMapping("/role")
	public List<Role> getAllRoles() {
		return roleRepo.findAll();
	}
	
	@GetMapping("/role/{name}")
	public Role getRole(@PathVariable String name) {
		List<Role> roles = roleRepo.findByName(name);
		if(roles != null && roles.size() > 0) {
			return roles.get(0);
		}
		return null;
	}
	
	@PostMapping("/role")
    public Role saveRole(@RequestBody Role role) {
		return roleRepo.save(role);
    }
	
	@PostMapping("/user/{username}/map/group")
    public String mapUserToGroup(@PathVariable String username, @RequestBody String[] groups) {
		User user = this.getUser(username);
		for (int i = 0; i < groups.length; i++) {
			Group group = this.getGroup(groups[i]);
			group.addUser(user);
			groupRepo.save(group);
		}
		return "success";
    }
	
	@PostMapping("/group/{groupname}/map/role")
    public String mapRolesToGroup(@PathVariable String groupname, @RequestBody String[] roles) {
		Group group = this.getGroup(groupname);
		for (int i = 0; i < roles.length; i++) {
			Role role = this.getRole(roles[i]);
			role.addGroup(group);
			roleRepo.save(role);
		}
		return "success";
    }
	
	
	@GetMapping("/clean")
	public void cleanAll() {
		roleRepo.deleteAll();
		groupRepo.deleteAll();
		userRepo.deleteAll();
	}
	
}
