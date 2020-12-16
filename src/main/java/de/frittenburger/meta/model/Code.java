package de.frittenburger.meta.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Code  {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("conditional")
	public ConditionalStatement conditionalStatement;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("loop")
	public LoopStatement loopStatement;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("assign")
	public AssignStatement assignStatement;
	
}
