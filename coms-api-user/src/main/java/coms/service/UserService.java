package coms.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import coms.model.User;
import coms.model.UserRepository;
import coms.model.dto.UserDto;

@Component
public class UserService {
	@Autowired
	private UserRepository userRepo;
	//@Autowired
    //private ModelMapper modelMapper;
	//@Autowired
	//private PasswordEncoder bcryptEncoder;
	@Autowired
	JdbcTemplate jdbcTemplate;
	public List<User> getallusers()
	{
		return userRepo.getallusers();
	}
	public UserDto getuserbyloginid(String loginid)
	{
		List<User> users = userRepo.getUserByLoginid(loginid);
		if(users.size()>0)
		{
			UserDto userdto = convertEntityToUserDto(users.get(0));
			//List<String> roles = new ArrayList<String>();
			List<String> roles = jdbcTemplate.query(
				        "select group_name from user_groups \r\n"
				        + "inner join user_group_mapping on user_groups.group_id = user_group_mapping.group_id\r\n"
				        + "inner join users on user_group_mapping.user_id=users.user_id \r\n"
				        + "where users.login_id= ?", new Object[] { loginid },
				        (rs, rowNum) -> new String(rs.getString("group_name"))
				    );
			 userdto.setRoles(roles);
			return userdto;
		}
		return null;
	}
	public String register(UserDto user) {
		userRepo.save(convertUserToEntity(user));
    	return "{\"status\":\"success\"}";
    }
	private User convertUserToEntity(UserDto userDto) {
		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(userDto, User.class);
		user.setUser_password(new BCryptPasswordEncoder().encode(userDto.getUser_password()));
		return user;
	}
	private UserDto convertEntityToUserDto(User user) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(user, UserDto.class);
	}
}
