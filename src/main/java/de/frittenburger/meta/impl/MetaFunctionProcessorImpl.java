package de.frittenburger.meta.impl;

import java.util.List;

import de.frittenburger.meta.interfaces.MetaCodeBlockProcessor;
import de.frittenburger.meta.interfaces.MetaConstProcessor;
import de.frittenburger.meta.interfaces.MetaFunctionProcessor;
import de.frittenburger.meta.interfaces.ModelMapper;
import de.frittenburger.meta.model.MetaFunction;
import de.frittenburger.meta.model.MetaModel;
import de.frittenburger.meta.model.MetaRuntime;
import de.frittenburger.meta.model.MetaVariable;

public class MetaFunctionProcessorImpl implements MetaFunctionProcessor {

	private final MetaConstProcessor constProcessor;
	private final MetaCodeBlockProcessor codeBlockProcessor;
	private final ModelMapper modelMapper = new ModelMapperImpl();
	public MetaFunctionProcessorImpl(MetaConstProcessor constProcessor,MetaCodeBlockProcessor codeBlockProcessor)
	{
		this.constProcessor = constProcessor;
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
			MetaModel model = modelMapper.getModel(runtime,var.type);
			stack.createVariable(var.name,model);
			stack.setVariable(var.name,values.get(i));
		}
		
		//result	
		MetaModel resultModel = modelMapper.getModel(runtime,function.result.type);
		stack.createVariable(function.result.name,resultModel);
		MetaValue defaultValue = constProcessor.process(resultModel.type,function.result.value);
		stack.setVariable(function.result.name,defaultValue);
		
		
		//Create next Layer
		
		//variables
		if(function.variables != null)
			for(int i = 0;i < function.variables.size();i++)
			{
				MetaVariable var = function.variables.get(i);
				MetaModel model = modelMapper.getModel(runtime,var.type);

				stack.createVariable(var.name,model);
				
				MetaValue value = constProcessor.process(model.type,var.value);
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
