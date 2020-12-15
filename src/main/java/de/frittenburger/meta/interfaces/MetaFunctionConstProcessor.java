package de.frittenburger.meta.interfaces;

import de.frittenburger.meta.impl.MetaValue;
import de.frittenburger.meta.model.MetaConst;

public interface MetaFunctionConstProcessor {

	MetaValue process(MetaConst constStatement);

}
