package de.frittenburger.meta.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.frittenburger.meta.interfaces.MetaCodeBlockProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.interfaces.MetaFunctionProcessor;
import de.frittenburger.meta.model.Code;
import de.frittenburger.meta.model.MetaExpression;
import de.frittenburger.meta.model.MetaFunction;
import de.frittenburger.meta.model.MetaRuntime;
import de.frittenburger.meta.model.MetaVariable;

public class TestBase {

	
	
	
	
	
	@Test
	public void testFunction() throws IOException {
		
		ClassLoader cl = this.getClass().getClassLoader();
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		MetaFunction function = mapper.readValue(cl.getResourceAsStream("base/function.yml"),MetaFunction.class);
		
		assertEquals("TestFunction",function.name);

		

		MetaCodeBlockProcessor blockProcessor = mock(MetaCodeBlockProcessor.class);
		MetaExpressionProcessor expressionProcessor = mock(MetaExpressionProcessor.class);
		MetaRuntime runtime = mock(MetaRuntime.class);
		MetaVariableStack stack = mock(MetaVariableStack.class);
		
		when(runtime.getVariableStack()).thenReturn(stack);
		when(expressionProcessor.process(eq(runtime), any(MetaExpression.class))).thenReturn(new MetaValue(0));
		
		MetaFunctionProcessor functionProcessor = new MetaFunctionProcessorImpl(expressionProcessor,blockProcessor);
		
		List<MetaValue> expressions = new ArrayList<>();
		
		expressions.add(new MetaValue(1));
		expressions.add(new MetaValue(2));

		functionProcessor.process(runtime,function,expressions);
		
		verify(expressionProcessor,times(2)).process(eq(runtime), any(MetaExpression.class));
		verify(blockProcessor,times(1)).process(eq(runtime), Mockito.<Code>anyList()); 
		verify(stack,times(4)).setVariable(any(String.class), any(MetaValue.class));
		verify(stack,times(4)).createVariable(any(MetaVariable.class));

	}

	
	

}
