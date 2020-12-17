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

	private final MetaExpressionProcessor expressionProcessor;
	private final MetaCodeConditionalProcessor conditionalProcessor;
	private final MetaCodeLoopProcessor loopProcessor;
	private final MetaCodeAssignProcessor assignProcessor;

	public MetaCodeBlockProcessorImpl(MetaExpressionProcessor expressionProcessor) {
		this.expressionProcessor = expressionProcessor;
		this.conditionalProcessor = new MetaCodeConditionalProcessorImpl(expressionProcessor, this);
		this.loopProcessor = new MetaCodeLoopProcessorImpl(expressionProcessor, this);
		this.assignProcessor = new MetaCodeAssignProcessorImpl(expressionProcessor);
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
			else if(code.voidExpression != null)
			{
				MetaValue value = expressionProcessor.process(runtime, code.voidExpression);
				if(!value.toString().equals("void"))
					throw new RuntimeException("value has to be void "+value);
			}
			else 
				throw new RuntimeException("not implemented");
		}
		
		
		
		
	}

}
