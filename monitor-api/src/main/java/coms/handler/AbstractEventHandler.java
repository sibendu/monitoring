package coms.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import coms.model.ProcessInstance;
import coms.service.ProcessService;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEventHandler{
	
	@Autowired
    private ProcessService jobService;
	
	public AbstractEventHandler(ProcessService jobService) {
		this.jobService = jobService;
	}
}
