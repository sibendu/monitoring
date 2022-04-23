package coms.util;

import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import coms.handler.AbstractEventHandlerDef;
import coms.handler.DecisionHandlerDef;
import coms.handler.ServiceHandlerDef;
import coms.handler.TaskHandlerDef;
import coms.process.ComsProcessDef;
import coms.process.EventDefinition;
import coms.process.proxy.ComsProcessDefProxy;
import coms.process.proxy.EventDefinitionProxy;
import coms.process.proxy.EventHandlerDefProxy;

public class ComsApiUtil {
	
	public static String EVENT_STATUS_SUCCESS = "Y";
	public static String EVENT_STATUS_FAIL = "N";
	
	public static String PROCESS_STATUS_NEW = "NEW";
	public static String PROCESS_STATUS_WIP = "WIP";
	public static String PROCESS_STATUS_COMPLETE = "COMPLETED";
	
	public static String TASK_ACTON_NEW = "NEW";
	public static String TASK_ACTION_ASSIGNED = "ASSIGNED";
	public static String TASK_ACTION_COMPLETE = "COMPLETE";
	
	public static String HANDLER_TYPE_JAVA = "JAVA";
	public static String HANDLER_TYPE_DECISION = "DECISION";
	public static String HANDLER_TYPE_SERVICE = "SERVICE";
	public static String HANDLER_TYPE_HUMATASK = "HUMAN";
	
	public static String DECISION_AND = "AND";
	public static String DECISION_OR = "OR";
	
	public static String ACTIVITY_TYPE_TASK = "TASK";
	public static String ACTIVITY_TYPE_DEFAULT = "DEFAULT";
	
	public static String EVENT_TYPE_AUTOMATIC = "AUTO";
	public static String EVENT_TYPE_HUMANTASK = "HUMAN";
		
	public static void main(String[] args) {

		ComsProcessDef demo = DEMO_PROCESS();
		
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting();
		Gson gson = builder.create();  
	      
		String json = gson.toJson(demo);
		System.out.println(json);  
		
		ComsProcessDefProxy dd = gson.fromJson(json, ComsProcessDefProxy.class); 
	    System.out.println(dd);  

	    demo = ComsApiUtil.convert(dd);
		System.out.println(demo);
	}
	
	
	public static ComsProcessDef DEMO_PROCESS() {
		ComsProcessDef demo = new ComsProcessDef("DEMO_PROCESS");
		
		EventDefinition event1 = new EventDefinition("START");
		event1.addHandler(new ServiceHandlerDef("Env Provision", "http://localhost:8080/sample/env/create",  new String[] {"CONFIG_ENV"}));
		
		EventDefinition event2 = new EventDefinition("CONFIG_ENV");
		event2.addHandler(new ServiceHandlerDef("Env Config", "http://localhost:8080/sample/env/config",  new String[] {"NOTIFY_CUSTOMER"}));

		EventDefinition event3 = new EventDefinition("NOTIFY_CUSTOMER");
		event3.addHandler(new ServiceHandlerDef("Notify Customer", "http://localhost:8080/sample/env/notify",  null));
						
		demo.addEvent(event1);
		demo.addEvent(event2);
		demo.addEvent(event3);

		demo.setEndEvents(new String[] {"NOTIFY_CUSTOMER"});
		
		return demo;
	}
	
	/*
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
		event6.addHandler(new DecisionHandlerDef("All Checks Complete",new String[]{"UNDERWRITE","EMPLOYMENT_CHECK","CREDIT_CHECK"}, ComsUtil.DECISION_AND, new String[]{"AUTOMATIC_DECISION"}));
		
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
	*/
	
	public static ComsProcessDef convert(ComsProcessDefProxy proxy) {
		ComsProcessDef def = new ComsProcessDef(proxy.getCode());
		def.setEndEvents(proxy.getEndEvents());
		
		List<EventDefinitionProxy> proxies = proxy.getEvents();
		
		EventDefinition eventDef = null;
		for (EventDefinitionProxy p : proxies) {
			
			eventDef = new EventDefinition(p.getCode());
			
			List<EventHandlerDefProxy> handlers = p.getHandlers();
			
			AbstractEventHandlerDef handlerDef = null;
			for (EventHandlerDefProxy h : handlers) {
				
				String thisType = h.getType();
				
				if(HANDLER_TYPE_SERVICE.equals(thisType)) {
					handlerDef = new ServiceHandlerDef(h.getName(), h.getService(), h.getNextEvents());
				}else if(HANDLER_TYPE_HUMATASK.equals(thisType)) {
					handlerDef = new TaskHandlerDef(h.getName(), h.getAssignedToGroup(), h.getAssignedToUser());
				}else if(HANDLER_TYPE_DECISION.equals(thisType)) {
					handlerDef = new DecisionHandlerDef(h.getName(), h.getEvents(), h.getCondition(), h.getNextEvents());
				}
				eventDef.addHandler(handlerDef);
			}
			
			def.addEvent(eventDef);
		}
		return def;	
	}
}
