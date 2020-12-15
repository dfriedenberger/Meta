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
import java.util.stream.Collector;
import java.util.stream.Collectors;

import de.frittenburger.meta.interfaces.MetaAlgorithmLoader;
import de.frittenburger.meta.interfaces.MetaAlgorithmProcessor;
import de.frittenburger.meta.interfaces.MetaBuiltInFunctionCallProcessor;
import de.frittenburger.meta.interfaces.MetaCodeBlockProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.interfaces.MetaFunctionProcessor;
import de.frittenburger.meta.model.Code;
import de.frittenburger.meta.model.InterruptFunctionExeption;
import de.frittenburger.meta.model.MetaAlgorithm;
import de.frittenburger.meta.model.MetaExpression;
import de.frittenburger.meta.model.MetaFunction;
import de.frittenburger.meta.model.MetaRuntime;
import de.frittenburger.meta.model.MetaVariable;

public class MetaAlgorithmProcessorImpl implements MetaAlgorithmProcessor {

	
	private final MetaBuiltInFunctionCallProcessor builtInFunctionCallProcessor = new MetaBuiltInFunctionCallProcessorImpl();
	private final MetaExpressionProcessor expressionProcessor = new MetaExpressionProcessorImpl(builtInFunctionCallProcessor);
	private final MetaCodeBlockProcessor blockProcessor = new MetaCodeBlockProcessorImpl(expressionProcessor);
	private final MetaFunctionProcessor functionProcessor = new MetaFunctionProcessorImpl(expressionProcessor,blockProcessor);

	
	@Override
	public MetaValue run(MetaRuntime runtime, String funcName, List<MetaValue> values) {
		
		
		MetaFunction function = getFunction(runtime,funcName);
		
		return functionProcessor.process(runtime,function,values);
		
		
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
	
		
		
	
		
//		if(code.constStatement != null)
//		{
//			switch(code.constStatement.type)
//			{
//			    case "class":
//			    case "void":
//			    case "property":
//				case "string":
//					return new MetaValue(code.constStatement.value);
//				case "number":
//					return new MetaValue(Long.parseLong(code.constStatement.value));
//			}
//			throw new IllegalArgumentException("Unknown type "+code.constStatement.type);
//		}
		
//		if(code.fn != null)
//		{
//			List<MetaValue> values = new ArrayList<>();
//			for(Code param : code.fn.params)
//			{
//				MetaValue value = processCode(runtime,param);
//				values.add(value);
//			}
//			
//			MetaValue value = null;
//			//call Function
//			switch(code.fn.name)
//			{
//			case "Mod":
//				value = new MetaValue( values.get(0).getLong() % values.get(1).getLong());
//				break;
//			case "Add":
//				if(values.get(0).isList())
//					values.get(0).getList().add(values.get(1));
//				else
//					value = new MetaValue( values.get(0).getLong() + values.get(1).getLong());
//				break;
//			case "Sub":
//				value = new MetaValue( values.get(0).getLong() - values.get(1).getLong());
//				break;
//			case "Mul":
//				value = new MetaValue( values.get(0).getLong() * values.get(1).getLong());
//				break;
//			case "And":
//				value = new MetaValue( values.get(0).getBoolean() && values.get(1).getBoolean());
//				break;
//			case "isNotZero":
//				value = new MetaValue(values.get(0).getLong() != 0);
//				break;
//			case "isZero":
//				value = new MetaValue(values.get(0).getLong() == 0);
//				break;
//			case "isLessThen":
//				value = new MetaValue( values.get(0).getLong() < values.get(1).getLong());
//				break;
//			case "isLessEqualsThen":
//				value = new MetaValue( values.get(0).getLong() <= values.get(1).getLong());
//				break;
//			case "isEquals":
//				if(values.get(0).isString())
//					value = new MetaValue( values.get(0).getString().equals(values.get(1).getString()));
//				else
//					value = new MetaValue( values.get(0).getLong() == values.get(1).getLong());
//				break;
//			case "Abs":
//				value = new MetaValue(Math.abs(values.get(0).getLong()));
//				break;
//			case "New":
//				value = new MetaValue(values.get(0).getString()+"@abcdefg123");
//				break;
//			case "ReadLines":
//				try {
//					List<MetaValue> out3 = ReadLines(values.get(0).getString()).stream().map(s -> new MetaValue(s)).collect(Collectors.toList());
//					value = new MetaValue(out3);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				break;
//			case "ConvertToNumberList":
//				List<String> in1 = values.get(0).getList().stream().map(v -> v.getString()).collect(Collectors.toList());
//				List<MetaValue> out1 = ConvertToNumberList(in1).stream().map(s -> new MetaValue(s)).collect(Collectors.toList());
//				value = new MetaValue(out1);
//				break;
//			case "ConvertToCharacterList":
//				List<MetaValue> out2 = ConvertToCharacterList(values.get(0).getString()).stream().map(s -> new MetaValue(s)).collect(Collectors.toList());
//				value = new MetaValue(out2);
//				break;
//			case "ConvertToNumber":
//				value = new MetaValue(ConvertToNumber(values.get(0).getString()));
//				break;
//			case "String2Number":
//				value = new MetaValue(Long.parseLong(values.get(0).getString()));
//				break;
//			case "Match":
//				List<MetaValue> out4 = Match(values.get(0).getString(),values.get(1).getString()).stream().map(s -> new MetaValue(s)).collect(Collectors.toList());
//				value = new MetaValue(out4);
//				break;
//			case "Size":
//				value = new MetaValue(values.get(0).getList().size());
//				break;
//			case "Get":
//				value = values.get(0).getList().get(values.get(1).toInteger());
//				break;
//			case "PrintLine":
//				System.out.println("PrintLine: "+values);
//				break;
//			default:
//				value = run(runtime,code.fn.name,values);
//				break;
//			}
//			//System.out.println("Function Call "+code.fn.name+" with "+values+" = "+value);
//			return value;
//		}
		
//		if(code.whileStatement != null && code.doStatement != null)
//		{			
//			while(true)
//			{
//				MetaValue repeat =  processCode(runtime,code.whileStatement);
//				if(!repeat.getBoolean()) break;
//				
//				for(Code doCode : code.doStatement)
//					processCode(runtime,doCode);
//			}
//			return null;
//
//		}
		
//		if(code.forStatement != null && code.doStatement != null)
//		{
//			Code init = code.forStatement.get(0);
//			Code condition = code.forStatement.get(1);
//			Code each = code.forStatement.get(2);
//			
//			processCode(runtime,init);
//			while(true)
//			{
//				MetaValue repeat =  processCode(runtime,condition);
//				if(!repeat.getBoolean()) break;
//				for(Code doCode : code.doStatement)
//					processCode(runtime,doCode);
//				
//				processCode(runtime,each);
//
//			}
//			
//			return null;
//		}
		
		
	
		

		
		
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
