package coms.ui.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
public class ComsUser extends User {
	
	private UserDto user;

	public ComsUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserDto user) {
		super(username, password, authorities);
		this.user = user;
	}
}
