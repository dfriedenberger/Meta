package de.frittenburger.meta.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.frittenburger.meta.interfaces.MetaBuiltInFunctionCallProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.interfaces.MetaFunctionCallProcessor;
import de.frittenburger.meta.interfaces.MetaReferenceProcessor;
import de.frittenburger.meta.interfaces.MetaConstProcessor;
import de.frittenburger.meta.model.MetaExpression;
import de.frittenburger.meta.model.MetaRuntime;

public class TestExpression {

	@Test
	public void testFunctionCall() throws  IOException {
		
		ClassLoader cl = this.getClass().getClassLoader();
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		MetaExpression expression = mapper.readValue(cl.getResourceAsStream("expression/functioncall.yml"),MetaExpression.class);
		
		MetaExpressionProcessor expressionProcessor = mock(MetaExpressionProcessor.class);
		MetaBuiltInFunctionCallProcessor builtInFunctionCallProcessor = mock(MetaBuiltInFunctionCallProcessor.class);
		
		when(builtInFunctionCallProcessor.call(eq("isZero"),Mockito.<MetaValue>anyList())).thenReturn(new MetaValue(true));
		
		MetaFunctionCallProcessor functionCallProcessor = new MetaFunctionCallProcessorImpl(expressionProcessor,builtInFunctionCallProcessor);
		
		MetaValue value = functionCallProcessor.process(null,expression.fn);
		
		
		assertNotNull(value);
		assertTrue(value.getBoolean());
		
	}
	
	@Test
	public void testReference() throws  IOException {
		
		ClassLoader cl = this.getClass().getClassLoader();
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		MetaExpression expression = mapper.readValue(cl.getResourceAsStream("expression/reference.yml"),MetaExpression.class);
		
		MetaRuntime runtime = mock(MetaRuntime.class);
		MetaVariableStack stack = mock(MetaVariableStack.class);
		
		when(runtime.getVariableStack()).thenReturn(stack);		
		
		when(stack.getValue("a")).thenReturn(new MetaValue(4711));
		MetaReferenceProcessor referenceProcessor = new MetaReferenceProcessorImpl();
		
		MetaValue value = referenceProcessor.process(runtime,expression.ref);
		
		
		assertNotNull(value);
		assertEquals(4711,value.getLong());
		
	}
	
	@Test
	public void testConst() throws  IOException {
		
		ClassLoader cl = this.getClass().getClassLoader();
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		MetaExpression expression = mapper.readValue(cl.getResourceAsStream("expression/const.yml"),MetaExpression.class);
		
		

		MetaConstProcessor constProcessor = new MetaConstProcessorImpl();
		
		MetaValue value = constProcessor.process(expression.constStatement.type,expression.constStatement.value);
		
		
		assertNotNull(value);
		assertEquals("Test",value.getString());
		
	}

}
