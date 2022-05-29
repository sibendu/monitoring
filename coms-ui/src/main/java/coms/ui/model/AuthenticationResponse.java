package coms.ui.model;

public class AuthenticationResponse {
	
	private String token;
	private UserDto user;
	
	public AuthenticationResponse()
	{
	}
	
	public AuthenticationResponse(String token, UserDto user) {
		super();
		this.token = token;
		this.user = user;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
