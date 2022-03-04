package coms.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import coms.ProcessDefinitionRepository;
import coms.handler.ComsEvent;
import coms.model.ProcessInstanceRepository;
import coms.model.TaskInstance;
import coms.model.TaskVariable;
import coms.service.ProcessService;
import coms.service.TaskService;
import coms.task.TaskAction;
import coms.util.ComsApiUtil;
import coms.service.EventService;
import coms.service.MessageService;


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
	public String index() {
		return new Date() + "\n";
	}
	
	@GetMapping("/cleanall")
	public String clean() {
		processService.cleanAll();
		taskService.cleanAll();
		eventService.cleanAll();
		processService.cleanAllProcessDef();
		return "All records removed successfully";
	}

}
