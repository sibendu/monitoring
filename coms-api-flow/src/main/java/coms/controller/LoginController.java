package coms.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import coms.handler.ComsEvent;
import coms.handler.ComsResult;
import coms.model.*;
import coms.process.ComsVariable;
import coms.service.*;


@RestController
@RequestMapping("/")
public class LoginController {
	
	@CrossOrigin
	@PostMapping("/login")
	public Token login() {
		System.out.println("LoginController.login()");
		return new Token("7647364734647348721");
	}
}
