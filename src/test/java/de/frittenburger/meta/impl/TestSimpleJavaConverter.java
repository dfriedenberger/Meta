package de.frittenburger.meta.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.frittenburger.meta.interfaces.MetaAlgorithmProcessor;
import de.frittenburger.meta.interfaces.SimpleJavaToMetaConverter;
import de.frittenburger.meta.model.MetaAlgorithm;
import de.frittenburger.meta.model.MetaRuntime;

public class TestSimpleJavaConverter {

	
	
	
	
	@Test
	public void testReportRepair() throws IOException {
	
		
		SimpleJavaToMetaConverter converter = new SimpleJavaToMetaConverterImpl();
		
		MetaAlgorithm algorithm = converter.convert(new File("src/main/java/de/frittenburger/aoc2020/day01/ReportRepair.java"));
		
		
		System.out.print(
				new ObjectMapper(new YAMLFactory()).writerWithDefaultPrettyPrinter().writeValueAsString(algorithm));
		
		MetaRuntime runtime = new MetaRuntime();
		runtime.setAlgorithm(algorithm);
		runtime.setVariableStack(new MetaVariableStack());

		
		//execute
		MetaAlgorithmProcessor processor = new MetaAlgorithmProcessorImpl();
		processor.run(runtime, "main", Arrays.asList(new MetaValue[] {new MetaValue("null")}));
		
	}
	
	
	@Test
	public void testPasswordPhilosophy() throws IOException {
	
		
		SimpleJavaToMetaConverter converter = new SimpleJavaToMetaConverterImpl();
		
		MetaAlgorithm algorithm = converter.convert(new File("src/main/java/de/frittenburger/aoc2020/day02/PasswordPhilosophy.java"));
		
		
		System.out.print(
				new ObjectMapper(new YAMLFactory()).writerWithDefaultPrettyPrinter().writeValueAsString(algorithm));
		
		MetaRuntime runtime = new MetaRuntime();
		runtime.setAlgorithm(algorithm);
		runtime.setVariableStack(new MetaVariableStack());

		
		//execute
		MetaAlgorithmProcessor processor = new MetaAlgorithmProcessorImpl();
		processor.run(runtime, "main", Arrays.asList(new MetaValue[] {new MetaValue("null")}));
		
	}

}
