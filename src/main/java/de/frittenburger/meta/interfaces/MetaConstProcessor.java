package de.frittenburger.meta.interfaces;

import de.frittenburger.meta.impl.MetaValue;

public interface MetaConstProcessor {

	MetaValue process(String type, String value);

}
