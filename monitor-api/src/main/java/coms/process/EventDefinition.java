package coms.process;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import coms.handler.AbstractEventHandlerDef;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class EventDefinition implements Serializable{

	private String code;
	
	private String[] nextEvents;
	
	private List<AbstractEventHandlerDef> handlers = new ArrayList<>();

	public EventDefinition(String code) {
		super();
		this.code = code;
	}
	
	public EventDefinition(String code, String[] nextEvents) {
		super();
		this.code = code;
		this.nextEvents = nextEvents;
	}
	
	public void addHandler(AbstractEventHandlerDef handler) {
		this.handlers.add(handler);
	}
}
