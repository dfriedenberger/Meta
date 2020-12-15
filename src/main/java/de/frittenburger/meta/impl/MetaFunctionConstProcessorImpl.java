package de.frittenburger.meta.impl;

import de.frittenburger.meta.interfaces.MetaFunctionConstProcessor;
import de.frittenburger.meta.model.MetaConst;

public class MetaFunctionConstProcessorImpl implements MetaFunctionConstProcessor {

	@Override
	public MetaValue process(MetaConst constStatement) {

		
		switch(constStatement.type)
		{
			case "string":
				return new MetaValue(constStatement.value);
			case "number":
				return new MetaValue(Long.parseLong(constStatement.value));
			default:
				throw new RuntimeException("const type not implemented "+constStatement.type);
		}
		
		
	}

}
