package de.frittenburger.meta.interfaces;

import de.frittenburger.meta.impl.MetaValue;
import de.frittenburger.meta.model.MetaFunctionCall;
import de.frittenburger.meta.model.MetaRuntime;

public interface MetaFunctionCallProcessor {

	MetaValue process(MetaRuntime runtime, MetaFunctionCall fn);

}
