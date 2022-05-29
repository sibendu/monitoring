package coms.ui.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MediatorRequestBody {
	String service;
	String operation;
	String requesttype;
	String requestbody;
}
