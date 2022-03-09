package coms.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import coms.model.ProcessInstance;
import coms.process.ProcessContext;
import coms.process.ComsVariable;
import coms.model.ProcessActivity;
import coms.service.ProcessService;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SampleJavaHandler extends AbstractEventHandler implements IEventHandler{
	
    public SampleJavaHandler(ProcessService jobService) {
		super(jobService);
	}

    public ComsResult process(ComsEvent e) {
    	//System.out.println("Event Handler fired: "+e.getCode()+", processor invoked: "+this.getClass());
    	ComsResult result = null;
    	String message = null;
        try {
            System.out.println("Calling CI/CD pipeline to start provisioning new environment .. ");
   
        	System.out.println("	Context: "+ e.getContext().serializeToString());
            
        	e.getContext().addVariable(new ComsVariable("VAR-NewEnv","Val-NewEnvHandler"));
        	
            Thread.sleep(3000);
            
            message = "New environment provisioning completed .. ";
            
            result = new ComsResult(true, message, e.getContext());
            
        } catch (Exception ex) {
            message = "Error in event handler "+e.getClass()+" :: Error = "+ex.getMessage();
            result = new ComsResult(false, message, e.getContext());
        }
    	//System.out.println("Event Handler completed: "+this.getClass()+" ::: Result = "+result);
        return result;
    }

}
