package de.frittenburger.meta.impl;

import java.util.HashMap;
import java.util.Map;

import de.frittenburger.meta.model.MetaVariable;

public class MetaVariableStack {


	private Map<String,MetaValue> variables = new HashMap<>();

	public void setVariable(String name, MetaValue metaValue) {
		variables.put(name,metaValue);
	}

	public void createVariable(MetaVariable var) {
		//TODO System.out.println("define variable "+var.name);
	}

	public MetaValue getValue(String name) {
		return variables.get(name);
	}

	
	public void pushLayer() {
		// TODO Auto-generated method stub
		
	}
	
	public void popLayer() {
		// TODO Auto-generated method stub
		
	}

	

}
