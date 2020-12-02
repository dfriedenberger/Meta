package de.frittenburger.meta.model;


import java.util.List;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
public class Code  {

	@JsonInclude(Include.NON_NULL)
	public List<MetaVariable> variables;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("if")
	public Code ifStatement;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("then")
	public List<Code> thenStatement;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("for")
	public List<Code> forStatement;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("do")
	public List<Code> doStatement;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("while")
	public Code whileStatement;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("assign")
	public Assign assignStatement;
	
	@JsonInclude(Include.NON_NULL)
	public MetaFunctionCall fn;
		
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("var")
	public String varStatement;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("const")
	public MetaConst constStatement;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("return")
	public Code returnStatement;

	

	@Override
	public String toString() {
		return "Code [variables=" + variables + ", ifStatement=" + ifStatement + ", doStatement=" + doStatement
				+ ", forStatement=" + forStatement + ", assignStatement=" + assignStatement + ", whileStatement="
				+ whileStatement + ", fn=" + fn + ", varStatement=" + varStatement + ", constStatement="
				+ constStatement + ", returnStatement=" + returnStatement + ", thenStatement=" + thenStatement + "]";
	}
	
}
