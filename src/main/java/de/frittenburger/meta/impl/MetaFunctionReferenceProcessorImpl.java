package de.frittenburger.meta.impl;

import de.frittenburger.meta.model.MetaRuntime;

public class MetaFunctionReferenceProcessorImpl implements MetaFunctionReferenceProcessor {

	@Override
	public MetaValue process(MetaRuntime runtime, String ref) {
		return runtime.getVariableStack().getValue(ref);
	}

}
