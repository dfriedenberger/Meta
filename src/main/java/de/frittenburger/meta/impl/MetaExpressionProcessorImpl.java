package de.frittenburger.meta.impl;

import de.frittenburger.meta.interfaces.MetaBuiltInFunctionCallProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.interfaces.MetaFunctionCallProcessor;
import de.frittenburger.meta.interfaces.MetaFunctionConstProcessor;
import de.frittenburger.meta.model.MetaExpression;
import de.frittenburger.meta.model.MetaRuntime;

public class MetaExpressionProcessorImpl implements MetaExpressionProcessor {

	
	
	private final MetaFunctionCallProcessor functionCallProcessor;
	private final MetaFunctionReferenceProcessor referenceProcessor;
	private final MetaFunctionConstProcessor constProcessor;
	
	public MetaExpressionProcessorImpl(MetaBuiltInFunctionCallProcessor builtInFunctionCallProcessor)
	{
		functionCallProcessor = new MetaFunctionCallProcessorImpl(this,builtInFunctionCallProcessor);
		referenceProcessor = new MetaFunctionReferenceProcessorImpl();
		constProcessor = new MetaFunctionConstProcessorImpl();
	}
	
	@Override
	public MetaValue process(MetaRuntime runtime, MetaExpression expression) {

		if(expression.fn != null)
			return functionCallProcessor.process(runtime, expression.fn);
		
		if(expression.ref != null)
			return referenceProcessor.process(runtime, expression.ref);
		
		if(expression.constStatement != null)
			return constProcessor.process(expression.constStatement);
		
		throw new RuntimeException("not implemented");
		
	}

}
