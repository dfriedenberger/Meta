package de.frittenburger.meta.impl;

import java.util.List;

import de.frittenburger.meta.interfaces.MetaCodeBlockProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.interfaces.MetaFunctionProcessor;
import de.frittenburger.meta.model.MetaFunction;
import de.frittenburger.meta.model.MetaRuntime;
import de.frittenburger.meta.model.MetaVariable;

public class MetaFunctionProcessorImpl implements MetaFunctionProcessor {

	private MetaExpressionProcessor expressionProcessor;
	private MetaCodeBlockProcessor codeBlockProcessor;

	public MetaFunctionProcessorImpl(MetaExpressionProcessor expressionProcessor,MetaCodeBlockProcessor codeBlockProcessor)
	{
		this.expressionProcessor = expressionProcessor;
		this.codeBlockProcessor = codeBlockProcessor;
	}

	
	@Override
	public MetaValue process(MetaRuntime runtime, MetaFunction function, List<MetaValue> values) {
		
		

		MetaVariableStack stack = runtime.getVariableStack();
		stack.pushLayer();

		//params
		for(int i = 0;i < function.params.size();i++)
		{
			MetaVariable var = function.params.get(i);
			stack.createVariable(var);
			stack.setVariable(var.name,values.get(i));
		}
		
		//result	
		stack.createVariable(function.result);
		MetaValue defaultValue = expressionProcessor.process(runtime, function.result.value);
		stack.setVariable(function.result.name,defaultValue);
		
		
		//Create next Layer
		
		//variables
		if(function.variables != null)
			for(int i = 0;i < function.variables.size();i++)
			{
				MetaVariable var = function.variables.get(i);
				stack.createVariable(var);
				MetaValue value = expressionProcessor.process(runtime, function.variables.get(i).value);
				stack.setVariable(var.name,value);
			}
		
		//code
		codeBlockProcessor.process(runtime,function.code);

		MetaValue result = stack.getValue(function.result.name);
		
		//delete variables Layer
		stack.popLayer();

		return result;
	}


	

}
