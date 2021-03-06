package coms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import coms.handler.ComsEvent;
import coms.handler.JavaHandlerDef;
import coms.message.MessageService;
import coms.model.ProcessActivity;
import coms.model.ProcessDefinition;
import coms.model.ProcessInstance;
import coms.model.TaskActivity;
import coms.model.TaskInstance;
import coms.process.ComsProcessDef;
import coms.process.ProcessContext;
import coms.process.ComsVariable;
import coms.process.EventDefinition;
import coms.process.ProcessSearchRequest;
import coms.task.TaskAction;
import coms.util.ComsApiUtil;
import coms.model.TaskVariable;
import coms.model.repo.ProcessActivityRepository;
import coms.model.repo.TaskInstanceRepository;

@Component
public class TaskService {
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	ProcessActivityRepository processActivityRepo;
	
	@Autowired
	public TaskInstanceRepository taskRepository;
	
//	@Autowired
//	private Queue queue;
	
//	public JobService(Queue queue) {
//        this.queue = queue;
//    }
	
	public Iterable<TaskInstance> getTasks() {
		//System.out.println("JobService.getJob(id)");
		return taskRepository.findAll();
	}
	
	public TaskInstance getTask(long id) {
		//System.out.println("JobService.getJob(id)");
		return taskRepository.findById(id);
	}
	
	public TaskInstance save(TaskInstance t) {
		//System.out.println("JobService.save(job)");
		return taskRepository.save(t);
	}
	
	public TaskInstance createTask(TaskInstance task) {
		System.out.println("TaskService.createTask()");

		//Create a new process instance record
		Date dt = new Date();
		task.setCreated(dt);
		
		if(task.getVariables() != null) {
			for (TaskVariable var : task.getVariables()) {
				var.setTaskInstance(task);
			}
		}
		
		task = taskRepository.save(task);
				
		return task;
	}
	
	public TaskInstance claimTask(long taskId, String user) {
		System.out.println("TaskService.claimTask()");

		TaskInstance instance = taskRepository.findById(taskId);
		
		//To do: need to check if user belongs to right group
		instance.setAssignedUser(user);
		instance.setStatus(ComsApiUtil.TASK_ACTION_ASSIGNED);
		instance.setUpdated(new Date());
		
		TaskInstance inst = taskRepository.save(instance);
				
		return inst;
	}

	public TaskInstance completeTask(long taskId, TaskAction action) throws Exception{
		System.out.println("TaskService.completeTask(): id="+taskId+", user="+action.getUser());

		TaskInstance instance = taskRepository.findById(taskId);
		System.out.println("Found task id "+instance.getId()+", assigned to user("+instance.getAssignedUser()+") and group("+instance.getAssignedGroup()+")");
		
		if(instance.getAssignedUser() == null || instance.getAssignedUser().equals("")) {
			throw new Exception("Task not yet assigned to a user; first claim the task.");
		}else if(!instance.getAssignedUser().equalsIgnoreCase(action.getUser())) {
			throw new Exception("Task id "+taskId+" assigned to user "+instance.getAssignedUser()+"; "+action.getUser()+" cannot change it. First reassign the task.");
		}else {
			System.out.println("Updating task id "+instance.getId());
			
			instance.setStatus(ComsApiUtil.TASK_ACTION_COMPLETE);
			instance.setUpdated(new Date());
			
			//Record an activity on the task by current user
			TaskActivity actitivity = new TaskActivity(null, action.getUser(), new Date(), action.getRemarks(), instance);
			instance.addActivity(actitivity);
			
			//Add more variables for context of the task if passed in payload
			if(action.getVariables() != null && action.getVariables().size() > 0) {
				for (ComsVariable thisVar : action.getVariables()) {
					TaskVariable var = new TaskVariable(thisVar.getName(), thisVar.getValue());
					var.setTaskInstance(instance);
					instance.addVariable(var);
				}
			}
			
			//And finally, save it all
			instance = taskRepository.save(instance);
			
			if(instance.getProcessId() != null && instance.getActivityId() != null) {
				//This task is created from a process. 
				//Let us proceed with process activities	
				ProcessActivity act = processActivityRepo.findById(instance.getActivityId().longValue());
				act.setFinish(new Date());
				
				ProcessActivity updatedAct = processActivityRepo.save(act);	
				
				ProcessInstance pi = processService.getJob(instance.getProcessId());        
    			ProcessDefinition pDef = processService.find(pi.getCode(), pi.getVersion());		
        		ComsProcessDef processDef = new Gson().fromJson(pDef.getDefinition(), ComsProcessDef.class);  //ProcessDefinitionRepository.getProcessDefinition(processCode);
        		
        		//Check if it was the end event for process
        		if(processDef.isEndEvent(act.getEvent())) {
        			
        			//It is (one of the) end events, update count of end Events completed for this process instance 
        			pi = processService.updateEndEventCompletedCount(pi, processDef);
        		
        		}else { 
        			
        			//Task completed, trigger nextEvents
        			
        			EventDefinition eventDef = processDef.getEventByCode(act.getEvent());
        			String[] nextEvents = eventDef.getHandlers().get(0).getNextEvents();
        			
        			Set<TaskVariable> vars = instance.getVariables();
        			List<ComsVariable> comVars = new ArrayList<>();
        			for (TaskVariable tv : vars) {
    					comVars.add(new ComsVariable(tv.getName(), tv.getValue()));
    				}
        			
            		// The process definition has next events are defined for this task. Trigger them all
                	for (int i = 0; i < nextEvents.length; i++) {
    					String nextEvent = nextEvents[i];    							
    					ProcessContext processCtx = new ProcessContext();
    					processCtx.setVariables(comVars);
    					ComsEvent ev = new ComsEvent(null, nextEvent, new Date(), instance.getProcessId(), processCtx);
    					
    					messageService.sendMessage(ev);
    				}
                	
                	System.out.println("Task "+instance.getName()+" complete; fired next events to resume process-id "+instance.getProcessId());  
        		}
			}
			   		
		}		
		return instance;
	}
		
	public List<TaskInstance> findByAssignedUser(String user) {
		System.out.println("JobService.findByAssignedUser()");
		List<TaskInstance> instances = taskRepository.findByAssignedUser(user);
		System.out.println("Result: "+instances.size());
		return instances;
	}
	
	public void cleanAll() {
		taskRepository.deleteAll();
	}
}
