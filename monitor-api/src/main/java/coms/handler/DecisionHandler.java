package coms.handler;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import coms.model.ProcessActivity;
import coms.model.ProcessInstance;
import coms.model.TaskInstance;
import coms.model.TaskVariable;
import coms.process.ProcessContext;
import coms.process.ComsVariable;
import coms.service.ProcessService;
import coms.service.TaskService;

@Component
public class DecisionHandler extends AbstractEventHandler implements IEventHandler{
	
    public DecisionHandler(ProcessService jobService) {
		super(jobService);
	}
    
    public ComsResult process(ComsEvent e) {
    	return new ComsResult(false, "**FATAL ERROR: This should have never been called", e.getContext());
    }
    
    public ComsResult process(ComsEvent e, DecisionHandlerDef decisionHandlerDef) {
    	
    	System.out.println("Decision handler :: process-instance = "+e.getProcessId()+", event = "+e.getCode());
    	ComsResult result = null;
    	String message = null;
    	try{
    		
        	//System.out.println("	Context: "+ e.getContext().serializeToString());
            
        	String[] events = decisionHandlerDef.getEvents();
        	
        	boolean[] eventCompleted = new boolean[events.length];
        	for (int i = 0; i < eventCompleted.length; i++) {
        		eventCompleted[i] = false;
        	}
        	
        	//ProcessInstance pi = this.getJobService().getJob(e.getProcessId());
        	Iterable<ProcessActivity> activties = this.getJobService().getActivities(e.getProcessId());
        	//Set<ProcessActivity> activties = pi.getRecords();
        	
        	for (ProcessActivity activity : activties) {
				for (int j = 0; j < events.length; j++) {
					if(activity.getEvent().equals(events[j]) && activity.getStart() != null && activity.getFinish() != null) {
						eventCompleted[j] = true;
						//System.out.println(activity.getEvent()+" is completed for Process Id "+e.getProcessId());
					}
				}
			}
        	
        	boolean eventsCompleted = true;
        	for (int k = 0; k < eventCompleted.length; k++) {
        		if(!eventCompleted[k]) {
        			System.out.println(events[k]+" is still not completed for Process Id "+e.getProcessId());
        			eventsCompleted = false;
        		}
        	}
        	
        	if(eventsCompleted) {
        		//All events are done
        		message = "All events for decision event are completed, Process id "+e.getProcessId()+" can proceed";
        		result = new ComsResult(true, message, e.getContext());
        	}else {
        		//All events are not done. Just skip
        		message = "Some events for decision event are still pending, Process id "+e.getProcessId();
        		result = new ComsResult(false, message, e.getContext());
        	}
        	
        	System.out.println(message);
        	
    	}catch(Exception ex) {
    		message = "Error in event handler "+e.getClass()+" :: Error = "+ex.getMessage();
            result = new ComsResult(false, message, e.getContext());
    	}
        return result;
    }

}
