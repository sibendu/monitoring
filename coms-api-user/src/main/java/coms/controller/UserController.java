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

import coms.model.User;
import coms.model.dto.UserDto;
import coms.service.UserService;

@RestController
public class UserController {
	@Autowired
	public UserService userService;
	@GetMapping("/getallusers")
	public ResponseEntity<List<User>> getallusers () {
		return new ResponseEntity<List<User>>(userService.getallusers(), HttpStatus.OK);
	}
	@GetMapping("/getuserbyloginid/{loginid}")
	public ResponseEntity<UserDto> getuserbyloginid(@PathVariable String loginid) {
		return new ResponseEntity<UserDto>(userService.getuserbyloginid(loginid), HttpStatus.OK);
	}
	@PostMapping("/registeruser")
    public String register(@RequestBody UserDto user) {
		userService.register(user);
    	return "{\"status\":\"success\"}";
    }
}
