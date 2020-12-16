package de.frittenburger.meta.impl;

import de.frittenburger.meta.interfaces.MetaBuiltInFunctionCallProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.interfaces.MetaFunctionCallProcessor;
import de.frittenburger.meta.interfaces.MetaReferenceProcessor;
import de.frittenburger.meta.interfaces.MetaConstProcessor;
import de.frittenburger.meta.model.MetaExpression;
import de.frittenburger.meta.model.MetaRuntime;

public class MetaExpressionProcessorImpl implements MetaExpressionProcessor {

	
	
	private final MetaFunctionCallProcessor functionCallProcessor;
	private final MetaReferenceProcessor referenceProcessor;
	private final MetaConstProcessor constProcessor;
	
	public MetaExpressionProcessorImpl(MetaBuiltInFunctionCallProcessor builtInFunctionCallProcessor,
			MetaReferenceProcessor referenceProcessor,MetaConstProcessor constProcessor)
	{
		this.functionCallProcessor = new MetaFunctionCallProcessorImpl(this,builtInFunctionCallProcessor);
		this.referenceProcessor = referenceProcessor;
		this.constProcessor = constProcessor;
	}
	
	@Override
	public MetaValue process(MetaRuntime runtime, MetaExpression expression) {

		if(expression.fn != null)
			return functionCallProcessor.process(runtime, expression.fn);
		
		if(expression.ref != null)
			return referenceProcessor.process(runtime, expression.ref);
		
		if(expression.constStatement != null)
			return constProcessor.process(expression.constStatement.type,expression.constStatement.value);
		
		throw new RuntimeException("not implemented");
		
	}

}
