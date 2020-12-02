package de.frittenburger.meta.interfaces;

import java.io.File;
import java.io.FileNotFoundException;

import de.frittenburger.meta.model.MetaAlgorithm;

public interface SimpleJavaToMetaConverter {

	MetaAlgorithm convert(File file) throws FileNotFoundException;

}
