package coms.process.proxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class EventHandlerDefProxy implements Serializable{

	private String name;
	private String type;
	private String[] nextEvents;
	private String service;
	private String handlerClass;
	private String assignedToGroup;
	private String assignedToUser;
	private String condition;
	private String[] events;
}
