package coms.message;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import coms.handler.AbstractEventHandlerDef;
import coms.handler.ComsEvent;
import coms.handler.ComsResult;
import coms.handler.DecisionHandler;
import coms.handler.DecisionHandlerDef;
import coms.handler.TaskHandler;
import coms.handler.TaskHandlerDef;
import coms.handler.IEventHandler;
import coms.handler.JavaHandlerDef;
import coms.handler.SampleJavaHandler;
import coms.handler.ServiceHandlerDef;
import coms.model.Event;
import coms.model.ProcessActivity;
import coms.model.ProcessDefinition;
import coms.model.ProcessInstance;
import coms.process.EventHandler;
import coms.process.ProcessContext;
import coms.process.proxy.ComsProcessDefProxy;
import coms.process.ComsProcessDef;
import coms.process.ComsVariable;
import coms.process.EventDefinition;
import coms.service.EventService;
import coms.service.ProcessService;
import coms.service.TaskService;
import coms.util.ComsApiUtil;
import coms.util.ComsUtil;

@Component
public class MessageListener {

	@Autowired
	private Queue input_queue;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	public ProcessService jobService;

	@Autowired
	public MessageService messageService;

	@Autowired
	public TaskService taskService;

	@Autowired
	public EventService eventService;

	@JmsListener(destination = "${INPUT_QUEUE}", concurrency = "3-5")
	public void processEvent(TextMessage message) {
		
		ComsEvent event = null;
		try {
			String jsonEvent = message.getText();
			event = new Gson().fromJson(jsonEvent, ComsEvent.class);
					
			Event jpaEvent = null;
			if (event.getId() == null) {
				// Event is not in database; first save it
				jpaEvent = eventService.createEvent(event);
				jpaEvent = eventService.save(jpaEvent);
			} else {
				jpaEvent = eventService.getEventById(event.getId());
			}

			ProcessInstance pi = jobService.getJob(event.getProcessId());
			Long processId = pi.getId();

			ProcessDefinition pDef = jobService.find(pi.getCode(), pi.getVersion());

			ComsProcessDefProxy proxyDef = new Gson().fromJson(pDef.getDefinition(), ComsProcessDefProxy.class);

			ComsProcessDef processDef = ComsApiUtil.convert(proxyDef);
			//System.out.println(processDef);

			EventDefinition eventDef = processDef.getEventByCode(event.getCode());
			List<AbstractEventHandlerDef> handlers = eventDef.getHandlers();

			System.out.println(
					"Event received; Process " + pi.getCode() + " :: Id " + processId + ", event " + event.getCode());

			boolean allHandlersSuccessful = true;
			boolean isDecisionNode = false;
			boolean isTaskNode = false;

			ComsResult result = null;

			if (handlers != null && handlers.size() > 0) {

				// Let all handlers do their job
				for (AbstractEventHandlerDef thisHandlerDef : handlers) {

					// Record an activity start for this process instance
					ProcessActivity activity = jobService.markActivityStart(pi, event, thisHandlerDef, processDef);

					isDecisionNode = thisHandlerDef instanceof DecisionHandlerDef ? true : false;

					isTaskNode = thisHandlerDef instanceof TaskHandlerDef ? true : false;

					// Now start the actual activity
					result = executeHandler(eventDef, thisHandlerDef, event, activity);
					
					if (result.isSuccess()) {
						
						// Fire next set of events for this handler, if defined
						// Only Service handlers and Decision handlers will have next events. Not applicable for Human tasks
						// // For Human tasks, next events will be fired when task is completed
						if (!thisHandlerDef.getType().equals(ComsApiUtil.HANDLER_TYPE_HUMATASK)) {
							
							String[] nextEventsHandler = thisHandlerDef.getNextEvents();
							if (nextEventsHandler != null && nextEventsHandler.length > 0) {
								for (int k = 0; k < nextEventsHandler.length; k++) {
									fireEvent(nextEventsHandler[k], processId,
											(result.getContext() == null ? null : result.getContext()));
								}
							}
							
							// Mark activity completed. For Human Task not needed
							markActivityCompletion(activity, (allHandlersSuccessful ? "Y" : "N"), result.getResult(),
									(event.getContext() == null ? null : event.getContext().serializeToString()));
						}						
					}else {
						allHandlersSuccessful = false;
						// This case needs more thought. What to do in case handler resulted in erroneous processing
						System.out.println(" *** Error in handler processing: " + event.getCode() + " , "+ result.getResult());
					}
				}

				if (allHandlersSuccessful && !isTaskNode && processDef.isEndEvent(event.getCode())) {
					// It is one of the end event. Check if process instance has reached completion
					// Update count of end Events completed for this process instance
					pi = jobService.updateEndEventCompletedCount(pi, processDef);
				}
				
				// All handlers have successfully processed without error, fire events for next
				// steps of process
				if (allHandlersSuccessful) {
					jpaEvent.setStatus(ComsApiUtil.EVENT_STATUS_SUCCESS);
				} else {
					// This case needs more thought. What to do in case one (or more) handler resulted in erroneous result
					jpaEvent.setStatus(ComsApiUtil.EVENT_STATUS_FAIL);
					System.out.println("Error in processing event: " + event.getCode() + " , Process-Instance = "
							+ event.getProcessId());
				}
				// Finally Update event record in DB
				jpaEvent.setFinish(new Date());
				eventService.save(jpaEvent);

			} else { // No handler defined, this denotes end of process
				// Event is processed. Update database
				throw new Exception("***FATAL Error *** No handler defined for event " + event.getCode());
			}

		} catch (Exception ee) {
			String exMsg = null;
			if(event != null) {
				exMsg = "Error in processing event " + event.getCode() + " for Process Id " + event.getProcessId() + " :: " + ee.getMessage();				
			}else {
				exMsg = "Error in processing message: "+ee.getMessage();
			}
			System.out.println(exMsg);
		}
	}

