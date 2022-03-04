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
@Table(name = "process_defintion")
@Getter @Setter @NoArgsConstructor
public class ProcessDefinition implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String code;
	private String version;
	private String description;
	
	@Column(length=5000)
	private String definition;
	
	private String status;
	
	public ProcessDefinition(String code, String version, String description, String definition, String status) {
		super();
		this.id = id;
		this.code = code;
		this.version = version;
		this.description = description;
		this.definition = definition;
		this.status = status;
	}
	
	
}
