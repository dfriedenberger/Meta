package de.frittenburger.meta.impl;

import java.util.ArrayList;
import java.util.List;

import de.frittenburger.meta.interfaces.MetaBuiltInFunctionCallProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.interfaces.MetaFunctionCallProcessor;
import de.frittenburger.meta.model.MetaExpression;
import de.frittenburger.meta.model.MetaFunctionCall;
import de.frittenburger.meta.model.MetaRuntime;

public class MetaFunctionCallProcessorImpl implements MetaFunctionCallProcessor {

	private MetaExpressionProcessor expressionProcessor;
	private MetaBuiltInFunctionCallProcessor metaBuiltInFunctionCallProcessor;

	public MetaFunctionCallProcessorImpl(MetaExpressionProcessor expressionProcessor,MetaBuiltInFunctionCallProcessor metaBuiltInFunctionCallProcessor)
	{
		this.expressionProcessor = expressionProcessor;
		this.metaBuiltInFunctionCallProcessor = metaBuiltInFunctionCallProcessor;
	}
	
	
	@Override
	public MetaValue process(MetaRuntime runtime, MetaFunctionCall fn) {
		
		List<MetaValue> values = new ArrayList<>();
		for(MetaExpression param : fn.params)
		{
			MetaValue value = expressionProcessor.process(runtime,param);
			values.add(value);
		}
		
		//call function fn.name
		//TODO is function defined
		//Built-in Functions (Add, Mod, ...) 
		return metaBuiltInFunctionCallProcessor.call(fn.name,values);
		
	}

}
