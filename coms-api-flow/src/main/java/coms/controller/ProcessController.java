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
import coms.model.repo.ProcessInstanceRepository;
import coms.process.ComsProcessDef;
import coms.process.ProcessContext;
import coms.process.ProcessSearchRequest;
import coms.model.ProcessActivity;
import coms.model.ProcessDefinition;
import coms.handler.ComsEvent;
import coms.message.MessageService;
import coms.service.ProcessService;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/process")
public class ProcessController {
	
	@Autowired
	ProcessService processService;
	
	@GetMapping("/def")
	@Operation(summary="Get all process definition")
	public Iterable<ProcessDefinition> getProcessDefs() {
		System.out.println("ProcessController.getProcessDef()");
		return processService.getProcessDefinitions();
	}
	
	@GetMapping("/def/{id}")
	@Operation(summary="Get a process definition by its id")
	public ProcessDefinition getProcessDefById(@PathVariable Long id) {
		System.out.println("ProcessController.getProcessDefById(id)");
		return processService.getProcessDefinition(id);
	}
	
	@GetMapping("/def/{processCode}/{version}")
	@Operation(summary="Get a process definition by its code and version")
	public ProcessDefinition getProcessDef(@PathVariable String processCode, @PathVariable String version) {
		System.out.println("ProcessController.getProcessDef()");
		return processService.find(processCode, version);
	}
	
	@PostMapping("/def/{processCode}/{version}")
	@Operation(summary="Create a new process definition object")
	public ProcessDefinition createProcessDef(@PathVariable String processCode, @PathVariable String version, @RequestBody String def) {
		System.out.println("ProcessController.createPrcessDef()");
		ProcessDefinition p = new ProcessDefinition(processCode, version, "", def, "DRAFT");	
		return processService.create(p);
	}
	
	@PutMapping("/def/{id}")
	@Operation(summary="Update a process definition (identified by url param id)")
	public ProcessDefinition updateProcessDef(@PathVariable Long id, @RequestBody String def) {
		System.out.println("ProcessController.createPrcessDef()");
		ProcessDefinition pdef = processService.getProcessDefinition(id);
		pdef.setDefinition(def);		
		return processService.save(pdef);
	}
	
	
	/*Process Instance related End points*/
	
	@GetMapping("/instance")
	@Operation(summary="Get all process instances")
	public Iterable<ProcessInstance> findJobs() {
		return processService.getJobs();
	}
	
	@PostMapping("/instance/search")
	@Operation(summary="Search process instances (by code and version)")
	public List<ProcessInstance> findByCodeAndStatus(@RequestBody ProcessSearchRequest request) {
		System.out.println("ProcessController.findByCodeAndStatus()");
		return processService.findByCodeAndStatus(request);
	}
	
	@GetMapping("/instance/{id}")
	@Operation(summary="Get a process instance by id")
	public ProcessInstance findJob(@PathVariable Long id) {
		return processService.getJob(id);
	}
	
	@PostMapping("/instance/{processCode}/{version}/start")
	@Operation(summary="Initiate a new process instance")
	public String startProcess(@PathVariable String processCode,@PathVariable String version, @RequestBody ProcessContext context) {
		//System.out.println("JobController.createNewEnv()");
		ProcessInstance processInStance = processService.startProcess( processCode, version, context);		
		System.out.println("Process instantiated: "+processCode+", Instance-Id = "+processInStance.getId());
				
		return "Request accepted: Job Id = "+processInStance.getId();
		
	}
}
