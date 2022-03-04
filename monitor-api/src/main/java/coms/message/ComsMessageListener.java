package coms.message;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import coms.ProcessDefinitionRepository;
import coms.handler.AbstractEventHandlerDef;
import coms.handler.ComsEvent;
import coms.handler.ComsResult;
import coms.handler.DecisionHandler;
import coms.handler.DecisionHandlerDef;
import coms.handler.TaskHandler;
import coms.handler.TaskHandlerDef;
import coms.handler.IEventHandler;
import coms.handler.JavaHandlerDef;
import coms.handler.ServiceHandlerDef;
import coms.handler.sample.NewEnvHandler;
import coms.model.Event;
import coms.model.ProcessActivity;
import coms.model.ProcessInstance;
import coms.process.EventHandler;
import coms.process.ProcessContext;
import coms.process.ComsProcessDef;
import coms.process.ComsVariable;
import coms.process.EventDefinition;
import coms.service.EventService;
import coms.service.MessageService;
import coms.service.ProcessService;
import coms.service.TaskService;
import coms.util.ComsApiUtil;
import coms.util.ComsUtil;
import io.kubemq.sdk.event.Channel;
import io.kubemq.sdk.queue.Queue;
import io.kubemq.sdk.queue.Transaction;
import io.kubemq.sdk.queue.TransactionMessagesResponse;
import io.kubemq.sdk.tools.Converter;

@Component
public class ComsMessageListener {

	@Autowired
	public ProcessService jobService;
	
	@Autowired
	public MessageService messageService;
	
	@Autowired
	public TaskService taskService;
	
	@Autowired
	public EventService eventService;
	
	private Queue queue;
	private Channel channel;
	private NewEnvHandler orderProcessor;
	private TaskExecutor taskExecutor;
	
	
	public ComsMessageListener(Queue queue, Channel channel, NewEnvHandler orderProcessor, TaskExecutor taskExecutor) {
		this.queue = queue;
		this.channel = channel;
		this.orderProcessor = orderProcessor;
		this.taskExecutor = taskExecutor;		
	}	
	
	@PostConstruct
	public void listen() {
		taskExecutor.execute(() -> {
			while (true) {
			    try {
                    Transaction transaction = queue.CreateTransaction();
                    TransactionMessagesResponse response = transaction.Receive(10, 10);
                    if (response.getMessage().getBody().length > 0) {
                    	
                    	transaction.AckMessage();  
                    	
                    	ComsEvent event = (ComsEvent)Converter.FromByteArray(response.getMessage().getBody());
                    	
                    	processEvent(event);
                    	
                    } else {
                        System.out.println("No messages in Queue ...");
                    }
                    
                    Thread.sleep(10000);
                    
                } catch (Exception e) {         
                	System.out.println("Error: "+e.getMessage());
					e.printStackTrace();
                }
			}
		});
	}
	
	public Event fireEvent(String code, Long processId, ProcessContext context) {
		Event jpaNextEvent = new Event(null, code, new Date(), null, processId, context.serializeToString(), null, false);
		jpaNextEvent = eventService.save(jpaNextEvent);
		
		ComsEvent eve = new ComsEvent(jpaNextEvent.getId() ,code, new Date(), processId, context);
		messageService.sendMessage(eve);
		return jpaNextEvent;
	}
	
	public void markActivityCompletion(ProcessActivity activity, String success, String message, String context) {
		//Mark activity completed. 									
		activity.setSuccess(success);
		activity.setMessage(message);
		activity.setVariables(context);
		activity.setFinish(new Date());
		activity = jobService.updateActivity(activity);
	}
	
