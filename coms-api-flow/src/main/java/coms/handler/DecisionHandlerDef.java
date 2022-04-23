package coms.handler;

import java.io.Serializable;
import java.util.List;

import coms.process.EventDefinition;
import coms.util.ComsApiUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DecisionHandlerDef extends AbstractEventHandlerDef {

	private String condition;
	private String[] events;
	
	public DecisionHandlerDef(String name, String[] events, String condition, String[] ne) {
		super(name, ComsApiUtil.HANDLER_TYPE_DECISION, ne);
		this.events = events;
		this.condition = condition;
	}
}
