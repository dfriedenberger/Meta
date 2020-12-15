package de.frittenburger.meta.interfaces;

import java.util.List;

import de.frittenburger.meta.impl.MetaValue;

public interface MetaBuiltInFunctionCallProcessor {

	MetaValue call(String name,List<MetaValue> values);

}
