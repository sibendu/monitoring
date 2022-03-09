package coms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event")
@Getter @Setter @NoArgsConstructor
public class Event implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String code;
	private Date start;
	private Date finish;
	private Long processId;
	
	@Column(length=5000)
	private String context;
	
	private String status;
	private boolean nextEvents;
	
	public Event(Long id, String code, Date start, Date finish, Long processId, String context, String status,boolean nextEvents) {
		super();
		this.id = id;
		this.code = code;
		this.start = start;
		this.finish = finish;
		this.processId = processId;
		this.context = context;
		this.status = status;
		this.nextEvents = nextEvents;
	}
}
