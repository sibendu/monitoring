package coms.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coms.model.ProcessInstance;
import coms.process.ComsProcessDef;
import coms.process.ProcessContext;
import coms.process.ProcessSearchRequest;
import coms.model.ProcessActivity;
import coms.model.ProcessDefinition;
import coms.handler.ComsEvent;
import coms.model.ProcessInstanceRepository;
import coms.service.ProcessService;
import coms.service.MessageService;


@RestController
@RequestMapping("/process")
public class ProcessController {
	
	@Autowired
	ProcessService jobService;
	
	@GetMapping("/{processCode}/{version}")
	public ProcessDefinition getProcessDef(@PathVariable String processCode, @PathVariable String version) {
		System.out.println("ProcessController.getProcessDef()");
		return jobService.find(processCode, version);
	}
	
	@PostMapping("/{processCode}/{version}")
	public ProcessDefinition createProcessDef(@PathVariable String processCode, @PathVariable String version, @RequestBody String def) {
		System.out.println("ProcessController.createPrcessDef()");
		ProcessDefinition p = new ProcessDefinition(processCode, version, "", def, "DRAFT");	
		return jobService.create(p);
	}
	
	@GetMapping("/instance")
	public Iterable<ProcessInstance> findJobs() {
		return jobService.getJobs();
	}
	
	@GetMapping("/instance/{id}")
	public ProcessInstance findJob(@PathVariable Long id) {
		return jobService.getJob(id);
	}
	
	@PostMapping("/search")
	public List<ProcessInstance> findByCodeAndStatus(@RequestBody ProcessSearchRequest request) {
		System.out.println("ProcessController.findByCodeAndStatus()");
		return jobService.findByCodeAndStatus(request);
	}
	
	@PostMapping("/start/{processCode}/{version}")
	public String startEnv(@PathVariable String processCode,@PathVariable String version, @RequestBody ProcessContext context) {
		//System.out.println("JobController.createNewEnv()");
		ProcessInstance processInStance = jobService.startProcess( processCode, version, context);		
		System.out.println("Process instantiated: "+processCode+", Instance-Id = "+processInStance.getId());
				
		return "Request accepted: Job Id = "+processInStance.getId();
		
	}
}
