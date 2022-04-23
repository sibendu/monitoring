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
import coms.model.Permission;
import coms.model.Role;
import coms.model.User;
import coms.model.dto.UserDto;
import coms.model.repo.GroupRepository;
import coms.model.repo.PermissionRepository;
import coms.model.repo.RoleRepository;
import coms.model.repo.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	public UserRepository userRepo;
	
	@Autowired
	public GroupRepository groupRepo;
	
	@Autowired
	public RoleRepository roleRepo;
	
	@Autowired
	public PermissionRepository permissionRepo;
	
	@GetMapping("/user")
	public List<User> getAllUsers (){
		return userRepo.findAll();
	}
	
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable Long id) {
		return userRepo.findById(id).get();
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
	
	@GetMapping("/group/{id}")
	public Group getGroup(@PathVariable Long id) {
		return groupRepo.findById(id).get();
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
	
	@GetMapping("/role/{id}")
	public Role getRole(@PathVariable Long id) {
		return roleRepo.findById(id).get();
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
	
	@GetMapping("/permission")
	public List<Permission> getAllPermissions (){
		return permissionRepo.findAll();
	}
	
	
	@GetMapping("/permission/{id}")
	public Permission getPermission(@PathVariable Long id) {
		return permissionRepo.findById(id).get();
	}
	
	@GetMapping("/permission/{name}")
	public Permission getPermission(@PathVariable String name) {
		List<Permission> permissions = permissionRepo.findByName(name);
		if(permissions != null && permissions.size() > 0) {
			return permissions.get(0);
		}
		return null;
	}
	
	@PostMapping("/permission")
    public Permission savePermission(@RequestBody Permission permission) {
		return permissionRepo.save(permission);
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
	
	
	@PostMapping("/role/{rolename}/map/permission")
    public String mapPermissionsToRole(@PathVariable String rolename, @RequestBody String[] permissions) {
		Role role = this.getRole(rolename);
		for (int i = 0; i < permissions.length; i++) {
			Permission perm = this.getPermission(permissions[i]);
			perm.addRole(role);
			roleRepo.save(role);
		}
		return "success";
    }
	
	
	@GetMapping("/clean")
	public void cleanAll() {
		permissionRepo.deleteAll();
		roleRepo.deleteAll();
		groupRepo.deleteAll();
		userRepo.deleteAll();
	}
	
}
