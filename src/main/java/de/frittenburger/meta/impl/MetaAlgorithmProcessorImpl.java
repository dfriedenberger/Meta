package de.frittenburger.meta.impl;

import static de.frittenburger.meta.impl.HelpersLibrary.ConvertToNumberList;
import static de.frittenburger.meta.impl.HelpersLibrary.ConvertToCharacterList;
import static de.frittenburger.meta.impl.HelpersLibrary.ConvertToNumber;
import static de.frittenburger.meta.impl.HelpersLibrary.ReadLines;
import static de.frittenburger.meta.impl.HelpersLibrary.Match;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import de.frittenburger.meta.interfaces.MetaAlgorithmLoader;
import de.frittenburger.meta.interfaces.MetaAlgorithmProcessor;
import de.frittenburger.meta.model.Code;
import de.frittenburger.meta.model.InterruptFunctionExeption;
import de.frittenburger.meta.model.MetaAlgorithm;
import de.frittenburger.meta.model.MetaFunction;
import de.frittenburger.meta.model.MetaRuntime;
import de.frittenburger.meta.model.MetaVariable;

public class MetaAlgorithmProcessorImpl implements MetaAlgorithmProcessor {

	
	
	@Override
	public MetaValue run(MetaRuntime runtime, String funcName, List<MetaValue> values) {
		
		
		MetaFunction metaFunction = getFunction(runtime,funcName);
		MetaVariableStack stack = runtime.getVariableStack();
		
		for(int i = 0;i < metaFunction.params.size();i++)
		{
			MetaVariable var = metaFunction.params.get(i);
			stack.createVariable(var);
			stack.setVariable(var.name,values.get(i));
		}
		
		try {
			for(Code code : metaFunction.code)
			{
				processCode(runtime,code);
			}
		} catch(InterruptFunctionExeption e) {
			return e.getValue();
		}
		return null;
	}

	


	
	private MetaFunction getFunction(MetaRuntime runtime, String funcName) {
		
		List<MetaFunction> functions = runtime.getAlgorithm().modul.functions;
		
		for(MetaFunction function : functions)
		{
			if(function.name.equals(funcName)) return function;
		}
		throw new IllegalArgumentException("unknown function "+funcName);
	}







