package coms.ui.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserDto {

	private long user_id;
	private String first_name;
	private String middle_name;
	private String last_name;
	//private String email;
	private String login_id;
	private String user_password;
	
	private List<String> roles;

	public UserDto(long user_id, String first_name, String middle_name, String last_name, String login_id,
			String user_password, List<String> roles) {
		super();
		this.user_id = user_id;
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		this.login_id = login_id;
		this.user_password = user_password;
		this.roles = roles;
	}
}
