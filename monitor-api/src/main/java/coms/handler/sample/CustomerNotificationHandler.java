package coms.handler.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import coms.handler.AbstractEventHandler;
import coms.handler.ComsEvent;
import coms.handler.ComsResult;
import coms.handler.IEventHandler;
import coms.model.ProcessInstance;
import coms.process.ProcessContext;
import coms.process.ComsVariable;
import coms.service.ProcessService;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomerNotificationHandler extends AbstractEventHandler implements IEventHandler{
	
    public CustomerNotificationHandler(ProcessService jobService) {
		super(jobService);
	}
    public ComsResult process(ComsEvent e) {
    	//System.out.println("Event Handler fired: "+e.getCode()+", processor invoked:"+this.getClass());
    	ComsResult result = null;
    	String message = null;
        try {
            System.out.println("Environment has been configured, sedning notification to customer");
            
        	System.out.println("	Context: "+ e.getContext().serializeToString());
        	
        	Thread.sleep(1000);
        	
        	message =  "Notification sent to customer";
            
            result = new ComsResult(true, message, e.getContext());
        } catch (Exception ex) {
            //ex.printStackTrace();
        	message = "Error in event handler "+e.getClass()+" :: Error = "+ex.getMessage();
            result = new ComsResult(false, message, e.getContext());
        }
    	//System.out.println("Event Handler completed: "+this.getClass()+" ::: Result = "+result);
        return result;
    }

}
