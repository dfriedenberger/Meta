package de.frittenburger.meta.impl;

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
				return new MetaValue("null"); //TODO Referenz
			default:
				throw new RuntimeException("const type not implemented "+type);
		}
	}

}
