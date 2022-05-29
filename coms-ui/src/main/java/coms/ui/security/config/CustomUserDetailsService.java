package coms.ui.security.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import coms.ui.HelperService;
import coms.ui.model.ComsUser;
import coms.ui.model.UserDto;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Value("${coms.userservice.uri}")
	private String userserviceUri;

	@Autowired
	HelperService helper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		List userRoles = new ArrayList<String>();
		userRoles.add("superadmin");
	    
		UserDto user = new UserDto(1001,"Sibendu", null, "Das", username,"", userRoles); //helper.GetUserByLoginid(username);
			
		String password = "password";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		
		if (username.equals("admin@coms.com")) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));//"$2a$10$0Nv/Qa7m8DdjWpBS2XRZWeP8rWDB7OdScb2grQSRDS9I9fWWlBNG2"
		}
		if (username.equals("user@coms.com")) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		}

		return new ComsUser(username, hashedPassword, roles, user);
	}

}
