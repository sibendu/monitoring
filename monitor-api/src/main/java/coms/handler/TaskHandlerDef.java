package coms.handler;

import java.util.List;

import coms.process.EventDefinition;
import coms.util.ComsApiUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TaskHandlerDef extends AbstractEventHandlerDef {
	
	private String handlerClass;
	private String assignedToGroup;
	private String assignedToUser;
	
	public TaskHandlerDef(String name, String assignedToGroup, String assignedToUser) {
		super(name, ComsApiUtil.HANDLER_TYPE_HUMATASK, null);
		this.handlerClass = "coms.handler.TaskHandler";
		this.assignedToGroup = assignedToGroup;
		this.assignedToUser = assignedToUser;
	}
}
