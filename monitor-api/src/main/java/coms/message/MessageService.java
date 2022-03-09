package coms.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import coms.handler.ComsEvent;

@Component
public class MessageService {
	
	@Autowired
	private Queue input_queue;
	 
    @Autowired
    private JmsTemplate jmsTemplate;
	
	public void sendMessage(ComsEvent event) {
		try {
			
			String json = new Gson().toJson(event, ComsEvent.class);
			
			jmsTemplate.send(input_queue, new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                  return session.createTextMessage(json);
                }
            });			
	    } catch (Exception e) {
	    	System.out.println("Error in posting event to queue: "+e.getMessage());
	    	e.printStackTrace();
	    }
	}
}
