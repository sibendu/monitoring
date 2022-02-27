package coms.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/sample")
public class SampleController {
	
	@PostMapping("/env/create")
	public ResponseEntity<ComsResult> createEnv(@RequestBody ComsEvent e) {
		
		String message = "Calling CI/CD pipeline to start provisioning new environment. Process-id= "+e.getProcessId();
		System.out.println(message);
		
		e.getContext().addVariable(new ComsVariable("VAR-NewEnv","NewEnvService"));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ComsResult result = new ComsResult(true, message, e.getContext());
		
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	@PostMapping("/env/config")
	public ResponseEntity<ComsResult> configEnv(@RequestBody ComsEvent e) {
		
		String message = "Configuring environment. Process-id= "+e.getProcessId();
		System.out.println(message);
		
		e.getContext().addVariable(new ComsVariable("VAR-ConfigEnv","Val-ConfigEnvService"));
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ComsResult result = new ComsResult(true, message, e.getContext());
		
		return new ResponseEntity(result, HttpStatus.OK);
	}
}
