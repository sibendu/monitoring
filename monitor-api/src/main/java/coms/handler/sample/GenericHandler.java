package coms.handler.sample;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import coms.handler.AbstractEventHandler;
import coms.handler.ComsEvent;
import coms.handler.ComsResult;
import coms.handler.IEventHandler;
import coms.process.ComsProcessContext;
import coms.process.ComsVariable;
import coms.service.ProcessService;

@Component
public class GenericHandler extends AbstractEventHandler implements IEventHandler{
	
    public GenericHandler(ProcessService jobService) {
		super(jobService);
	}

    public ComsResult process(ComsEvent e) {
    	//System.out.println("Generic event handler :: process-instance = "+e.getProcessId()+", event = "+e.getCode());
    	ComsResult result = null;
    	String message = null;
    	try{
    		
    		System.out.println("Generic Handler processing ....");
        	System.out.println("	Context: "+ e.getContext().serializeToString());
            
        	//e.getContext().addVariable(new ComsVariable("VAR-Generic","Val-GenericHandler"));
        	
    		Thread.sleep(2000);
    		
    		message = "GenericHandler completed .. ";
            
            result = new ComsResult(true, message, e.getContext());
            
    	}catch(Exception ex) {
    		message = "Error in event handler "+e.getClass()+" :: Error = "+ex.getMessage();
            result = new ComsResult(false, message, e.getContext());
    	}
        return result;
    }

}
