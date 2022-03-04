package coms.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import coms.process.EventDefinition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AbstractEventHandlerDef implements Serializable{

	private String name;
	private String type;
	private String[] nextEvents;
	
	public AbstractEventHandlerDef(String name, String type, String[] nextEvents) {
		this.name =  name;
		this.type = type;
		this.nextEvents = nextEvents;
	}
}
