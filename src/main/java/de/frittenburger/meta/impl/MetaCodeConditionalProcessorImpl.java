package de.frittenburger.meta.impl;

import de.frittenburger.meta.interfaces.MetaCodeBlockProcessor;
import de.frittenburger.meta.interfaces.MetaCodeConditionalProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.model.ConditionalStatement;
import de.frittenburger.meta.model.MetaRuntime;

public class MetaCodeConditionalProcessorImpl implements MetaCodeConditionalProcessor {

	private MetaExpressionProcessor expressionProcessor;
	private MetaCodeBlockProcessor codeBlockProcessor;

	public MetaCodeConditionalProcessorImpl(MetaExpressionProcessor expressionProcessor,MetaCodeBlockProcessor codeBlockProcessor)
	{
		this.expressionProcessor = expressionProcessor;
		this.codeBlockProcessor = codeBlockProcessor;
	}
	
	@Override
	public void process(MetaRuntime runtime, ConditionalStatement conditionalStatement) {
		
		MetaValue repeat =  expressionProcessor.process(runtime,conditionalStatement.condition);
		
		if(repeat.getBoolean()) 
			codeBlockProcessor.process(runtime,conditionalStatement.thenBlockStatement);
		else if(conditionalStatement.elseBlockStatement != null)
			codeBlockProcessor.process(runtime,conditionalStatement.elseBlockStatement);

	}

}
