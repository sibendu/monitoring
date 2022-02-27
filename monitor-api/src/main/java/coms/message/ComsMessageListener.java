package coms.message;

import java.net.URI;
import java.util.Date;

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
import coms.handler.HumanTaskHandler;
import coms.handler.HumanTaskHandlerDef;
import coms.handler.IEventHandler;
import coms.handler.JavaHandlerDef;
import coms.handler.ServiceHandlerDef;
import coms.handler.sample.NewEnvHandler;
import coms.model.ProcessActivity;
import coms.model.ProcessInstance;
import coms.process.ComsProcess;
import coms.service.MessageService;
import coms.service.ProcessService;
import coms.service.TaskService;
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
                    	
                    	String eventCode = event.getCode();
                    	
                    	ProcessInstance currentProcessInstance = jobService.getJob(event.getProcessId());
                    	String processCode = currentProcessInstance.getCode();
                    	
                    	System.out.println("Event "+ eventCode+" received for Process "+processCode+ " :: process-instance "+currentProcessInstance.getId());
                    	
                    	ComsProcess processDef = ProcessDefinitionRepository.getProcessDefinition(processCode);
                    	
                    	if(processDef.getStartEvent().equalsIgnoreCase(eventCode)) {
                    		//This is first event, mark process instance as started
                    	}
                    	
                    	//Fire handlers to process this event
                    	AbstractEventHandlerDef[] handlers = processDef.getEventHandlers(event.getCode());
                    	                   	                    	                    	
                    	boolean handlersSuccessful = true;
                    	String success = "N";
                    	ComsResult thisHandlerResult = null;
                    	String processVars =  null;
                    	
                    	if(handlers != null && handlers.length > 0) {    
                    		
                    		//Let all handlers do their job 
	                    	for (int i = 0; i < handlers.length; i++) {
								
	                    		AbstractEventHandlerDef thisHandlerDef = handlers[i];
	                    		IEventHandler handler = null;
	                    		boolean isHUmanTask = false;	                    		
	                    		
	                    		//Record an activity start for this process instance
	                    		
								ProcessActivity activityRecord = jobService.markActivityStart(currentProcessInstance, event, thisHandlerDef, processDef);
								
								//Now start the actual activity
	                    		if(thisHandlerDef instanceof HumanTaskHandlerDef) {
	                        		//It is a human task in te process. The handler will create a task record and exit. 
	                    			//Then engine should wait for user to act on that. 
	                    			// Process activity for current step should not be marked completed and Next events should not be fired immediately
	                    			// These should happen automatically when the task gets completed 
	                    			isHUmanTask = true;
	                    			
	                    			HumanTaskHandler humanTaskHandler = new HumanTaskHandler(jobService, taskService);
	                    			
	                    			thisHandlerResult = humanTaskHandler.process(event, activityRecord.getId(), (HumanTaskHandlerDef)thisHandlerDef);
	                        	}else if(thisHandlerDef instanceof JavaHandlerDef) {
	                        		//This is an automatic procss activity
	                        		JavaHandlerDef javaHandlerDef = (JavaHandlerDef)thisHandlerDef;
	                        		handler = (IEventHandler)Class.forName(javaHandlerDef.getHandlerClass()).getConstructor(ProcessService.class).newInstance(jobService);		                    		
	                        		
	                        		thisHandlerResult = handler.process(event);
	                        		
	                        	}else if(thisHandlerDef instanceof ServiceHandlerDef) {
	                        		//This is an automatic procss activity
	                        		ServiceHandlerDef svcHandlerDef = (ServiceHandlerDef)thisHandlerDef;
	                        		System.out.println("Invoking service: "+svcHandlerDef.getService());
	                        		
	                        		RestTemplate restTemplate = new RestTemplate();	   
	                        		
	                        		ResponseEntity<ComsResult> res = restTemplate.postForEntity(new URI(svcHandlerDef.getService()), event, ComsResult.class);
	                        		thisHandlerResult = res.getBody();
	                        		System.out.println("Service response received: "+thisHandlerResult.isSuccess()+", result = "+ thisHandlerResult.getResult());
	                        	}else {
	                        		System.out.println("**FATAL ERROR**: Unsupported event handler impl. "+thisHandlerDef);
	                        	}
																
								success = "N";
								if(thisHandlerResult.isSuccess()) {
									success = "Y";
								}else {
									handlersSuccessful = false; // At least one handler for current event did not finish successfully
								}
								
								if(!isHUmanTask) {
									
									// It is an Automatic process activity. Mark activity completed. 									
									
									activityRecord.setSuccess(success);
									activityRecord.setMessage(thisHandlerResult.getResult());
									
									if(event.getContext() != null) {
										activityRecord.setVariables( event.getContext().serializeToString());
									}
									
									activityRecord = jobService.markActivityEnd(activityRecord);
									
									
									//Fire next set of events for this handler, if defined
		                    		String[] nextEventsFromHandler = thisHandlerDef.getNextEvents();
		                    		if(nextEventsFromHandler != null) {                    			                    	
			                        	for (int k = 0; k < nextEventsFromHandler.length; k++) {
			    							String nextHandlerEvent = nextEventsFromHandler[k];    							
			    							ComsEvent eve = new ComsEvent(nextHandlerEvent, new Date(), event.getProcessId(), event.getContext());
			    							messageService.sendMessage(eve);
			    						}
		    				    		//System.out.println("Next events triggered : "+ nextEvents.toString());
		                    		}
	                    		
								}else {
									System.out.println("Human task created, activity is open and handler level events kept on hold");
								}
							}
	                    	
	                    	String[] nextEvents = processDef.getNextEvents(event.getCode());
	                    	
	                    	// All handlers have successfully processed without error, decide what to do next 
	                    	if(handlersSuccessful) {
	                    		
	                    		if(nextEvents != null && nextEvents.length > 0) {
		                    		// next events are defined. Trigger them all
		                        	for (int i = 0; i < nextEvents.length; i++) {
		    							String nextEvent = nextEvents[i];    							
		    							ComsEvent ev = new ComsEvent(nextEvent, new Date(), event.getProcessId(), event.getContext());
		    							messageService.sendMessage(ev);
		    						}
	                    		}else {
	                    			//current event does not have any next event defined
	                    			// check if it is one of the end event, and if yes, if process instance has reached completion 
	                    			
	                    			if(processDef.isEndEvent(eventCode)) {
										//It is (one of the) end events, update count of end Events completed for this process instance 
	                    				currentProcessInstance = jobService.updateEndEventCompletedCount(currentProcessInstance, processDef);	                    					                    				                    			
	                    			}
	                    		}	               	
	                    	}else {
	                    		//This case needs more thought. What to do in case one (or more) handler resulted in erroneous result 
	                    		
	                    		System.out.println("Error in processing event: "+event.getCode()+" , Process-Instance = "+event.getProcessId());                   		
	                    	}     
                    	
                    	}else { // No handler defined, this denotes end of process
                    		throw new Exception("***FATAL Error *** No handler defined for event "+eventCode);
                    	}                    	                  	                                               
                    	
                    } else {
                        System.out.println("No messages");
                    }
                    
                    Thread.sleep(10000);
                    
                } catch (Exception e) {         
                	System.out.println("Error: "+e.getMessage());
					e.printStackTrace();
                }
			}
		});

	}

}