	private MetaValue processCode(MetaRuntime runtime, Code code) throws InterruptFunctionExeption {
		
		MetaVariableStack stack = runtime.getVariableStack();
		//processCode
		if(code.variables != null)
		{
			for(MetaVariable var : code.variables)
			{
				stack.createVariable(var);
			}
			return null;
		}
		
		
		if(code.assignStatement != null)
		{
			MetaValue value = processCode(runtime, code.assignStatement.value);

			stack.setVariable(code.assignStatement.var, value);
			return null;

		}
		
		if(code.varStatement != null)
		{
			MetaValue value = stack.getValue(code.varStatement);
			return value;
		}
		
		if(code.constStatement != null)
		{
			switch(code.constStatement.type)
			{
			    case "class":
			    case "void":
				case "string":
					return new MetaValue(code.constStatement.value);
				case "number":
					return new MetaValue(Long.parseLong(code.constStatement.value));
			}
			throw new IllegalArgumentException("Unknown type "+code.constStatement.type);
		}
		
		if(code.fn != null)
		{
			List<MetaValue> values = new ArrayList<>();
			for(Code param : code.fn.params)
			{
				MetaValue value = processCode(runtime,param);
				values.add(value);
			}
			
			MetaValue value = null;
			//call Function
			switch(code.fn.name)
			{
			case "Mod":
				value = new MetaValue( values.get(0).getLong() % values.get(1).getLong());
				break;
			case "Add":
				value = new MetaValue( values.get(0).getLong() + values.get(1).getLong());
				break;
			case "Sub":
				value = new MetaValue( values.get(0).getLong() - values.get(1).getLong());
				break;
			case "Mul":
				value = new MetaValue( values.get(0).getLong() * values.get(1).getLong());
				break;
			case "And":
				value = new MetaValue( values.get(0).getBoolean() && values.get(1).getBoolean());
				break;
			case "isNotZero":
				value = new MetaValue(values.get(0).getLong() != 0);
				break;
			case "isZero":
				value = new MetaValue(values.get(0).getLong() == 0);
				break;
			case "isLessThen":
				value = new MetaValue( values.get(0).getLong() < values.get(1).getLong());
				break;
			case "isLessEqualsThen":
				value = new MetaValue( values.get(0).getLong() <= values.get(1).getLong());
				break;
			case "isEquals":
				if(values.get(0).isString())
					value = new MetaValue( values.get(0).getString().equals(values.get(1).getString()));
				else
					value = new MetaValue( values.get(0).getLong() == values.get(1).getLong());
				break;
			case "Abs":
				value = new MetaValue(Math.abs(values.get(0).getLong()));
				break;
			case "New":
				value = new MetaValue(values.get(0).getString()+"@abcdefg123");
				break;
			case "ReadLines":
				try {
					value = new MetaValue(ReadLines(values.get(0).getString()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "ConvertToNumberList":
				value = new MetaValue(ConvertToNumberList(values.get(0).getList(String.class)));
				break;
			case "ConvertToCharacterList":
				value = new MetaValue(ConvertToCharacterList(values.get(0).getString()));
				break;
			case "ConvertToNumber":
				value = new MetaValue(ConvertToNumber(values.get(0).getString()));
				break;
			case "String2Number":
				value = new MetaValue(Long.parseLong(values.get(0).getString()));
				break;
			case "Match":
				value = new MetaValue(Match(values.get(0).getString(),values.get(1).getString()));
				break;
			case "Size":
				value = new MetaValue(values.get(0).getList().size());
				break;
			case "Get":
				value = new MetaValue(values.get(0).getList().get(values.get(1).toInteger()));
				break;
			case "PrintLine":
				System.out.println("PrintLine: "+values);
				break;
			default:
				value = run(runtime,code.fn.name,values);
				break;
			}
			//System.out.println("Function Call "+code.fn.name+" with "+values+" = "+value);
			return value;
		}
		
		if(code.doStatement != null && code.whileStatement != null)
		{			
			while(true)
			{
				
				for(Code doCode : code.doStatement)
					processCode(runtime,doCode);
				
				MetaValue repeat =  processCode(runtime,code.whileStatement);
				if(!repeat.getBoolean()) break;
			}
			return null;

		}
		
		if(code.forStatement != null && code.doStatement != null)
		{
			Code init = code.forStatement.get(0);
			Code condition = code.forStatement.get(1);
			Code each = code.forStatement.get(2);
			
			processCode(runtime,init);
			while(true)
			{
				MetaValue repeat =  processCode(runtime,condition);
				if(!repeat.getBoolean()) break;
				for(Code doCode : code.doStatement)
					processCode(runtime,doCode);
				
				processCode(runtime,each);

			}
			
			return null;
		}
		
		
		if(code.returnStatement != null)
		{
			MetaValue value = processCode(runtime,code.returnStatement);
			throw new InterruptFunctionExeption(value);
		}
		
		if(code.whileStatement != null)
		{
			return processCode(runtime, code.whileStatement);
		}
		
		if(code.ifStatement != null)
		{
			MetaValue value = processCode(runtime, code.ifStatement);
			
			if(value.getBoolean())
			{
				for(Code thenCode : code.thenStatement)
					processCode(runtime,thenCode);
			}
			return null;

		}
		
		
		throw new IllegalArgumentException("could not process "+code);

	}


	public static void main(String args[]) throws  IOException
	{
		MetaAlgorithmLoader loader = new MetaAlgorithmLoaderImpl();
		MetaAlgorithm algorithm = loader.load(new FileInputStream(args[0]));

		
		MetaRuntime runtime = new MetaRuntime();
		runtime.setAlgorithm(algorithm);
		runtime.setVariableStack(new MetaVariableStack());

		//execute
		MetaAlgorithmProcessor processor = new MetaAlgorithmProcessorImpl();
		processor.run(runtime, "main", Arrays.asList(new MetaValue[] {new MetaValue("null")}));
	}
}
