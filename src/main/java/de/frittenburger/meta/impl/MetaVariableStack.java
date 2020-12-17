package de.frittenburger.meta.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.frittenburger.meta.model.BaseType;
import de.frittenburger.meta.model.MetaModel;

public class MetaVariableStack {


	private Map<String,MetaValue> variables = new HashMap<>();

	public void setVariable(String name, MetaValue metaValue) {
		variables.put(name,metaValue);
	}

	public void createVariable(String name,MetaModel type) {

		switch(type.type)
		{
			//case BaseType.TList:
			//	variables.put(name, new MetaValue(new ArrayList<MetaValue>()));
			//	break;
			case BaseType.TObject:
				for(MetaModel stype : type.properties)
				{
					switch(stype.type)
					{
						case BaseType.TList:
							variables.put(name+"."+stype.name, new MetaValue(new ArrayList<MetaValue>()));
							break;
					}
					
				}
				break;
		
		}
		
		
	}

	public MetaValue getValue(String name) {
		if(!variables.containsKey(name))
			throw new RuntimeException(name+" not exists");
		return variables.get(name);
	}

	
	public void pushLayer() {
		// TODO Auto-generated method stub
		
	}
	
	public void popLayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return "MetaVariableStack [variables=" + variables + "]";
	}

	

}
