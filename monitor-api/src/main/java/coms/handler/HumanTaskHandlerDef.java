package coms.handler;

import coms.util.ComsApiUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class HumanTaskHandlerDef extends AbstractEventHandlerDef {
	
	private String handlerClass;
	private String assignedToGroup;
	private String assignedToUser;
	
	public HumanTaskHandlerDef(String name, String[] nextEvents, String assignedToGroup, String assignedToUser) {
		super(name, ComsApiUtil.HANDLER_TYPE_HUMATASK, nextEvents);
		this.handlerClass = "coms.handler.ComsHumanTaskHandler";
		this.assignedToGroup = assignedToGroup;
		this.assignedToUser = assignedToUser;
	}
}
