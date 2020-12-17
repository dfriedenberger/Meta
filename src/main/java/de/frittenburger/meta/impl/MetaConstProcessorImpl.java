package de.frittenburger.meta.impl;

import java.util.ArrayList;
import de.frittenburger.meta.interfaces.MetaConstProcessor;

public class MetaConstProcessorImpl implements MetaConstProcessor {



	@Override
	public MetaValue process(String type, String value) {
		switch(type)
		{
			case "string":
				return new MetaValue(value);
			case "number":
				return new MetaValue(Long.parseLong(value));
			case "object":
				return new MetaValue(value); //TODO Referenz null, new 
			case "list":
				return new MetaValue(new ArrayList<MetaValue>()); //TODO Referenz null, new 
			default:
				throw new RuntimeException("const type not implemented "+type);
		}
	}

}
