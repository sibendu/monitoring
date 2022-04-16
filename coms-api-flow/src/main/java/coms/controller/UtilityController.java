package coms.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coms.model.ProcessInstance;
import coms.process.ComsProcessDef;
import coms.process.ProcessContext;
import coms.process.ComsVariable;
import coms.process.ProcessSearchRequest;
import coms.model.ProcessActivity;
import coms.handler.ComsEvent;
import coms.message.MessageService;
import coms.model.ProcessInstanceRepository;
import coms.model.TaskInstance;
import coms.model.TaskVariable;
import coms.service.ProcessService;
import coms.service.TaskService;
import coms.task.TaskAction;
import coms.util.ComsApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import coms.service.EventService;


@RestController
@RequestMapping("/util")
public class UtilityController {
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	EventService eventService;
	
	@GetMapping("/live")
	@Operation(summary="Utility method: liveness checking") 
	public String index() {
		return "Time xx: " + new Date().toString();  
	}
	
	@GetMapping("/clean")
	@Operation(summary="Utility method: Clean all records (except process definitions) from database")
	public String clean() {
		processService.cleanAll();
		taskService.cleanAll();
		eventService.cleanAll();
		return "All records removed successfully";
	}

	@GetMapping("/cleandef")
	@Operation(summary="Utility method: Clean all process definitions")
	public String cleanDef() {
		processService.cleanAllProcessDef();
		return "All process definitions removed successfully";
	}
}
