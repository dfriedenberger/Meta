package de.frittenburger.meta.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.frittenburger.meta.interfaces.MetaAlgorithmLoader;
import de.frittenburger.meta.interfaces.MetaAlgorithmProcessor;
import de.frittenburger.meta.model.MetaAlgorithm;
import de.frittenburger.meta.model.MetaRuntime;

public class TestMetaAlgorithmProcessor {

	@Test
	public void testHello() throws IOException {

		ClassLoader cl = this.getClass().getClassLoader();
		MetaAlgorithmLoader loader = new MetaAlgorithmLoaderImpl();
		MetaAlgorithm algorithm = loader.load(cl.getResourceAsStream("Hello.yml"));

		System.out.print(
				new ObjectMapper(new YAMLFactory()).writerWithDefaultPrettyPrinter().writeValueAsString(algorithm));

		MetaRuntime runtime = new MetaRuntime();
		runtime.setAlgorithm(algorithm);
		runtime.setVariableStack(new MetaVariableStack());

		
		//execute
		MetaAlgorithmProcessor processor = new MetaAlgorithmProcessorImpl();

		assertEquals(0,processor.run(runtime, "main", Arrays.asList(new MetaValue[] {new MetaValue("null")})).getLong());
		
	}
	

	@Test
	public void testGreatestCommonDivisor() throws IOException {

		ClassLoader cl = this.getClass().getClassLoader();
		MetaAlgorithmLoader loader = new MetaAlgorithmLoaderImpl();
		MetaAlgorithm algorithm = loader.load(cl.getResourceAsStream("GreatestCommonDivisor.yml"));

		System.out.print(
				new ObjectMapper(new YAMLFactory()).writerWithDefaultPrettyPrinter().writeValueAsString(algorithm));

		MetaRuntime runtime = new MetaRuntime();
		runtime.setAlgorithm(algorithm);
		runtime.setVariableStack(new MetaVariableStack());

		
		//execute
		MetaAlgorithmProcessor processor = new MetaAlgorithmProcessorImpl();

		assertEquals(3, processor.run(runtime, "GreatestCommonDivisor", Arrays.asList(new MetaValue[] {new MetaValue(6), new MetaValue(9) })).getLong());
		assertEquals(1, processor.run(runtime, "GreatestCommonDivisor", Arrays.asList(new MetaValue[] {new MetaValue(23), new MetaValue(29)})).getLong());
		
		assertEquals(5, processor.run(runtime, "GreatestCommonDivisor", Arrays.asList(new MetaValue[] {new MetaValue(0), new MetaValue(5)})).getLong());
		assertEquals(4, processor.run(runtime, "GreatestCommonDivisor", Arrays.asList(new MetaValue[] {new MetaValue(4), new MetaValue(0)})).getLong());
		
	}

}
