package de.frittenburger.meta.interfaces;

import de.frittenburger.meta.impl.MetaValue;
import de.frittenburger.meta.model.MetaRuntime;

public interface MetaReferenceProcessor {

	MetaValue process(MetaRuntime runtime, String ref);

}
