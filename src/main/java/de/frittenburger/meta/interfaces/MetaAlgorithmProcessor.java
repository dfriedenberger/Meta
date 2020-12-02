package de.frittenburger.meta.interfaces;

import java.util.List;

import de.frittenburger.meta.impl.MetaValue;
import de.frittenburger.meta.model.MetaRuntime;

public interface MetaAlgorithmProcessor {


	MetaValue run(MetaRuntime runtime, String funcName, List<MetaValue> values);

}
