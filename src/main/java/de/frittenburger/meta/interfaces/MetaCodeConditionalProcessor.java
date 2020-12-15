package de.frittenburger.meta.interfaces;


import de.frittenburger.meta.model.ConditionalStatement;
import de.frittenburger.meta.model.MetaRuntime;

public interface MetaCodeConditionalProcessor {

	void process(MetaRuntime runtime, ConditionalStatement conditionalStatement);

}
