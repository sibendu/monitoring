package coms.handler;

import java.io.Serializable;
import java.util.List;

import coms.process.EventDefinition;
import coms.util.ComsApiUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ServiceHandlerDef extends AbstractEventHandlerDef {

	private String service;
	
	public ServiceHandlerDef(String name, String service, String[] nextEvents) {
		super(name, ComsApiUtil.HANDLER_TYPE_SERVICE, nextEvents);
		this.service = service;
	}
}
