package de.frittenburger.meta.interfaces;

import java.util.List;

import de.frittenburger.meta.impl.MetaValue;
import de.frittenburger.meta.model.MetaFunction;
import de.frittenburger.meta.model.MetaRuntime;

public interface MetaFunctionProcessor {


	MetaValue process(MetaRuntime runtime, MetaFunction function, List<MetaValue> values);

}
