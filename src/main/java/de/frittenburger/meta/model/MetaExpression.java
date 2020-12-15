package de.frittenburger.meta.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class MetaExpression {

	
	@JsonInclude(Include.NON_NULL)
	public MetaFunctionCall fn;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("ref")
	public String ref;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("const")
	public MetaConst constStatement;
	
}
