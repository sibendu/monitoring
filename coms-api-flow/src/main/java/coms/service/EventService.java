package coms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import coms.model.ProcessInstance;
import coms.model.repo.EventRepository;
import coms.model.repo.ProcessActivityRepository;
import coms.model.repo.ProcessInstanceRepository;
import coms.process.ComsProcessDef;
import coms.process.ProcessContext;
import coms.process.EventDefinition;
import coms.process.ProcessSearchRequest;
import coms.util.ComsApiUtil;
import coms.model.Event;
import coms.model.ProcessActivity;
import coms.handler.AbstractEventHandler;
import coms.handler.AbstractEventHandlerDef;
import coms.handler.ComsEvent;
import coms.handler.TaskHandler;
import coms.handler.TaskHandlerDef;
import coms.handler.IEventHandler;
import coms.handler.JavaHandlerDef;
import coms.handler.ServiceHandlerDef;

@Component
public class EventService {
	
	@Autowired
	public EventRepository repository;
	
	public Iterable<Event> getJobs() {
		return repository.findAll();
	}
	
	public Event getEventById(Long id) {
		return repository.findById(id).get();
	}
	
	public Event save(Event e) {
		return repository.save(e);
	}

	public Event createEvent(ComsEvent event) {
		Event e = new Event(null, event.getCode(), new Date(), null, event.getProcessId(), (event.getContext()==null?null:event.getContext().serializeToString()), null, false);				
		return repository.save(e);
	}

	
	public List<Event> find(String code, Long processId) {
		return repository.find(code, processId);
	}
	
	public void cleanAll() {
		repository.deleteAll();
	}
}
