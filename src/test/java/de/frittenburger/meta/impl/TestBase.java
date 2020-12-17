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
import de.frittenburger.meta.interfaces.MetaConstProcessor;
import de.frittenburger.meta.interfaces.MetaFunctionProcessor;
import de.frittenburger.meta.model.Code;
import de.frittenburger.meta.model.MetaAlgorithm;
import de.frittenburger.meta.model.MetaFunction;
import de.frittenburger.meta.model.MetaModel;
import de.frittenburger.meta.model.MetaModul;
import de.frittenburger.meta.model.MetaRuntime;

public class TestBase {

	
	
	
	
	
	@Test
	public void testFunction() throws IOException {
		
		ClassLoader cl = this.getClass().getClassLoader();
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		MetaFunction function = mapper.readValue(cl.getResourceAsStream("base/function.yml"),MetaFunction.class);
		
		assertEquals("TestFunction",function.name);

		

		MetaCodeBlockProcessor blockProcessor = mock(MetaCodeBlockProcessor.class);
		MetaConstProcessor constProcessor = mock(MetaConstProcessor.class);
		MetaRuntime runtime = mock(MetaRuntime.class);
		MetaVariableStack stack = mock(MetaVariableStack.class);
		MetaAlgorithm algorithm = mock(MetaAlgorithm.class);
		algorithm.modul = new MetaModul();

		when(runtime.getVariableStack()).thenReturn(stack);
		when(runtime.getAlgorithm()).thenReturn(algorithm);
		when(constProcessor.process(any(String.class),any(String.class))).thenReturn(new MetaValue(0));
		
		MetaFunctionProcessor functionProcessor = new MetaFunctionProcessorImpl(constProcessor,blockProcessor);
		
		List<MetaValue> expressions = new ArrayList<>();
		
		expressions.add(new MetaValue(1));
		expressions.add(new MetaValue(2));

		functionProcessor.process(runtime,function,expressions);
		
		verify(constProcessor,times(2)).process(any(String.class),any(String.class));
		verify(blockProcessor,times(1)).process(eq(runtime), Mockito.<Code>anyList()); 
		verify(stack,times(4)).setVariable(any(String.class), any(MetaValue.class));
		verify(stack,times(4)).createVariable(any(String.class),any(MetaModel.class));

	}

	
	@Test
	public void testModel() throws IOException {
		
		ClassLoader cl = this.getClass().getClassLoader();
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		MetaModel model = mapper.readValue(cl.getResourceAsStream("base/model.yml"),MetaModel.class);
		
		assertNotNull(model);
	
	}

}
