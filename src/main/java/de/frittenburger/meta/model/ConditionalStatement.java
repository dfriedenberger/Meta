package de.frittenburger.meta.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ConditionalStatement {

	@JsonProperty("if")
	public MetaExpression condition;
	
	@JsonProperty("then")
	public List<Code> thenBlockStatement;
	
	@JsonProperty("else")
	@JsonInclude(Include.NON_NULL)
	public List<Code> elseBlockStatement;
}
