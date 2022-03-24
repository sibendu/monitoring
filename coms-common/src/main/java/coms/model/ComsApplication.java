package coms.model;

import java.util.Date;
import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ComsApplication {
	
	@Autowired 
	EventRepository eventRepo;
	
	@Autowired 
	ProcessInstanceRepository repo;
	
	@Autowired 
	ProcessActivityRepository recRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(ComsApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			
//			Long processId = new Long(1001);
//			String code ="ABC";
//			
//			Event jpaNextEvent = new Event(null, code, new Date(), null, processId, null, null , false);
//			jpaNextEvent = eventRepo.save(jpaNextEvent);
//			
//			System.out.println("Fired Next event: "+jpaNextEvent.getCode()+" -- "+jpaNextEvent.getId());
//			
//			List<Event> evs = eventRepo.find(code, processId);
//			if(evs != null && evs.size() > 0) {
//				System.out.println("Process id "+ processId +" event "+code + " is fired sucessfully");	
//			}
			
			System.out.println("Let's inspect the beans provided by Spring Boot:");
//			ProcessInstance job = new ProcessInstance(null,"A", "NEW",new Date(), null);	
//			job = repo.save(job);
//			System.out.println(job.getId());
			
//			ProcessInstance pi = repo.findById(79);
//				
//			ProcessActivity act = new ProcessActivity(null,"Event", "handler", "handler-name", new Date(), null, "test", pi);		
//			
//			pi.addActivity(act);
//			
//			ProcessInstance instance = repo.save(pi);
			
//			ProcessInstance jobB = new ProcessInstance(null,"B", "NEW",new Date(), null);	
//			ProcessActivity rec1 = new ProcessActivity(null, "event1", "handler1" , "Step 1", new Date(),null, jobB);
//			jobB.getRecords().add(rec1);
//			jobB = repo.save(jobB);
//			System.out.println(jobB.getId()+" - "+rec1.getId());
//			
//			
//			ProcessInstance jobC = repo.findById(1);			
//			ProcessActivity rec3 = new ProcessActivity(null, "event1", "handler1" , "Step 1", new Date(),null, jobC);
//			ProcessActivity rec4 = recRepo.save(rec3);
//			System.out.println(jobC.getId()+" - "+rec4.getId());
//			
//			rec4.setFinish(new Date());
//			ProcessActivity rec5 = recRepo.save(rec3);
//			System.out.println(jobC.getId()+" - "+rec5.getId());
			
			
			List<ProcessInstance> jobs = repo.findByCodeAndStatus("DEMO_PROCESS", "COMPLETED");
			System.out.println(jobs.size());
		};
	}


}
