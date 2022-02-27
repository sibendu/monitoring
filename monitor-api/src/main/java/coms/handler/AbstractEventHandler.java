package coms.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import coms.model.ProcessInstance;
import coms.service.ProcessService;

public abstract class AbstractEventHandler{
	
	@Autowired
    private ProcessService jobService;
	
	public AbstractEventHandler(ProcessService jobService) {
		this.jobService = jobService;
	}

	public ProcessService getJobService() {
		return jobService;
	}

	public void setJobService(ProcessService jobService) {
		this.jobService = jobService;
	}
}
