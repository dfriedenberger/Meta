package de.frittenburger.meta.impl;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.frittenburger.meta.interfaces.MetaAlgorithmLoader;
import de.frittenburger.meta.model.MetaAlgorithm;

public class MetaAlgorithmLoaderImpl implements MetaAlgorithmLoader {

	@Override
	public MetaAlgorithm load(InputStream is) throws IOException {

			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			return mapper.readValue(is,MetaAlgorithm.class);
						
		
	}

	
}
