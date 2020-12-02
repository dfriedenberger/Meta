package de.frittenburger.meta.model;

import de.frittenburger.meta.impl.MetaValue;

public class InterruptFunctionExeption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MetaValue value;

	public InterruptFunctionExeption(MetaValue value) {
		this.value = value;
	}

	public MetaValue getValue() {
		return value;
	}

}
