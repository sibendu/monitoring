package coms.process;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import coms.handler.AbstractEventHandlerDef;
import coms.handler.ComsEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ComsProcessDef implements Serializable{
	
	private String code;
	
    private List<EventDefinition> events = new ArrayList<>();
    
    private String[] endEvents;
		
	public ComsProcessDef(String code) {
		super();
		this.code =  code;
	}
	
	public void addEvent(EventDefinition event) {
		this.events.add(event);
	}
	
//	public String[] getNextEvents(String currentEvent) {
//		for (ComsEventDefinition ev : events) {
//			if(ev.getCode().equals(currentEvent)) {
//				return ev.getNextEvents();
//			}
//		}
//		return null;
//	}
	
	public List<AbstractEventHandlerDef> getEventHandlers(String currentEvent) {
		for (EventDefinition ev : events) {
			if(ev.getCode().equals(currentEvent)) {
				return ev.getHandlers();
			}
		}
		return null;
	}
	
	public EventDefinition getStartEvent() {
		return events.get(0);
	}
	
	public boolean validateProcessDefinition() throws Exception{
		
		//End events must be defined
		if(endEvents == null || endEvents.length == 0 ) {
			throw new Exception("Process definition must have end events defined");
		}
		
		//For end events, there should not be further events
//		for (int i = 0; i < endEvents.length; i++) {
//			
//			AbstractEventHandlerDef[] hs = this.eventHandlers.get(endEvents[i]);
//			for (int j = 0; j < hs.length; j++) {
//				if(hs[i].getNextEvents() != null || hs[i].getNextEvents().length > 0) {
//					throw new Exception("Event "+ endEvents[i]+ "is an end event, but its handler has next event defined");
//				}
//			}
//			
//			String[] nextEvents = eventMap.get(endEvents[i]);
//			if(nextEvents != null || nextEvents.length > 0) {
//				throw new Exception("Next events are defined for the end event "+endEvents[i]);
//			}
//		}
		
		return true;
	}
	
	public EventDefinition getEventByCode(String code) {
		for (EventDefinition ev : events) {
			if(ev.getCode().equals(code)) {
				return ev;
			}
		}
		return null;
	}
	
	public boolean isEndEvent(String code) {
		for (int i = 0; i < endEvents.length; i++) {
			if(endEvents[i].equals(code)) {
				return true;
			}
		}
		return false;
	}
}
