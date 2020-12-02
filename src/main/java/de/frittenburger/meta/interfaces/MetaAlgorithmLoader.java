package de.frittenburger.meta.interfaces;

import java.io.IOException;
import java.io.InputStream;

import de.frittenburger.meta.model.MetaAlgorithm;

public interface MetaAlgorithmLoader {

	MetaAlgorithm load(InputStream is) throws IOException;

}
