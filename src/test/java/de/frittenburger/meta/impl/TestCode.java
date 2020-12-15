package de.frittenburger.meta.impl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*; 
import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.frittenburger.meta.interfaces.MetaCodeAssignProcessor;
import de.frittenburger.meta.interfaces.MetaCodeBlockProcessor;
import de.frittenburger.meta.interfaces.MetaCodeConditionalProcessor;
import de.frittenburger.meta.interfaces.MetaCodeLoopProcessor;
import de.frittenburger.meta.interfaces.MetaExpressionProcessor;
import de.frittenburger.meta.model.Code;
import de.frittenburger.meta.model.MetaExpression;
import de.frittenburger.meta.model.MetaRuntime;

public class TestCode {

	
	
	
	@Test
	public void testLoop() throws IOException {
		
		ClassLoader cl = this.getClass().getClassLoader();
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		Code code = mapper.readValue(cl.getResourceAsStream("code/loop.yml"),Code.class);
		
		assertNotNull(code.loopStatement);

		MetaCodeBlockProcessor blockProcessor = mock(MetaCodeBlockProcessor.class);
		MetaExpressionProcessor expressionProcessor = mock(MetaExpressionProcessor.class);
		
		when(expressionProcessor.process(eq(null), any(MetaExpression.class)))
	        .thenReturn(new MetaValue(true))
	        .thenReturn(new MetaValue(true))
	        .thenReturn(new MetaValue(false));

		
	  		
		MetaCodeLoopProcessor loopProcessor = new MetaCodeLoopProcessorImpl(expressionProcessor, blockProcessor);
		
		
		loopProcessor.process(null,code.loopStatement);

		
		verify(expressionProcessor,times(3)).process(eq(null), any(MetaExpression.class));
		verify(blockProcessor,times(2)).process(eq(null), Mockito.<Code>anyList()); 

		
	}
	
	
	@Test
	public void testIf() throws IOException {
		
		ClassLoader cl = this.getClass().getClassLoader();
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		Code code = mapper.readValue(cl.getResourceAsStream("code/conditional.yml"),Code.class);
		
		assertNotNull(code.conditionalStatement);
		
		MetaCodeBlockProcessor blockProcessor = mock(MetaCodeBlockProcessor.class);
		MetaExpressionProcessor expressionProcessor = mock(MetaExpressionProcessor.class);
		
		when(expressionProcessor.process(eq(null), any(MetaExpression.class))).thenReturn(new MetaValue(true));
	       
	  		
		MetaCodeConditionalProcessor conditionalProcessor = new MetaCodeConditionalProcessorImpl(expressionProcessor, blockProcessor);
		
		
		conditionalProcessor.process(null,code.conditionalStatement);

		
		verify(expressionProcessor,times(1)).process(eq(null), any(MetaExpression.class));
		verify(blockProcessor,times(1)).process(eq(null), Mockito.<Code>anyList()); 
		
		
	}
	
	
	@Test
	public void testAssign() throws IOException {
		
		ClassLoader cl = this.getClass().getClassLoader();
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		Code code = mapper.readValue(cl.getResourceAsStream("code/assign.yml"),Code.class);
		
		assertNotNull(code.assignStatement);
		
	    MetaExpressionProcessor expressionProcessor = mock(MetaExpressionProcessor.class);
	    
	    MetaRuntime runtime = mock(MetaRuntime.class);
		MetaVariableStack stack = mock(MetaVariableStack.class);
		
		when(runtime.getVariableStack()).thenReturn(stack);
		when(expressionProcessor.process(eq(runtime), any(MetaExpression.class))).thenReturn(new MetaValue(1));
		
		MetaCodeAssignProcessor assignProcessor = new MetaCodeAssignProcessorImpl(expressionProcessor);
		
		
		assignProcessor.process(runtime,code.assignStatement);
		
		verify(stack).setVariable(eq("a"), any(MetaValue.class));

		
		
	}

}
