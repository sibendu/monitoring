package coms;

import java.util.HashMap;

import org.springframework.boot.SpringApplication;

import com.google.gson.Gson;

import coms.handler.AbstractEventHandlerDef;
import coms.handler.DecisionHandlerDef;
import coms.handler.TaskHandler;
import coms.handler.TaskHandlerDef;
import coms.handler.JavaHandlerDef;
import coms.handler.ServiceHandlerDef;

import coms.process.*;
import coms.util.*;

public class ProcessDefinitionRepository {
	
	private static HashMap<String, ComsProcessDef> processDefinitions = new HashMap<>();
	
	static {
		try {
			ComsProcessDef loan = LOAN_PROCESS();
			processDefinitions.put(loan.getCode(), loan);
			
			ComsProcessDef demoProcDef = DEMO_PROCESS();
			processDefinitions.put(demoProcDef.getCode(), demoProcDef);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		ComsProcessDef demo = DEMO_PROCESS();
		System.out.println(new Gson().toJson(demo));  
		
		demo = LOAN_PROCESS();
		System.out.println(new Gson().toJson(demo));  
	}
	
	public static ComsProcessDef DEMO_PROCESS() {
		ComsProcessDef demo = new ComsProcessDef("DEMO_PROCESS");
		
		EventDefinition event1 = new EventDefinition("START", new String[] {"CONFIG_ENV"});
		event1.addHandler(new ServiceHandlerDef("Env Provision", "http://localhost:8080/sample/env/create",  null));
		
		EventDefinition event2 = new EventDefinition("CONFIG_ENV", new String[] {"NOTIFY_CUSTOMER"});
		event2.addHandler(new ServiceHandlerDef("Env Config", "http://localhost:8080/sample/env/config",  null));

		EventDefinition event3 = new EventDefinition("NOTIFY_CUSTOMER");
		event3.addHandler(new ServiceHandlerDef("Notify Customer", "http://localhost:8080/sample/env/notify",  null));
						
		demo.addEvent(event1);
		demo.addEvent(event2);
		demo.addEvent(event3);

		demo.setEndEvents(new String[] {"NOTIFY_CUSTOMER"});
		
		return demo;
	}
	
	public static ComsProcessDef LOAN_PROCESS() {
		ComsProcessDef demo = new ComsProcessDef("LOAN_PROCESS");
		
		EventDefinition event1 = new EventDefinition("COMPLETENESS_CHECK", new String[] {"CORRECTNESS_CHECK"});
		event1.addHandler(new ServiceHandlerDef("Check Applicaiton Completeness", "http://localhost:8080/sample/loan",  null));
		
		
		EventDefinition event2 = new EventDefinition("CORRECTNESS_CHECK", new String[] {"UNDERWRITE"});
		event2.addHandler(new ServiceHandlerDef("Check Applicaiton Correctness", "http://localhost:8080/sample/loan",  null));
		
		EventDefinition event3 = new EventDefinition("UNDERWRITE", new String[] {"ALL_CHECK_DONE"});
		event3.addHandler(new ServiceHandlerDef("Validate Borrowers", "http://localhost:8080/sample/loan",  new String[] {"EMPLOYMENT_CHECK","CREDIT_CHECK"}));
		event3.addHandler(new ServiceHandlerDef("Validate Property", "http://localhost:8080/sample/loan",  null));
		
		EventDefinition event4 = new EventDefinition("EMPLOYMENT_CHECK", new String[] {"ALL_CHECK_DONE"});
		event4.addHandler(new ServiceHandlerDef("Check Borrower Employement", "http://localhost:8080/sample/loan",  null));
		
		EventDefinition event5 = new EventDefinition("CREDIT_CHECK", new String[] {"ALL_CHECK_DONE"});
		event5.addHandler(new ServiceHandlerDef("Check Borrower Credit History", "http://localhost:8080/sample/loan",  null));
		
		EventDefinition event6 = new EventDefinition("ALL_CHECK_DONE", new String[] {"AUTOMATIC_DECISION"});
		event6.addHandler(new DecisionHandlerDef("All Checks Complete",new String[]{"UNDERWRITE","EMPLOYMENT_CHECK","CREDIT_CHECK"}, ComsUtil.DECISION_AND));
		
		EventDefinition event7 = new EventDefinition("AUTOMATIC_DECISION", new String[] {"REVIEW_RESULTS"});
		event7.addHandler(new ServiceHandlerDef("Run Automatic Decision", "http://localhost:8080/sample/loan",  null));		
		
		
		
		EventDefinition event8 = new EventDefinition("REVIEW_RESULTS", new String[] {"NOTIFY_CUSTOMER"});
		//event8.addHandler(new ServiceHandlerDef("Review All Results", "http://localhost:8080/sample/loan",  null));
		event8.addHandler(new TaskHandlerDef("Review All Results", null ,  "sibendu"));
		
		EventDefinition event9 = new EventDefinition("NOTIFY_CUSTOMER");
		event9.addHandler(new ServiceHandlerDef("Notify Customer", "http://localhost:8080/sample/loan",  null));
		
		demo.addEvent(event1);
		demo.addEvent(event2);
		demo.addEvent(event3);
		demo.addEvent(event4);
		demo.addEvent(event5);
		demo.addEvent(event6);
		demo.addEvent(event7);
		demo.addEvent(event8);
		demo.addEvent(event9);
		
		demo.setEndEvents(new String[] {"NOTIFY_CUSTOMER"});
	
		return demo;
	}
	
	public static ComsProcessDef getProcessDefinition(String procesCode) {
		return processDefinitions.get(procesCode);
	}
}
