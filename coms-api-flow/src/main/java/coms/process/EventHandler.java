package coms.process;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class EventHandler implements Serializable {

	private String name;
	private String service;
	private List<EventDefinition> nextEvents = new ArrayList<>();
	
	public EventHandler(String name, String service, List<EventDefinition> nextEvents) {
		super();
		this.name = name;
		this.service = service;
		this.nextEvents = nextEvents;
	}
	
	public void addNextEvent(EventDefinition nextEvent) {
		this.nextEvents.add(nextEvent);
	}
}
