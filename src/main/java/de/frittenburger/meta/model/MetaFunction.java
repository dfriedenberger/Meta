package de.frittenburger.meta.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class MetaFunction {
	
	public String name;
	
	@JsonInclude(Include.NON_DEFAULT)
	@JsonProperty("static")
	public boolean staticProperty;
	
	public List<MetaVariable> params;
	public List<MetaVariable> variables;
	
	public MetaVariable result;

	public List<Code> code;
	
	
	
}
