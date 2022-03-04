package coms.handler;

import java.io.Serializable;
import java.util.List;

import coms.process.EventDefinition;
import coms.util.ComsApiUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class JavaHandlerDef extends AbstractEventHandlerDef {

	private String handlerClass;
	
	public JavaHandlerDef(String name, String handlerClass, String[] nextEvents) {
		super(name, ComsApiUtil.HANDLER_TYPE_JAVA, nextEvents);
		this.handlerClass = handlerClass;
	}
}
