package coms.handler;

import java.io.Serializable;

import coms.process.ProcessContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ComsResult implements Serializable{

	private boolean success;
	private String result;
	private ProcessContext context;
	
	public ComsResult(boolean success, String result, ProcessContext context) {
		super();
		this.success = success;
		this.result = result;
		this.context = context;
	} 
	
}
