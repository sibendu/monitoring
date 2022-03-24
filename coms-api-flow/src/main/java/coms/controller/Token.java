package coms.controller;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Token implements Serializable {
	private String token;

	public Token(String token) {
		super();
		this.token = token;
	}
	
	
}
