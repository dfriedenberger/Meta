package de.frittenburger.meta.impl;

import de.frittenburger.meta.interfaces.MetaReferenceProcessor;
import de.frittenburger.meta.model.MetaRuntime;

public class MetaReferenceProcessorImpl implements MetaReferenceProcessor {

	@Override
	public MetaValue process(MetaRuntime runtime, String ref) {
		return runtime.getVariableStack().getValue(ref);
	}

}
