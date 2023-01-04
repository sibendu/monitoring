package coms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/process")
public class ProcessDefinitionController {
	
	@Autowired
	ProcessService processService;
	
	@GetMapping("/def")
	@Operation(summary="Get all process definition")
	public Iterable<ProcessDefinition> getProcessDefs() {
		System.out.println("ProcessController.getProcessDef()");
		return processService.getProcessDefinitions();
	}
	
	@GetMapping("/def/{id}")
	@Operation(summary="Get a process definition by id")
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
	
	@PostMapping("/def/search")
	@Operation(summary="Search process definition (by code and version)")
	public List<ProcessDefinition> findByCode(@RequestBody ProcessSearchRequest request) {
		System.out.println("ProcessController.findByCode()");
		return processService.findDefByCodeAndStatus(request);
	}
	
	@PostMapping("/def/new")
	@Operation(summary="Create a new process definition object")
	public ProcessDefinition createProcessDef(@RequestBody ProcessDefinition def) {
		System.out.println("ProcessController.createPrcessDef()");
		return processService.create(def);
	}
	
	@PutMapping("/def/{id}")
	@Operation(summary="Update a process definition (identified by url param id)")
	public ProcessDefinition updateProcessDef(@PathVariable Long id, @RequestBody ProcessDefinition def) {
		System.out.println("ProcessController.updateProcessDef()");
		ProcessDefinition pdef = processService.getProcessDefinition(def.getId());
		pdef.setCode(def.getCode());
		pdef.setVersion(def.getVersion());
		pdef.setStatus(def.getStatus());
		pdef.setDescription(def.getDescription());
		pdef.setDefinition(def.getDefinition());
		
		return processService.save(pdef);
	}

	@DeleteMapping("/def/{id}")
	@Operation(summary="Delete a process definition by id")
	public boolean deleteProcessDefById(@PathVariable Long id) {
		System.out.println("ProcessController.deleteProcessDefById(id)");
		return processService.delteProcessDefinition(id);
	}
	
	@PostMapping("/def/")
	@Operation(summary="Delete process definitions using ids")
	public boolean deleteProcessDefByIds(@RequestBody List<Long> ids) {
		System.out.println("ProcessController.deleteProcessDefByIds(ids)");
		return processService.delteProcessDefinitions(ids);
	}
}
