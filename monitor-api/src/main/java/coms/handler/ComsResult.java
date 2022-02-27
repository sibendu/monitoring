package coms.handler;

import java.io.Serializable;

import coms.process.ComsProcessContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ComsResult implements Serializable{

	private boolean success;
	private String result;
	private ComsProcessContext context;
	
	public ComsResult(boolean success, String result, ComsProcessContext context) {
		super();
		this.success = success;
		this.result = result;
		this.context = context;
	} 
	
}
