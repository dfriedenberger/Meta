package de.frittenburger.meta.interfaces;

import de.frittenburger.meta.impl.MetaValue;
import de.frittenburger.meta.model.Code;
import de.frittenburger.meta.model.MetaExpression;
import de.frittenburger.meta.model.MetaRuntime;

public interface MetaExpressionProcessor {

	MetaValue process(MetaRuntime runtime, MetaExpression condition);

}
