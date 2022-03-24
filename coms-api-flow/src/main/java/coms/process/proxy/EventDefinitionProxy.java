package coms.process.proxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class EventDefinitionProxy implements Serializable{

	private String code;
	
	private String[] nextEvents;
	
	private List<EventHandlerDefProxy> handlers = new ArrayList<>();

}
