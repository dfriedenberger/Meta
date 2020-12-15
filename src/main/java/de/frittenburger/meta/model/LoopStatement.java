package de.frittenburger.meta.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoopStatement {

	
	@JsonProperty("while")
	public MetaExpression condition;
	
	@JsonProperty("do")
	public List<Code> doBlockStatement;
	
	
}
