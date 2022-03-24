package coms.handler;

import java.io.Serializable;
import java.util.Date;

import coms.process.ProcessContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ComsEvent implements Serializable {

	private Long id;
	private String code;
	private Date time;
	private Long processId;
	private String handler; 
	private ProcessContext context;

	public ComsEvent(Long id, String code, Date time, Long processId, ProcessContext context) {
		super();
		this.id = id;
		this.code = code;
		this.time=time;
		this.processId = processId;
		
		if(context != null) {
			this.context = context;
		}else {
			this.context= new ProcessContext();
		}
	}	
}
