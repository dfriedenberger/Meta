package de.frittenburger.meta.impl;

import de.frittenburger.meta.interfaces.MetaCodeBlockProcessor;
import de.frittenburger.meta.interfaces.MetaCodeLoopProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.model.LoopStatement;
import de.frittenburger.meta.model.MetaRuntime;

public class MetaCodeLoopProcessorImpl implements MetaCodeLoopProcessor {

	
	private MetaExpressionProcessor expressionProcessor;
	private MetaCodeBlockProcessor codeBlockProcessor;

	public MetaCodeLoopProcessorImpl(MetaExpressionProcessor expressionProcessor,MetaCodeBlockProcessor codeBlockProcessor)
	{
		this.expressionProcessor = expressionProcessor;
		this.codeBlockProcessor = codeBlockProcessor;
	}
	
	@Override
	public void process(MetaRuntime runtime, LoopStatement loopStatement) {
		
		
		while(true)
		{
			MetaValue repeat =  expressionProcessor.process(runtime,loopStatement.condition);
			
			if(!repeat.getBoolean()) break;
			
			codeBlockProcessor.process(runtime,loopStatement.doBlockStatement);
		}
	}

}
