package de.frittenburger.meta.impl;

import de.frittenburger.meta.interfaces.MetaCodeAssignProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.model.AssignStatement;
import de.frittenburger.meta.model.MetaRuntime;

public class MetaCodeAssignProcessorImpl implements MetaCodeAssignProcessor {

	private MetaExpressionProcessor expressionProcessor;

	public MetaCodeAssignProcessorImpl(MetaExpressionProcessor expressionProcessor) {
		this.expressionProcessor = expressionProcessor;
	}

	@Override
	public void process(MetaRuntime runtime, AssignStatement assignStatement) {

		MetaValue value =  expressionProcessor.process(runtime,assignStatement.value);
		runtime.getVariableStack().setVariable(assignStatement.ref, value);

	}

}
