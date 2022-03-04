package coms.controller;

import java.util.Date;
import java.util.List;

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
	
	@PostMapping("/loan")
	public ResponseEntity<ComsResult> processLOan(@RequestBody ComsEvent e) {
				
		String message = "Loan Process Id "+e.getProcessId()+", Event "+e.getCode()+" processed by handler "+ e.getHandler();
		System.out.println(message);
				
		try {
			Thread.sleep(10);
//			if(e.getCode().equals("EMPLOYMENT_CHECK")) {
//				System.out.println("Delaying EMPLOYMENT_CHECK");
//				Thread.sleep(10000);
//			}else {
//				Thread.sleep(500);
//			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ComsResult result = new ComsResult(true, message, e.getContext());
		
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	
	@PostMapping("/allcheckscomplete")
	public ResponseEntity<ComsResult> processLoanAllChecks(@RequestBody ComsEvent e) {
		
		String handlerName = null;
		List<ComsVariable> vars = e.getContext().getVariables();
		for (ComsVariable var : vars) {
			if(var.getName().equals("SERVICE_HANDLER")) {
				handlerName = var.getValue();
			}
		} 
				
		String message = "Loan Process Id "+e.getProcessId()+", Event "+e.getCode()+" processed by handler "+ handlerName;
		System.out.println(message);
				
		ComsResult result = new ComsResult(true, message, e.getContext());
		
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	
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
	
	@PostMapping("/env/notify")
	public ResponseEntity<ComsResult> notify(@RequestBody ComsEvent e) {
		
		String message = "Notifying customer. Process-id= "+e.getProcessId();
		System.out.println(message);
		
		e.getContext().addVariable(new ComsVariable("VAR-Notify","Val-NotifyService"));
		
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
