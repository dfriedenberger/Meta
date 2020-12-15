package de.frittenburger.meta.impl;

import java.util.List;

import de.frittenburger.meta.interfaces.MetaCodeAssignProcessor;
import de.frittenburger.meta.interfaces.MetaCodeBlockProcessor;
import de.frittenburger.meta.interfaces.MetaCodeConditionalProcessor;
import de.frittenburger.meta.interfaces.MetaCodeLoopProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.model.Code;
import de.frittenburger.meta.model.MetaRuntime;

public class MetaCodeBlockProcessorImpl implements MetaCodeBlockProcessor {

	private final MetaCodeConditionalProcessor conditionalProcessor;
	private final MetaCodeLoopProcessor loopProcessor;
	private final MetaCodeAssignProcessor assignProcessor;

	public MetaCodeBlockProcessorImpl(MetaExpressionProcessor expressionProcessor) {
		conditionalProcessor = new MetaCodeConditionalProcessorImpl(expressionProcessor, this);
		loopProcessor = new MetaCodeLoopProcessorImpl(expressionProcessor, this);
		assignProcessor = new MetaCodeAssignProcessorImpl(expressionProcessor);
	}

	@Override
	public void process(MetaRuntime runtime, List<Code> block) {

		
		for(Code code : block)
		{
			if(code.loopStatement != null)
				loopProcessor.process(runtime, code.loopStatement);
			else if(code.conditionalStatement != null)
				conditionalProcessor.process(runtime, code.conditionalStatement);
			else if(code.assignStatement != null)
				assignProcessor.process(runtime, code.assignStatement);
			else 
				throw new RuntimeException("not implemented");

		}
		
		
		
		
	}

}
