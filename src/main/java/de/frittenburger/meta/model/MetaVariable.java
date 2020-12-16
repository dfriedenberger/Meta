package de.frittenburger.meta.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class MetaVariable {

	@JsonInclude(Include.NON_NULL)
	public String name;
	
	public String type;
	
	@JsonInclude(Include.NON_NULL)
	public List<MetaVariable> properties;
	
	@JsonInclude(Include.NON_NULL)
	public MetaVariable items;
	
	@JsonInclude(Include.NON_NULL)
	public String value;
	
}