	public Event fireEvent(String code, Long processId, ProcessContext context) {
		Event jpaNextEvent = new Event(null, code, new Date(), null, processId, context.serializeToString(), null,
				false);
		jpaNextEvent = eventService.save(jpaNextEvent);

		ComsEvent eve = new ComsEvent(jpaNextEvent.getId(), code, new Date(), processId, context);
		messageService.sendMessage(eve);
		return jpaNextEvent;
	}

	public void markActivityCompletion(ProcessActivity activity, String success, String message, String context) {
		// Mark activity completed.
		activity.setSuccess(success);
		activity.setMessage(message);
		activity.setVariables(context);
		activity.setFinish(new Date());
		activity = jobService.updateActivity(activity);
	}

	public ComsResult executeHandler(EventDefinition eventDef, AbstractEventHandlerDef handlerDef, ComsEvent event,
			ProcessActivity activity) throws Exception {

		ComsResult result = null;

		event.setHandler(handlerDef.getName());

		if (handlerDef instanceof TaskHandlerDef) {
			// It is a human task in the process. The handler will create a task record and
			// exit.
			// Then engine should wait for user to act on that.
			// Process activity for current step should not be marked completed and Next
			// events should not be fired immediately
			// These should happen automatically when the task gets completed
			TaskHandler humanTaskHandler = new TaskHandler(jobService, taskService);

			result = humanTaskHandler.process(event, eventDef, activity.getId(), (TaskHandlerDef) handlerDef);
		} else if (handlerDef instanceof DecisionHandlerDef) {

			DecisionHandlerDef decisionHandlerDef = (DecisionHandlerDef) handlerDef;

			// eventsAfterDecision = decisionHandlerDef.getEvents();

			DecisionHandler handler = new DecisionHandler(jobService);

			result = handler.process(event, decisionHandlerDef);

		} else if (handlerDef instanceof JavaHandlerDef) {
			// This is an automatic procss activity
			JavaHandlerDef javaHandlerDef = (JavaHandlerDef) handlerDef;
			IEventHandler handler = (IEventHandler) Class.forName(javaHandlerDef.getHandlerClass())
					.getConstructor(ProcessService.class).newInstance(jobService);

			result = handler.process(event);

		} else if (handlerDef instanceof ServiceHandlerDef) {
			// This is an automatic procss activity
			ServiceHandlerDef svcHandlerDef = (ServiceHandlerDef) handlerDef;
			// System.out.println("Invoking service: "+svcHandlerDef.getService());

			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<ComsResult> res = restTemplate.postForEntity(new URI(svcHandlerDef.getService()), event,
					ComsResult.class);
			result = res.getBody();
			// System.out.println("Service response received:
			// "+thisHandlerResult.isSuccess()+", result = "+
			// thisHandlerResult.getResult());
		} else {
			throw new Exception("**FATAL ERROR**: Unsupported event handler impl. " + handlerDef);
		}

		return result;
	}
}
