package de.frittenburger.meta.impl;

import java.util.List;

import de.frittenburger.meta.interfaces.MetaBuiltInFunctionCallProcessor;

public class MetaBuiltInFunctionCallProcessorImpl implements MetaBuiltInFunctionCallProcessor {

	@Override
	public MetaValue call(String name, List<MetaValue> values) {

		
		switch(name)
		{
			case "Mod":
				return new MetaValue( values.get(0).getLong() % values.get(1).getLong());
			case "isZero":
				return new MetaValue( values.get(0).getLong() == 0);
			case "isNotZero":
				return new MetaValue( values.get(0).getLong() != 0);			
			case "Abs":
				return new MetaValue( Math.abs(values.get(0).getLong()));
			case "PrintLine":
				System.out.println("PrintLine: "+values);
				return new MetaValue(0);
			case "Get":
				return values.get(0).getList().get((int) values.get(1).getLong());
			case "Add":
				values.get(0).getList().add(values.get(1));
				return new MetaValue("void");
			default:
				throw new RuntimeException(name+" not implemented");
		}
		
		
		
	}

}
