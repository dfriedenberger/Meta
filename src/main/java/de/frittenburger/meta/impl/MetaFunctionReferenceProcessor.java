package de.frittenburger.meta.impl;

import de.frittenburger.meta.model.MetaRuntime;

public interface MetaFunctionReferenceProcessor {

	MetaValue process(MetaRuntime runtime, String ref);

}
