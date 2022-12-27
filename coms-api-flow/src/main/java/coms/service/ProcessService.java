package coms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import coms.model.ProcessInstance;
import coms.model.repo.ProcessActivityRepository;
import coms.model.repo.ProcessInstanceRepository;
import coms.model.repo.ProcessRepository;
import coms.process.ComsProcessDef;
import coms.process.ProcessContext;
import coms.process.EventDefinition;
import coms.process.ProcessSearchRequest;
import coms.util.ComsApiUtil;
import coms.model.ProcessActivity;
import coms.handler.AbstractEventHandler;
import coms.handler.AbstractEventHandlerDef;
import coms.handler.ComsEvent;
import coms.handler.TaskHandler;
import coms.handler.TaskHandlerDef;
import coms.message.MessageService;
import coms.handler.IEventHandler;
import coms.handler.JavaHandlerDef;
import coms.handler.ServiceHandlerDef;
import coms.model.ProcessDefinition;

@Component
public class ProcessService {
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	public ProcessRepository processRepo;
	
	@Autowired
	public ProcessInstanceRepository repository;
	
	@Autowired
	public ProcessActivityRepository recordRepository;
	
	public Iterable<ProcessDefinition> getProcessDefinitions() {
		//System.out.println("JobService.getJobs()");
		return processRepo.findAll();
	}
	
	public ProcessDefinition getProcessDefinition(Long id) {
		return processRepo.findById(id).get();
	}
	
	public ProcessDefinition create(ProcessDefinition p) {
		return processRepo.save(p);
	}
	
	public ProcessDefinition save(ProcessDefinition def) {
		return processRepo.save(def);
	}
	
	public ProcessDefinition find(String code, String version) {
		ProcessDefinition def = null;
		List<ProcessDefinition> processes = processRepo.findByCodeAndVersion(code, version);
		if(processes != null && processes.size() > 0) {
			def = processes.get(0);
		}
		return def;
	}
	
	public List<ProcessDefinition> findDefByCodeAndStatus(ProcessSearchRequest request) {
		System.out.println("JobService.findByCodeAndStatus()");
		List<ProcessDefinition> defs = processRepo.findByCodeAndVersion(request.getCode(), request.getVersion());
		System.out.println("Result: "+defs.size());
		return defs;
	}
	
	public Iterable<ProcessInstance> getJobs() {
		//System.out.println("JobService.getJobs()");
		return repository.findAll();
	}
	
	public Iterable<ProcessActivity> getActivities(Long pid) {
		//System.out.println("JobService.getJobs()");
		return recordRepository.findByProcessId(pid);
	}
	
	public List<ProcessActivity> findByActivityByEvent(Long pid, String event) {
		return recordRepository.findByActivityByEvent(pid, event);
	}
	
	
	public ProcessInstance getJob(Long id) {
		//System.out.println("JobService.getJob(id)");
		return repository.findById(id).get();
	}
	
	public ProcessInstance save(ProcessInstance job) {
		//System.out.println("JobService.save(job)");
		return repository.save(job);
	}
	
	public ProcessActivity getJobRecord(Long id) {
		//System.out.println("JobService.getJobRecord(id)");
		return recordRepository.findById(id).get();
	}
	
	public ProcessActivity save(ProcessActivity jobRecord) {
		//System.out.println("JobService.save(jobRecord)");
		return recordRepository.save(jobRecord);
	}
	
	public ProcessInstance startProcess(String processCode,String version, ProcessContext context) {
		//System.out.println("JobService.startProcess(event)");

		//Create a new process instance record
		Date dt = new Date();
		ProcessInstance processInStance = new ProcessInstance(null, processCode, version, ComsApiUtil.PROCESS_STATUS_NEW, dt, dt);
		processInStance = repository.save(processInStance);
		
		// Trigger first event to start processing
		ProcessDefinition processDef = find(processCode, version);		
		ComsProcessDef process = new Gson().fromJson(processDef.getDefinition(), ComsProcessDef.class);  //ProcessDefinitionRepository.getProcessDefinition(processCode);
		EventDefinition startEvent = process.getStartEvent();
		ComsEvent event = new ComsEvent(null, startEvent.getCode(), new Date(), processInStance.getId(), context);
        
		messageService.sendMessage(event);
				
		return processInStance;
	}
	 
	public ProcessActivity markActivityStart(ProcessInstance pi, ComsEvent event, AbstractEventHandlerDef handlerDef, ComsProcessDef processDef) {// String eventCode, String handler, String description, String variables) {

		//Create a new activity record for current process instance
		
		String handler = null;
		if(handlerDef instanceof TaskHandlerDef) {
			handler = ComsApiUtil.HANDLER_TYPE_HUMATASK;
    	}else if(handlerDef instanceof JavaHandlerDef) {
    		handler = ((JavaHandlerDef)handlerDef).getHandlerClass();    		
    	}else if(handlerDef instanceof ServiceHandlerDef) {
    		handler = ((ServiceHandlerDef)handlerDef).getService();
    	}
		
		String variables = event.getContext() != null ? event.getContext().serializeToString(): null;		
		ProcessActivity act = new ProcessActivity(null, event.getCode(), handlerDef.getType(), handler , handlerDef.getName(), new Date(), null, variables, pi);		
		
		ProcessActivity activity = recordRepository.save(act);
		
		if(processDef.getStartEvent().getCode().equalsIgnoreCase(event.getCode())) {
			//This is first event. Mark instance status as WIP
			pi.setStatus(ComsApiUtil.PROCESS_STATUS_WIP);
			pi = repository.save(pi);
			System.out.println("PI updated");
		}
		
		
		return activity;
	}
	
	public ProcessActivity updateActivity(ProcessActivity rec) {		
		ProcessActivity rec1 = recordRepository.save(rec);
		return rec1;
	}
	
	public List<ProcessInstance> findByCodeAndStatus(ProcessSearchRequest request) {
		System.out.println("JobService.findByCodeAndStatus()");
		List<ProcessInstance> instances = repository.findByCodeAndStatus(request.getCode(), request.getVersion());
		System.out.println("Result: "+instances.size());
		return instances;
	}
	
	public List<ProcessInstance> findByCreatedAfter(Date birthDate) {
		return repository.findByCreatedAfter(birthDate);
	}
	
	public void cleanAll() {
		repository.deleteAll();
	}
	
	public void cleanAllProcessDef() {
		processRepo.deleteAll();
	}
	
	public ProcessInstance updateEndEventCompletedCount(ProcessInstance in,ComsProcessDef processDef) {
		
		ProcessInstance inst = repository.findById(in.getId()).get();//Latest record fetced
		
		inst.setNoEndEvents(inst.getNoEndEvents()+1);
		
		if(inst.getNoEndEvents() == processDef.getEndEvents().length) {
			//This was the last end event; process instance can be marked completed
			System.out.println("Marking process completed: process-id "+ inst.getId());
			inst.setStatus(ComsApiUtil.PROCESS_STATUS_COMPLETE);
		}
		inst.setUpdated(new Date());
		
		ProcessInstance newInst = repository.save(inst);
		return newInst;
	}
	
}