	public void processEvent(ComsEvent event) {
		
		try {
			Event jpaEvent = null;        	
        	if(event.getId() == null) {
        		//Event is not in database; first save it
        		jpaEvent = eventService.createEvent(event);
        		jpaEvent = eventService.save(jpaEvent);
        	}else {
        		jpaEvent = eventService.getEventById(event.getId());
        	}
        	
        	ProcessInstance pi = jobService.getJob(event.getProcessId());        	
        	Long processId = pi.getId();
        	ComsProcessDef processDef = ProcessDefinitionRepository.getProcessDefinition(pi.getCode());
        	EventDefinition eventDef = processDef.getEventByCode(event.getCode());
        	List<AbstractEventHandlerDef> handlers = eventDef.getHandlers();

        	System.out.println("Event received; Process "+pi.getCode()+ " :: Id "+ processId+", event "+ event.getCode());
        	        	
        	boolean allHandlersSuccessful = true;
        	boolean isDecisionNode = false;	
        	boolean isTaskNode = false;	
        	
        	
        	ComsResult result = null;
        	
        	if(handlers != null && handlers.size() > 0) {    
        		
        		//Let all handlers do their job 
        		for (AbstractEventHandlerDef thisHandlerDef : handlers) {
        			
            		//Record an activity start for this process instance	               
					ProcessActivity activity = jobService.markActivityStart(pi, event, thisHandlerDef, processDef);
					
					isDecisionNode = thisHandlerDef instanceof DecisionHandlerDef ? true : false; 
					
					isTaskNode = thisHandlerDef instanceof TaskHandlerDef ? true : false; 
					
					//Now start the actual activity
					result = executeHandler(eventDef, thisHandlerDef, event, activity);
									
					if(!result.isSuccess()) {
						allHandlersSuccessful = false; // At least one handler for current event did not finish successfully
					}
					
					//Fire next set of events for this handler, if defined
					//Only Service handlers will have next events. For Human tasks and Decision event, not applicable
					if(thisHandlerDef.getType() == ComsApiUtil.HANDLER_TYPE_SERVICE) {
                		String[] nextEventsHandler = thisHandlerDef.getNextEvents();
                		if(nextEventsHandler != null && nextEventsHandler.length > 0) {                    			                    	
                        	for (int k = 0; k < nextEventsHandler.length; k++) {
                        		fireEvent(nextEventsHandler[k], processId, (result.getContext() == null?null:result.getContext()));
    						}
                		}
					}
					
					//Mark activity completed. 	
					markActivityCompletion(activity, 
							(allHandlersSuccessful? "Y": "N"), result.getResult(), 
							(event.getContext() == null? null: event.getContext().serializeToString()));
				}
            	
        		// All handlers have successfully processed without error, fire events for next steps of process
				//For Human tasks, not applicable
            	if(allHandlersSuccessful && !isTaskNode) {
            		
            		if(event.getCode().equals("REVIEW_RESULTS")) {
            			System.out.println("Here");
            		}
            		
            		String[] nextEvents = processDef.getEventByCode(event.getCode()).getNextEvents();
            		
            		if(nextEvents != null && nextEvents.length > 0) {
                	
            			// Generally nextEvents to be fired. But for Decision Node, nextEvents should be fired only ONCE
            			boolean fireNextEvents = true;
            			
            			if(isDecisionNode) {
                			//Check if nextEvent for the Decision Node was/were already fired	
            				for (int i = 0; i < nextEvents.length; i++) {            					
            					List<Event> evs = eventService.find(nextEvents[i], processId);
            					if(evs != null && evs.size() > 0) {
            						System.out.println("Process id "+ processId +" event "+nextEvents[i] + " need not be fired again");		                    					
            						fireNextEvents = false;
            						break;
            					}
							}
            			}
            			
            			if(fireNextEvents) {
                			//Triggering all next events
                        	for (int k =0; k < nextEvents.length; k++) {                        		
                        		fireEvent(nextEvents[k], processId, (event.getContext() == null?null:event.getContext()));                        		                        		
    						}
            			}
            		}else {
            			//current event does not have any next event defined
            			// check if it is one of the end event, and if yes, if process instance has reached completion             			
            			if(processDef.isEndEvent(event.getCode())) {
							//It is (one of the) end events, update count of end Events completed for this process instance 
            				pi = jobService.updateEndEventCompletedCount(pi, processDef);	                    					                    				                    			
            			}
            		}	
            		
                	 //Event is processed. Update database
            		 jpaEvent.setStatus("Y");
               		 jpaEvent.setNextEvents(true);
           		 
            	} else {
            		//This case needs more thought. What to do in case one (or more) handler resulted in erroneous result 
            		//Event processed but at least some handler errored. Update database
            		 jpaEvent.setStatus("N");
               		 jpaEvent.setNextEvents(true);
            		 System.out.println("Error in processing event: "+event.getCode()+" , Process-Instance = "+event.getProcessId());                   		
            	}
            	
            	//Finally Update event record in DB 
            	jpaEvent.setFinish(new Date());
        		eventService.save(jpaEvent);
        	
        	}else { // No handler defined, this denotes end of process
        		//Event is processed. Update database
        		throw new Exception("***FATAL Error *** No handler defined for event "+event.getCode());
        	} 
    		
		}catch(Exception ee) {
			ee.printStackTrace();
			System.out.println("Error in processing event "+event.getCode()+" for Process Id "+event.getProcessId()+" :: "+ee.getMessage());
		}
	}

public ComsResult executeHandler(EventDefinition eventDef, AbstractEventHandlerDef handlerDef, ComsEvent event, ProcessActivity activity) throws Exception{
		
		ComsResult result = null;
		
		event.setHandler(handlerDef.getName());
		
		if(handlerDef instanceof TaskHandlerDef) {
    		//It is a human task in the process. The handler will create a task record and exit. 
			//Then engine should wait for user to act on that. 
			// Process activity for current step should not be marked completed and Next events should not be fired immediately
			// These should happen automatically when the task gets completed 
			TaskHandler humanTaskHandler = new TaskHandler(jobService, taskService);
			
			result = humanTaskHandler.process(event, eventDef, activity.getId(), (TaskHandlerDef)handlerDef);
		}else if(handlerDef instanceof DecisionHandlerDef) {
			
			DecisionHandlerDef decisionHandlerDef = (DecisionHandlerDef)handlerDef;
			
			//eventsAfterDecision = decisionHandlerDef.getEvents();
			
    		DecisionHandler handler = new DecisionHandler(jobService);
    		
    		result = handler.process(event, decisionHandlerDef);
    		
    	}else if(handlerDef instanceof JavaHandlerDef) {
    		//This is an automatic procss activity
    		JavaHandlerDef javaHandlerDef = (JavaHandlerDef)handlerDef;
    		IEventHandler handler = (IEventHandler)Class.forName(javaHandlerDef.getHandlerClass()).getConstructor(ProcessService.class).newInstance(jobService);		                    		
    		
    		result = handler.process(event);
    		
    	}else if(handlerDef instanceof ServiceHandlerDef) {
    		//This is an automatic procss activity
    		ServiceHandlerDef svcHandlerDef = (ServiceHandlerDef)handlerDef;
    		//System.out.println("Invoking service: "+svcHandlerDef.getService());
    		
    		RestTemplate restTemplate = new RestTemplate();	   
    		
    		ResponseEntity<ComsResult> res = restTemplate.postForEntity(new URI(svcHandlerDef.getService()), event, ComsResult.class);
    		result = res.getBody();
    		//System.out.println("Service response received: "+thisHandlerResult.isSuccess()+", result = "+ thisHandlerResult.getResult());
    	}else {
    		throw new Exception("**FATAL ERROR**: Unsupported event handler impl. "+handlerDef);
    	}
		
		return result;
	}
}
