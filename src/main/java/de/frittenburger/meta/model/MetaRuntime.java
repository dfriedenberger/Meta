package de.frittenburger.meta.model;

import de.frittenburger.meta.impl.MetaVariableStack;

public class MetaRuntime {
	
	private MetaVariableStack variableStack;

	private MetaAlgorithm algorithm;

	public MetaVariableStack getVariableStack() {
		return variableStack;
	}

	public void setVariableStack(MetaVariableStack variableStack) {
		this.variableStack = variableStack;
	}

	public MetaAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(MetaAlgorithm algorithm) {
		this.algorithm = algorithm;
	}
	
}
