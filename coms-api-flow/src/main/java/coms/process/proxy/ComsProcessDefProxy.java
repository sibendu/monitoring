package coms.process.proxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ComsProcessDefProxy implements Serializable{
	
	private String code;
	
    private List<EventDefinitionProxy> events = new ArrayList<>();
    
    private String[] endEvents;
}
