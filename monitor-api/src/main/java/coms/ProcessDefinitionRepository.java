package coms;

import java.util.HashMap;

import coms.handler.AbstractEventHandlerDef;
import coms.handler.HumanTaskHandler;
import coms.handler.HumanTaskHandlerDef;
import coms.handler.JavaHandlerDef;
import coms.handler.ServiceHandlerDef;
import coms.process.ComsProcess;
import coms.util.ComsApiUtil;

public class ProcessDefinitionRepository {
	
	private static HashMap<String, ComsProcess> processDefinitions = new HashMap<>();
	
	static {
		try {
			ComsProcess newEnvProcDef = define_NEW_ENV_REQ();
			
			processDefinitions.put(newEnvProcDef.getCode(), newEnvProcDef);
			
			ComsProcess demoProcDef = define_DEMO_PROCESS();
			
			processDefinitions.put(demoProcDef.getCode(), demoProcDef);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static ComsProcess define_DEMO_PROCESS() {
		ComsProcess demo = new ComsProcess("DEMO_PROCESS");
		
		demo.addEvent("START", 
				new AbstractEventHandlerDef[] { 
					new ServiceHandlerDef("Env Provision", "http://first-api:8080/sample/env/create",  null),
					new JavaHandlerDef( "New Env","coms.handler.sample.NewEnvHandler",null) 
				}, 
				new String[] {"STEP_1"});
		
		demo.addEvent("STEP_1", 
				new AbstractEventHandlerDef[] { 
						new ServiceHandlerDef( "Env Configuration", "http://localhost:8080/sample/env/config",null),
						new JavaHandlerDef( "Config env","coms.handler.sample.EnvConfigHandler",null) 
				}, 
				new String[] {"STEP_2"});

		demo.addEvent("STEP_2", 
				new AbstractEventHandlerDef[] { 
						new JavaHandlerDef( "Notify Customer","coms.handler.sample.CustomerNotificationHandler", null),
						new JavaHandlerDef("Generic handler", "coms.handler.sample.GenericHandler", null) 
				}, 
				new String[] {"STEP_3", "STEP_4"});
		
		demo.addEvent("STEP_3", 
				new AbstractEventHandlerDef[] { 
						new HumanTaskHandlerDef("Review Expense Report", new String[] {"STEP_5"}, null ,null) 
				}, 
				new String[] {"STEP_6"});
		
		demo.addEvent("STEP_4", 
				new AbstractEventHandlerDef[] { 
						new JavaHandlerDef( "Generic handler","coms.handler.sample.GenericHandler", null) 
				}, 
				null);
		
		demo.addEvent("STEP_5", 
				new AbstractEventHandlerDef[] { 
						new JavaHandlerDef( "Generic handler","coms.handler.sample.GenericHandler", null) 
				}, 
				null);
		
		demo.addEvent("STEP_6", 
				new AbstractEventHandlerDef[] { 
						new JavaHandlerDef( "Generic handler","coms.handler.sample.GenericHandler", null) 
				}, 
				null);
		
		demo.setEndEvents(new String[] {"STEP_4", "STEP_5", "STEP_6"});
		
		return demo;
	}
	
	public static ComsProcess define_NEW_ENV_REQ() {
		ComsProcess demo = new ComsProcess("NEW_ENV_REQ");
		
		demo.addEvent("NEW_ENV_PROVISION", 
				new JavaHandlerDef[] { 
						new JavaHandlerDef( "New Env Provision","coms.handler.sample.NewEnvHandler", null) 
				}, 
				new String[] {"NEW_ENV_CONFIG"});
		
		demo.addEvent("NEW_ENV_CONFIG", 
				new JavaHandlerDef[] { 
						new JavaHandlerDef( "Env Config", "coms.handler.sample.EnvConfigHandler", null) 
				}, 
				new String[] {"NOTIFY_CUSTOMER"});
		
		demo.addEvent("NOTIFY_CUSTOMER", 
				new JavaHandlerDef[] { 
						new JavaHandlerDef( "Notify Customer","coms.handler.sample.CustomerNotificationHandler",  null) 
				}, 
				null);
		
		demo.setEndEvents(new String[] {"NOTIFY_CUSTOMER"});
		
		return demo;
	}
	
	public static ComsProcess MY_ORDER_PROCESS() {
		ComsProcess demo = new ComsProcess("MY_ORDER_PROCESS");
		
		demo.setEndEvents(new String[] {"TEST"});
		
		return demo;
	}
	
	public static ComsProcess getProcessDefinition(String procesCode) {
		return processDefinitions.get(procesCode);
	}
}
